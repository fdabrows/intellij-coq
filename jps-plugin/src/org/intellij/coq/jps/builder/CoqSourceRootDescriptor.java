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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildRootDescriptor;
import org.jetbrains.jps.builders.BuildTarget;

import java.io.File;
import java.io.FileFilter;

public class CoqSourceRootDescriptor extends BuildRootDescriptor {
  private final File myRoot;
  private final String myModuleName;
  private final BuildTarget<? extends CoqSourceRootDescriptor> myErlangTarget;
  private final boolean myTests;

  public CoqSourceRootDescriptor(@NotNull File root,
                                 @NotNull BuildTarget<? extends CoqSourceRootDescriptor> erlangTarget,
                                 @NotNull String moduleName,
                                 boolean isTests) {
    myRoot = root;
    myErlangTarget = erlangTarget;
    myModuleName = moduleName;
    myTests = isTests;
  }

  @Override
  public String getRootId() {
    return myRoot.getAbsolutePath();
  }

  @Override
  public File getRootFile() {
    return myRoot;
  }

  @Override
  public BuildTarget<?> getTarget() {
    return myErlangTarget;
  }

  @NotNull
  @Override
  public FileFilter createFileFilter() {
    return new FileFilter() {
      @Override
      public boolean accept(@NotNull File file) {
        String name = file.getName();
        return CoqBuilderUtil.isSource(name) ||
               CoqBuilderUtil.isHeader(name) ||
               CoqBuilderUtil.isAppConfigFileName(name);
      }
    };
  }

  public boolean isTests() {
    return myTests;
  }

  @NotNull
  public String getModuleName() {
    return myModuleName;
  }
}
