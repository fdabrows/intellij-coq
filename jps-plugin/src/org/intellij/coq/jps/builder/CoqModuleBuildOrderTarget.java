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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildRootIndex;
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.builders.BuildTargetRegistry;
import org.jetbrains.jps.builders.TargetOutputIndex;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.module.JpsModule;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class CoqModuleBuildOrderTarget extends BuildTarget<CoqSourceRootDescriptor> {
  private static final String NAME = "prepare files target";

  private final JpsProject myProject;

  public CoqModuleBuildOrderTarget(@NotNull JpsProject project,
                                   @NotNull CoqModuleBuildOrderTargetType targetType) {
    super(targetType);
    myProject = project;
  }

  @Override
  public String getId() {
    return myProject.getName();
  }

  @Override
  public Collection<BuildTarget<?>> computeDependencies(BuildTargetRegistry targetRegistry,
                                                        TargetOutputIndex outputIndex) {
    return ContainerUtil.emptyList();
  }

  @NotNull
  @Override
  public List<CoqSourceRootDescriptor> computeRootDescriptors(JpsModel model,
                                                              ModuleExcludeIndex index,
                                                              IgnoredFileIndex ignoredFileIndex,
                                                              BuildDataPaths dataPaths) {
    if (model == null) {
      return ContainerUtil.emptyList();
    }
    List<CoqSourceRootDescriptor> result = ContainerUtil.newArrayList();
    for (JpsModule module : model.getProject().getModules()) {
      CoqTargetBuilderUtil.addRootDescriptors(this, module, result);
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CoqModuleBuildOrderTarget)) return false;

    CoqModuleBuildOrderTarget that = (CoqModuleBuildOrderTarget) o;

    if (!myProject.equals(that.myProject)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return myProject.hashCode();
  }

  @Nullable
  @Override
  public CoqSourceRootDescriptor findRootDescriptor(String rootId, BuildRootIndex rootIndex) {
    return CoqTargetBuilderUtil.findRootDescriptor(rootId,
                                                      rootIndex,
                                                      (CoqModuleBuildOrderTargetType) getTargetType());
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return NAME;
  }

  @NotNull
  @Override
  public Collection<File> getOutputRoots(@NotNull CompileContext context) {
    return ContainerUtil.emptyList();
  }

  @NotNull
  public JpsProject getProject() {
    return myProject;
  }
}
