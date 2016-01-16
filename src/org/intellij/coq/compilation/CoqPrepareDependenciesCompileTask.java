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

package org.intellij.coq.compilation;

import com.intellij.compiler.server.BuildManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileTask;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.SystemProperties;
import com.intellij.util.xmlb.SkipDefaultValuesSerializationFilters;
import com.intellij.util.xmlb.XmlSerializationException;
import com.intellij.util.xmlb.XmlSerializer;
import org.intellij.coq.CoqFileType;
import org.intellij.coq.jps.builder.CoqBuilderUtil;
import org.intellij.coq.jps.builder.CoqFileDescriptor;
import org.intellij.coq.jps.builder.CoqProjectBuildOrder;
import org.intellij.coq.configuration.CoqCompilerSettings;
import org.jdom.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dabrowski on 15/01/2016.
 */
public class CoqPrepareDependenciesCompileTask implements CompileTask {

    private static final Logger LOG = Logger.getInstance(CoqPrepareDependenciesCompileTask.class);

    @Override
    public boolean execute(CompileContext compileContext) {

        System.out.println("CoqPrepareDependenciesCompileTask.execute");
        Project project = compileContext.getProject();
        if (CoqCompilerSettings.getInstance(project).isUseRebarCompilerEnabled()) {
            System.out.println("CoqPrepareDependenciesCompileTask.execute[1]");
            // delegate dependencies resolution to rebar
            return true;
        }
        LOG.info("Prepare build order for project " + project.getName());


        File projectSystemDirectory = BuildManager.getInstance().getProjectSystemDirectory(project);
        if (projectSystemDirectory == null) {
            addPrepareDependenciesFailedMessage(compileContext);
            return true;
        }

        CoqProjectBuildOrder projectBuildOrder =
                ApplicationManager.getApplication().runReadAction(new Computable<CoqProjectBuildOrder>() {
            @Nullable
            @Override
            public CoqProjectBuildOrder compute() {

                return getProjectBuildOrder(compileContext);
            }
        });

        if (projectBuildOrder == null) {
            return false; // errors are reported to context.
        }
        writeBuildOrder(compileContext, projectSystemDirectory, projectBuildOrder);
        System.out.println("CoqPrepareDependenciesCompileTask.execute (Done)");
        return true;

    }

    public static void writeBuildOrder(@NotNull CompileContext context,
                                       @NotNull File projectSystemDirectory,
                                       @NotNull CoqProjectBuildOrder projectBuildOrder) {
        System.out.println("CoqPrepareDependenciesCompileTask.writeBuildOrder");
        try {
            LOG.debug("Serialize build order");
            Document serializedDocument = new Document(XmlSerializer.serialize(projectBuildOrder, new SkipDefaultValuesSerializationFilters()));
            File parentDir = new File(projectSystemDirectory, CoqBuilderUtil.BUILDER_DIRECTORY);
            //noinspection ResultOfMethodCallIgnored
            parentDir.mkdirs();
            File file = new File(parentDir, CoqBuilderUtil.BUILD_ORDER_FILE_NAME);

            LOG.debug("Write build order to " + file.getAbsolutePath());
            JDOMUtil.writeDocument(serializedDocument, file, SystemProperties.getLineSeparator());
            System.out.println("Write build order to " + file.getAbsolutePath() + "(Done)");
        }
        catch (XmlSerializationException e) {
            LOG.error("Can't serialize build order object.", e);
            addPrepareDependenciesFailedMessage(context);
        }
        catch (IOException e) {
            LOG.warn("Some I/O errors occurred while writing build orders to file", e);
            addPrepareDependenciesFailedMessage(context);
        }
    }

    @Nullable
    private static CoqProjectBuildOrder getProjectBuildOrder(@NotNull CompileContext context) {

        System.out.println("CoqPrepareDendenciesCompileTask.getProjectBuildOrder");

        //try {
            Module[] modulesToCompile = context.getCompileScope().getAffectedModules();
            CoqProjectBuildOrder cpb  = new CoqProjectBuildOrder(getTopologicallySortedFileDescriptors(context));
            //System.out.println(cpb.myCoqFiles.size());
            return cpb;
        /*}
        catch (CyclicDependencyFoundException e) {
            String message = "Cyclic erlang module dependency detected. Check files " +
                    e.getFirstFileInCycle() + " and " + e.getLastFileInCycle() +
                    "or their dependencies(parse_transform, behaviour, include)";
            LOG.debug(message, e);
            context.addMessage(CompilerMessageCategory.ERROR, message, null, -1, -1);
            return null;
        }*/
    }

    public static List<VirtualFile> getSubdirs(VirtualFile file) {

        VirtualFile[] files = file.getChildren();
        List<VirtualFile> dirs = new ArrayList<>();

        for (VirtualFile f : files) {
            if (f.isDirectory()) {
                dirs.add(f);
                dirs.addAll(getSubdirs(f));
            }
        }
        return dirs;
    }

    @NotNull
    private static List<CoqFileDescriptor> getTopologicallySortedFileDescriptors(@NotNull CompileContext context) {

        //TODO : Handle several modules???

        VirtualFile[] files = context.getCompileScope().getFiles(CoqFileType.INSTANCE, true);
        List<VirtualFile> vfiles = Arrays.asList(files);

        VirtualFile base = context.getProject().getBaseDir().findChild("src");
        List<VirtualFile> subdirs = getSubdirs(base);
Project project = context.getProject();
        Runtime runtime = Runtime.getRuntime();
        Sdk projectSdk = ProjectRootManager.getInstance(context.getProject()).getProjectSdk();

        List<String> cmd = new ArrayList<>();
        cmd.add(projectSdk.getHomePath() + "/bin/coqdep");
        cmd.add("-I");
        cmd.add(project.getBasePath()+"/src");
        for (VirtualFile file : subdirs) {cmd.add("-I"); cmd.add(file.getPath());}
        for (VirtualFile file : files) cmd.add(file.getPath());

        File dir = new File(project.getBasePath()+"/src");



        Process p = null;
        try {
            p = runtime.exec(cmd.toArray(new String[0]), new String[0], dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader processOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader processError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        try {
            if (p.waitFor() > 0) System.out.println("Sort Error");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> lines = new ArrayList<>();
        String str;
        try {
            while ((str = processOutput.readLine()) != null)
                lines.add(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List <CoqFileDescriptor> coqFileDescriptors = new ArrayList<>();

        for (VirtualFile vf : vfiles) {
            List<String> dep = new ArrayList<>();
            for (String str2 : lines) {
                String[] parts = str2.split(":");
                String[] left = parts[0].split(" ");
                String[] right = parts[1].split(" ");
                Pattern pattern = Pattern.compile("[^.]*.vo");
                for (String a : left) {
                    if (vf.getPath().equals(a.substring(0, a.length() - 1))) {
                        for (String b : right) {
                            Matcher matcher2 = pattern.matcher(b);
                            if (matcher2.matches()){
                                dep.add(b.substring(0, b.length() - 1));
                            }
                        }
                    }
                }
            }
            coqFileDescriptors.add(new CoqFileDescriptor(vf.getPath(), dep));
        }

      /*  for (CoqFileDescriptor cf : coqFileDescriptors) {
            System.out.print(cf.myPath + " : ");
            for (String str2 : cf.myDependencies){
                System.out.print(str2 + " ");
            }
            System.out.println("");
        }*/
        return coqFileDescriptors;
    }


    public static void addPrepareDependenciesFailedMessage(@NotNull CompileContext context) {
        context.addMessage(CompilerMessageCategory.WARNING, "Failed to submit dependencies info to compiler.", null, -1, -1);
    }



}
