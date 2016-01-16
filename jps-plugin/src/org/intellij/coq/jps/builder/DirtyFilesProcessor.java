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
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.builders.DirtyFilesHolder;
import org.jetbrains.jps.builders.FileProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class DirtyFilesProcessor<T, P extends BuildTarget<CoqSourceRootDescriptor>> implements FileProcessor<CoqSourceRootDescriptor, P> {
  private final List<T> myDirtyElements = ContainerUtil.newArrayList();

  public List<T> collectDirtyElements(@NotNull DirtyFilesHolder<CoqSourceRootDescriptor, P> holder) throws IOException {
    holder.processDirtyFiles(this);
    return myDirtyElements;
  }

  @Override
  public boolean apply(P erlangTarget,
                       File file,
                       CoqSourceRootDescriptor erlangSourceRootDescriptor) throws IOException {
    ContainerUtil.addIfNotNull(myDirtyElements, getDirtyElement(file));
    return true;
  }

  @Nullable
  protected abstract T getDirtyElement(@NotNull File file) throws IOException;
}
