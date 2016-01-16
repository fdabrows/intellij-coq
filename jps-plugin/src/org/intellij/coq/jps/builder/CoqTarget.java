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

import com.intellij.util.containers.ContainerUtil;
import org.intellij.coq.jps.model.JpsCoqModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.*;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.java.JpsJavaClasspathKind;
import org.jetbrains.jps.model.java.JpsJavaExtensionService;
import org.jetbrains.jps.model.module.JpsModule;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CoqTarget extends ModuleBasedTarget<CoqSourceRootDescriptor> {
  private CoqModuleBuildOrder myBuildOrder;

  public CoqTarget(@NotNull JpsModule module, CoqTargetType targetType) {
    super(targetType, module);
  }

  @Override
  public String getId() {
    return myModule.getName();
  }

  @Override
  public Collection<BuildTarget<?>> computeDependencies(BuildTargetRegistry targetRegistry,
                                                        TargetOutputIndex outputIndex) {
    List<BuildTarget<?>> dependencies = ContainerUtil.newArrayList();
    Set<JpsModule> modules = getDependenciesModules();
    for (JpsModule module : modules) {
      if (module.getModuleType().equals(JpsCoqModuleType.INSTANCE)) {
        dependencies.addAll(targetRegistry.getModuleBasedTargets(module, BuildTargetRegistry.ModuleTargetSelector.ALL));
      }
    }
    dependencies.addAll(targetRegistry.getAllTargets(CoqModuleBuildOrderTargetType.INSTANCE));
    return dependencies;
  }

  @NotNull
  public Set<JpsModule> getDependenciesModules() {
    return JpsJavaExtensionService.dependencies(myModule).includedIn(JpsJavaClasspathKind.compile(isTests())).getModules();
  }

  @NotNull
  @Override
  public List<CoqSourceRootDescriptor> computeRootDescriptors(JpsModel model,
                                                              ModuleExcludeIndex index,
                                                              IgnoredFileIndex ignoredFileIndex,
                                                              BuildDataPaths dataPaths) {
    List<CoqSourceRootDescriptor> result = ContainerUtil.newArrayList();
    CoqTargetBuilderUtil.addRootDescriptors(this, myModule, result);
    return result;
  }

  @Nullable
  @Override
  public CoqSourceRootDescriptor findRootDescriptor(String rootId, BuildRootIndex rootIndex) {
    return CoqTargetBuilderUtil.findRootDescriptor(rootId, rootIndex, (CoqTargetType) getTargetType());
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Erlang '" + myModule.getName() + "'";
  }

  @NotNull
  @Override
  public Collection<File> getOutputRoots(CompileContext context) {
    return ContainerUtil.newArrayList(JpsJavaExtensionService.getInstance().getOutputDirectory(myModule, false),
                                      JpsJavaExtensionService.getInstance().getOutputDirectory(myModule, true));
  }

  @Override
  public boolean isTests() {
    return false;
  }

  @Nullable
  public CoqModuleBuildOrder getBuildOrder() {
    return myBuildOrder;
  }

  public void setBuildOrder(@NotNull CoqModuleBuildOrder buildOrder) {
    myBuildOrder = buildOrder;
  }
}
