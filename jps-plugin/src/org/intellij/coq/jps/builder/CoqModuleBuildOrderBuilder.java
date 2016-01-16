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

import com.intellij.openapi.util.Conditions;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.graph.GraphGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildOutputConsumer;
import org.jetbrains.jps.builders.BuildRootIndex;
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.builders.DirtyFilesHolder;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ProjectBuildException;
import org.jetbrains.jps.incremental.TargetBuilder;
import org.jetbrains.jps.incremental.messages.BuildMessage;
import org.jetbrains.jps.incremental.messages.CompilerMessage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.intellij.coq.jps.builder.CoqBuilderUtil.*;

public class CoqModuleBuildOrderBuilder extends TargetBuilder<CoqSourceRootDescriptor, CoqModuleBuildOrderTarget> {
  private static final String NAME = "Build order builder";

  public CoqModuleBuildOrderBuilder() {
    super(Collections.singletonList(CoqModuleBuildOrderTargetType.INSTANCE));
  }

  @Override
  public void build(@NotNull CoqModuleBuildOrderTarget target,
                    @NotNull DirtyFilesHolder<CoqSourceRootDescriptor, CoqModuleBuildOrderTarget> holder,
                    @NotNull BuildOutputConsumer outputConsumer,
                    @NotNull CompileContext context) throws ProjectBuildException, IOException {
    LOG.info("Computing dirty files");
    LOG.debug("Load project build order.");
    CoqProjectBuildOrder projectBuildOrder = loadProjectBuildOrder(context);
    if (projectBuildOrder == null) {
      addPrepareDependenciesFailedMessage(context);
      return;
    }

    setEmptyBuildOrders(context);

    LOG.debug("Collect dirty files.");
    List<String> dirtyErlangFilePaths = new DirtyFilesProcessor<String, CoqModuleBuildOrderTarget>() {
      @Nullable
      @Override
      protected String getDirtyElement(@NotNull File file) throws IOException {
        String fileName = file.getName();
        return isSource(fileName) || isHeader(fileName) ? file.getAbsolutePath() : null;
      }
    }.collectDirtyElements(holder);

    if (dirtyErlangFilePaths.isEmpty()) {
      LOG.debug("There are no dirty .erl or .hrl files.");
    }
    else {
      LOG.debug("Search dirty modules.");
      List<String> sortedDirtyModules = getSortedDirtyModules(projectBuildOrder, dirtyErlangFilePaths);
      addFilesToBuildTarget(context, sortedDirtyModules);
    }
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return NAME;
  }

  private static void setEmptyBuildOrders(@NotNull CompileContext context) {
    List<BuildTarget<?>> allTargets = context.getProjectDescriptor().getBuildTargetIndex().getAllTargets();
    List<BuildTarget<?>> erlangTargets = ContainerUtil.filter(allTargets, Conditions.instanceOf(CoqTarget.class));
    for (BuildTarget<?> erlangTarget : erlangTargets) {
      ((CoqTarget) erlangTarget).setBuildOrder(new CoqModuleBuildOrder());
    }
  }

  @Nullable
  private static CoqProjectBuildOrder loadProjectBuildOrder(@NotNull CompileContext context) {
    return readFromXML(context, BUILD_ORDER_FILE_NAME, CoqProjectBuildOrder.class);
  }

  @NotNull
  private static List<String> getSortedDirtyModules(@NotNull CoqProjectBuildOrder projectBuildOrder,
                                                    @NotNull List<String> dirtyErlangFilePaths) {
    Set<String> allDirtyFiles = getAllDirtyFiles(projectBuildOrder, dirtyErlangFilePaths);
    return getSortedDirtyModules(projectBuildOrder.myErlangFiles, allDirtyFiles);
  }

  private static void addPrepareDependenciesFailedMessage(@NotNull CompileContext context) {
    context.processMessage(new CompilerMessage(NAME, BuildMessage.Kind.WARNING, "The project will be fully rebuilt due to errors."));
  }

  @NotNull
  private static Set<String> getAllDirtyFiles(@NotNull CoqProjectBuildOrder projectBuildOrder,
                                              @NotNull List<String> dirtyFiles) {
    SortedModuleDependencyGraph graph = new SortedModuleDependencyGraph(projectBuildOrder);
    Set<String> allDirtyFiles = ContainerUtil.newHashSet();
    for (String node : dirtyFiles) {
      findDirtyFiles(node, GraphGenerator.create(graph), allDirtyFiles);
    }
    return allDirtyFiles;
  }

  private static void findDirtyFiles(@NotNull String filePath,
                                     @NotNull GraphGenerator<String> dependenciesGraph,
                                     @NotNull Set<String> dirtyFiles) {
    if (dirtyFiles.contains(filePath)) return;
    dirtyFiles.add(filePath);
    Iterator<String> dependentFilesIterator = dependenciesGraph.getOut(filePath);
    while (dependentFilesIterator.hasNext()) {
      findDirtyFiles(dependentFilesIterator.next(), dependenciesGraph, dirtyFiles);
    }
  }

  @NotNull
  private static List<String> getSortedDirtyModules(@NotNull List<CoqFileDescriptor> sortedFiles,
                                                    @NotNull final Set<String> allDirtyFiles) {
    return ContainerUtil.mapNotNull(sortedFiles, new Function<CoqFileDescriptor, String>() {
      @Nullable
      @Override
      public String fun(CoqFileDescriptor node) {
        return isSource(node.myPath) && allDirtyFiles.contains(node.myPath) ? node.myPath : null;
      }
    });
  }

  private static void addFilesToBuildTarget(@NotNull CompileContext context,
                                            @NotNull List<String> sortedDirtyErlangModules) {
    List<CoqTargetType> targetTypes = Collections.singletonList(CoqTargetType.INSTANCE);
    BuildRootIndex buildRootIndex = context.getProjectDescriptor().getBuildRootIndex();
    for (String filePath : sortedDirtyErlangModules) {
      CoqSourceRootDescriptor root = buildRootIndex.findParentDescriptor(new File(filePath), targetTypes, context);
      if (root == null) {
        LOG.error("Source root not found.");
        return;
      }
      CoqTarget target = (CoqTarget) root.getTarget();

      CoqModuleBuildOrder buildOrder = target.getBuildOrder();
      if (buildOrder == null) {
        LOG.error("buildOrder for erlang module target are not set.");
        return;
      }

      if (root.isTests()) {
        buildOrder.myOrderedErlangTestFilePaths.add(filePath);
      }
      else {
        buildOrder.myOrderedErlangFilePaths.add(filePath);
      }
    }
  }

  private static class SortedModuleDependencyGraph implements GraphGenerator.SemiGraph<String> {
    private final LinkedHashMap<String, List<String>> myPathsToDependenciesMap = ContainerUtil.newLinkedHashMap();

    public SortedModuleDependencyGraph(@NotNull CoqProjectBuildOrder projectBuildOrder) {
      for (CoqFileDescriptor node : projectBuildOrder.myErlangFiles) {
        myPathsToDependenciesMap.put(node.myPath, node.myDependencies);
      }
    }

    @Override
    public Collection<String> getNodes() {
      return myPathsToDependenciesMap.keySet();
    }

    @Override
    public Iterator<String> getIn(String node) {
      return myPathsToDependenciesMap.get(node).iterator();
    }
  }
}
