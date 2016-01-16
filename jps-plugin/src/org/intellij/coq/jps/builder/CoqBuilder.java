/*
 * IntelliJ-coqplugin  / Plugin IntelliJ for Coq
 * Copyright (c) 2016
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.intellij.coq.jps.builder;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.BaseOSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.intellij.coq.jps.model.CoqCompilerOptions;
import org.intellij.coq.jps.model.JpsCoqCompilerOptionsExtension;
import org.intellij.coq.jps.model.JpsCoqModuleType;
import org.intellij.coq.jps.model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildOutputConsumer;
import org.jetbrains.jps.builders.DirtyFilesHolder;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ProjectBuildException;
import org.jetbrains.jps.incremental.TargetBuilder;
import org.jetbrains.jps.incremental.messages.BuildMessage;
import org.jetbrains.jps.incremental.messages.CompilerMessage;
import org.jetbrains.jps.incremental.resources.ResourcesBuilder;
import org.jetbrains.jps.incremental.resources.StandardResourceBuilderEnabler;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.java.JpsJavaExtensionService;
import org.jetbrains.jps.model.library.sdk.JpsSdk;
import org.jetbrains.jps.model.module.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.intellij.coq.jps.builder.CoqBuilderUtil.LOG;
import static org.intellij.coq.jps.builder.CoqBuilderUtil.isSource;

public class CoqBuilder extends TargetBuilder<CoqSourceRootDescriptor, CoqTarget> {
  public static final String NAME = "erlc";

  public CoqBuilder() {
    super(Collections.singletonList(CoqTargetType.INSTANCE));

    //TODO provide a way to copy erlang resources
    //disables java resource builder for erlang modules
    ResourcesBuilder.registerEnabler(new StandardResourceBuilderEnabler() {
      @Override
      public boolean isResourceProcessingEnabled(@NotNull JpsModule module) {
        return !(module.getModuleType() instanceof JpsCoqModuleType);
      }
    });
  }

  @Override
  public void build(@NotNull CoqTarget target,
                    @NotNull DirtyFilesHolder<CoqSourceRootDescriptor, CoqTarget> holder,
                    @NotNull BuildOutputConsumer outputConsumer,
                    @NotNull CompileContext context) throws ProjectBuildException, IOException {

    JpsModule module = target.getModule();
    JpsProject project = module.getProject();
    CoqCompilerOptions compilerOptions = JpsCoqCompilerOptionsExtension.getOrCreateExtension(project).getOptions();
    if (compilerOptions.myUseRebarCompiler) return;

    LOG.info("Build module " + target.getPresentableName());
    File sourceOutput = getBuildOutputDirectory(module, false, context);
    File testOutput = getBuildOutputDirectory(module, true, context);

    if (holder.hasRemovedFiles()) {
      LOG.debug("Remove invalid .beam and .app files.");
      removeOutputFiles(holder.getRemovedFiles(target), sourceOutput, testOutput);
    }

    buildSources(target, context, compilerOptions, outputConsumer, sourceOutput, false);
    buildSources(target, context, compilerOptions, outputConsumer, testOutput, true);

    processAppConfigFiles(holder, outputConsumer, context, sourceOutput, testOutput);
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return NAME;
  }

  private static void removeOutputFiles(@NotNull Collection<String> removedFiles, @NotNull File... outputDirectories) {
    for (File dir : outputDirectories) {
      List<File> outputErlangModuleFiles = ContainerUtil.concat(getBeams(findErlFiles(removedFiles), dir),
                                                                getAppsOutput(findAppFiles(removedFiles), dir));
      for (File output : outputErlangModuleFiles) {
        if (output.exists() && !output.delete()) {
          LOG.warn("Can't delete file " + output.getAbsolutePath());
        }
      }
    }
  }

  @NotNull
  private static List<File> getBeams(@NotNull Collection<String> erlPaths,
                                     @NotNull final File outputDirectory) {
    return ContainerUtil.map(erlPaths, new Function<String, File>() {
      @Override
      public File fun(String filePath) {
        String name = FileUtil.getNameWithoutExtension(new File(filePath));
        return new File(outputDirectory.getAbsolutePath() + File.separator + name + ".beam");
      }
    });
  }

  @NotNull
  private static List<File> getAppsOutput(@NotNull List<String> sourceApps, @NotNull final File outputDir) {
    return ContainerUtil.map(sourceApps, new Function<String, File>() {
      @Override
      public File fun(String sourceFile) {
        return getDestinationAppConfig(outputDir, sourceFile);
      }
    });
  }

  private static void buildSources(@NotNull CoqTarget target,
                                   @NotNull CompileContext context,
                                   @NotNull CoqCompilerOptions compilerOptions,
                                   @NotNull BuildOutputConsumer outputConsumer,
                                   @NotNull File outputDir,
                                   final boolean isTests) throws IOException, ProjectBuildException {
    List<String> erlangModulePathsToCompile = getErlangModulePaths(target, context, isTests);
    if (erlangModulePathsToCompile.isEmpty()) {
      String message = isTests ? "Test sources is up to date for module" : "Sources is up to date for module";
      reportMessageForModule(context, message, target.getModule().getName());
      return;
    }
    String message = isTests ? "Compile tests for module" : "Compile source code for module";
    reportMessageForModule(context, message, target.getModule().getName());
    runErlc(target, context, compilerOptions, erlangModulePathsToCompile, outputConsumer, outputDir, isTests);
  }

  private static void reportMessageForModule(@NotNull CompileContext context,
                                             @NotNull String messagePrefix,
                                             @NotNull String moduleName) {
    String message = messagePrefix + " \"" + moduleName + "\".";
    reportMessage(context, message);
  }

  private static void reportMessage(@NotNull CompileContext context, @NotNull String message) {
    LOG.info(message);
    context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.PROGRESS, message));
    context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.INFO, message));
  }

  @NotNull
  private static File getBuildOutputDirectory(@NotNull JpsModule module,
                                              boolean forTests,
                                              @NotNull CompileContext context) throws ProjectBuildException {
    JpsJavaExtensionService instance = JpsJavaExtensionService.getInstance();
    File outputDirectory = instance.getOutputDirectory(module, forTests);
    if (outputDirectory == null) {
      String errorMessage = "No output dir for module " + module.getName();
      context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.ERROR, errorMessage));
      throw new ProjectBuildException(errorMessage);
    }
    if (!outputDirectory.exists()) {
      FileUtil.createDirectory(outputDirectory);
    }
    return outputDirectory;
  }

  private static void processAppConfigFiles(DirtyFilesHolder<CoqSourceRootDescriptor, CoqTarget> holder,
                                            BuildOutputConsumer outputConsumer,
                                            CompileContext context,
                                            File... outputDirectories) throws IOException {
    List<File> appConfigFiles = new DirtyFilesProcessor<File, CoqTarget>() {
      @Nullable
      @Override
      protected File getDirtyElement(@NotNull File file) throws IOException {
        return CoqBuilderUtil.isAppConfigFileName(file.getName()) ? file : null;
      }
    }.collectDirtyElements(holder);

    for (File appConfigSrc : appConfigFiles) {
      for (File outputDir : outputDirectories) {
        File appConfigDst = getDestinationAppConfig(outputDir, appConfigSrc.getName());
        FileUtil.copy(appConfigSrc, appConfigDst);
        reportMessage(context, String.format("Copy %s to %s", appConfigDst.getAbsolutePath(), outputDir.getAbsolutePath()));
        outputConsumer.registerOutputFile(appConfigDst, Collections.singletonList(appConfigSrc.getAbsolutePath()));
      }
    }
  }

  @NotNull
  private static File getDestinationAppConfig(File outputDir, @NotNull String fileName) {
    return new File(outputDir, getAppConfigDestinationFileName(fileName));
  }

  @NotNull
  private static String getAppConfigDestinationFileName(@NotNull String sourceFileName) {
    return StringUtil.trimEnd(sourceFileName, ".src");
  }

  private static void runErlc(CoqTarget target,
                              CompileContext context,
                              CoqCompilerOptions compilerOptions,
                              List<String> erlangModulePathsToCompile,
                              BuildOutputConsumer outputConsumer,
                              File outputDirectory,
                              boolean isTest) throws ProjectBuildException, IOException {
    GeneralCommandLine commandLine = getErlcCommandLine(target, context, compilerOptions, outputDirectory, erlangModulePathsToCompile, isTest);
    Process process;
    LOG.debug("Run erlc compiler with command " + commandLine.getCommandLineString());
    try {
      process = commandLine.createProcess();
    }
    catch (ExecutionException e) {
      throw new ProjectBuildException("Failed to launch erlang compiler", e);
    }
    BaseOSProcessHandler handler = new BaseOSProcessHandler(process, commandLine.getCommandLineString(), Charset.defaultCharset());
    ProcessAdapter adapter = new CoqCompilerProcessAdapter(context, NAME, "");
    handler.addProcessListener(adapter);
    handler.startNotify();
    handler.waitFor();
    consumeFiles(outputConsumer, getBeams(erlangModulePathsToCompile, outputDirectory));
  }

  private static GeneralCommandLine getErlcCommandLine(CoqTarget target,
                                                       CompileContext context,
                                                       CoqCompilerOptions compilerOptions,
                                                       File outputDirectory,
                                                       List<String> erlangModulePaths,
                                                       boolean isTest) throws ProjectBuildException {
    GeneralCommandLine commandLine = new GeneralCommandLine();
    JpsModule module = target.getModule();
    JpsSdk<JpsDummyElement> sdk = CoqTargetBuilderUtil.getSdk(context, module);
    File executable = JpsCoqSdkType.getByteCodeCompilerExecutable(sdk.getHomePath());
    commandLine.withWorkDirectory(outputDirectory);
    commandLine.setExePath(executable.getAbsolutePath());
    addCodePath(commandLine, module, target, context);
    addParseTransforms(commandLine, module);
    addDebugInfo(commandLine, compilerOptions.myAddDebugInfoEnabled);
    addIncludePaths(commandLine, module);
    addMacroDefinitions(commandLine, isTest);
    commandLine.addParameters(erlangModulePaths);
    return commandLine;
  }

  private static void addMacroDefinitions(GeneralCommandLine commandLine, boolean isTests) {
    if (isTests) {
      commandLine.addParameters("-DTEST");
    }
  }

  private static void addDebugInfo(@NotNull GeneralCommandLine commandLine, boolean addDebugInfoEnabled) {
    if (addDebugInfoEnabled) {
      commandLine.addParameter("+debug_info");
    }
  }

  private static void addIncludePaths(@NotNull GeneralCommandLine commandLine, @Nullable JpsModule module) {
    if (module == null) return;
    for (JpsTypedModuleSourceRoot<JpsDummyElement> includeDirectory : module.getSourceRoots(CoqIncludeSourceRootType.INSTANCE)) {
      commandLine.addParameters("-I", includeDirectory.getFile().getPath());
    }
  }

  @NotNull
  private static List<String> getErlangModulePaths(@NotNull CoqTarget target,
                                                   @NotNull CompileContext context,
                                                   boolean isTest) {
    List<String> erlangDirtyModules = getErlangModulePathsFromTarget(target, isTest);
    if (erlangDirtyModules != null) {
      return erlangDirtyModules;
    }

    String message = "Erlang module " + target.getModule().getName() + " will be fully rebuilt.";
    LOG.warn(message);
    context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.WARNING, message));
    return getErlangModulePathsDefault(target, isTest);
  }

  @Nullable
  private static List<String> getErlangModulePathsFromTarget(@NotNull CoqTarget target, boolean isTests) {
    CoqModuleBuildOrder buildOrder = target.getBuildOrder();
    if (buildOrder == null) return null;

    List<String> modules = buildOrder.myOrderedErlangFilePaths;
    return isTests ? ContainerUtil.concat(modules, buildOrder.myOrderedErlangTestFilePaths) : modules;
  }

  @NotNull
  private static List<String> getErlangModulePathsDefault(@NotNull CoqTarget target, boolean isTests) {
    CommonProcessors.CollectProcessor<File> erlFilesCollector = new CommonProcessors.CollectProcessor<File>() {
      @Override
      protected boolean accept(@NotNull File file) {
        return !file.isDirectory() && isSource(file.getName());
      }
    };
    List<JpsModuleSourceRoot> sourceRoots = ContainerUtil.newArrayList();
    JpsModule module = target.getModule();
    ContainerUtil.addAll(sourceRoots, module.getSourceRoots(JavaSourceRootType.SOURCE));
    if (isTests) {
      ContainerUtil.addAll(sourceRoots, module.getSourceRoots(JavaSourceRootType.TEST_SOURCE));
    }
    for (JpsModuleSourceRoot root : sourceRoots) {
      FileUtil.processFilesRecursively(root.getFile(), erlFilesCollector);
    }
    return ContainerUtil.map(erlFilesCollector.getResults(), new Function<File, String>() {
      @NotNull
      @Override
      public String fun(@NotNull File file) {
        return file.getAbsolutePath();
      }
    });
  }

  private static void addParseTransforms(@NotNull GeneralCommandLine commandLine,
                                         @Nullable JpsModule module) throws ProjectBuildException {
    JpsCoqModuleExtension extension = JpsCoqModuleExtension.getExtension(module);
    List<String> parseTransforms = extension != null ? extension.getParseTransforms() : Collections.<String>emptyList();
    if (parseTransforms.isEmpty()) return;
    for (String ptModule : parseTransforms) {
      commandLine.addParameter("+{parse_transform, " + ptModule + "}");
    }
  }

  private static void addCodePath(@NotNull GeneralCommandLine commandLine,
                                  @NotNull JpsModule module,
                                  @NotNull CoqTarget target,
                                  @NotNull CompileContext context) throws ProjectBuildException {
    List<JpsModule> codePathModules = ContainerUtil.newArrayList();
    collectDependentModules(module, codePathModules, ContainerUtil.<String>newHashSet());
    addModuleToCodePath(commandLine, module, target.isTests(), context);
    for (JpsModule codePathModule : codePathModules) {
      if (codePathModule != module) {
        addModuleToCodePath(commandLine, codePathModule, false, context);
      }
    }
  }

  private static void collectDependentModules(@NotNull JpsModule module,
                                              @NotNull Collection<JpsModule> addedModules,
                                              @NotNull Set<String> addedModuleNames) {
    String moduleName = module.getName();
    if (addedModuleNames.contains(moduleName)) return;
    addedModuleNames.add(moduleName);
    addedModules.add(module);
    for (JpsDependencyElement dependency : module.getDependenciesList().getDependencies()) {
      if (!(dependency instanceof JpsModuleDependency)) continue;
      JpsModuleDependency moduleDependency = (JpsModuleDependency) dependency;
      JpsModule depModule = moduleDependency.getModule();
      if (depModule != null) {
        collectDependentModules(depModule, addedModules, addedModuleNames);
      }
    }
  }

  private static void addModuleToCodePath(@NotNull GeneralCommandLine commandLine,
                                          @NotNull JpsModule module,
                                          boolean forTests,
                                          @NotNull CompileContext context) throws ProjectBuildException {
    File outputDirectory = getBuildOutputDirectory(module, forTests, context);
    commandLine.addParameters("-pa", outputDirectory.getPath());
    for (String rootUrl : module.getContentRootsList().getUrls()) {
      try {
        String path = new URL(rootUrl).getPath();
        commandLine.addParameters("-pa", path);
      }
      catch (MalformedURLException e) {
        context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.ERROR, "Failed to find content root for module: " + module.getName()));
      }
    }
  }

  private static void consumeFiles(@NotNull BuildOutputConsumer outputConsumer,
                                   @NotNull List<File> dirtyFilePaths) throws IOException {
    for (File outputFile : dirtyFilePaths) {
      if (outputFile.exists()) {
        outputConsumer.registerOutputFile(outputFile, Collections.singletonList(outputFile.getAbsolutePath()));
      }
    }
  }

  @NotNull
  private static List<String> findAppFiles(@NotNull Collection<String> removedFiles) {
    return ContainerUtil.filter(removedFiles, new Condition<String>() {
      @Override
      public boolean value(String filePath) {
        return CoqBuilderUtil.isAppConfigFileName(filePath);
      }
    });
  }

  @NotNull
  private static List<String> findErlFiles(@NotNull Collection<String> removedFiles) {
    return ContainerUtil.filter(removedFiles, new Condition<String>() {
      @Override
      public boolean value(String s) {
        return CoqBuilderUtil.isSource(s);
      }
    });
  }

}