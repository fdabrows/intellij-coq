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
import org.intellij.coq.jps.model.CoqIncludeSourceRootType;
import org.intellij.coq.jps.model.JpsCoqSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildRootIndex;
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ProjectBuildException;
import org.jetbrains.jps.incremental.messages.BuildMessage;
import org.jetbrains.jps.incremental.messages.CompilerMessage;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.library.sdk.JpsSdk;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.module.JpsTypedModuleSourceRoot;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CoqTargetBuilderUtil {
  private CoqTargetBuilderUtil() {
  }

  @NotNull
  public static JpsSdk<JpsDummyElement> getSdk(@NotNull CompileContext context,
                                               @NotNull JpsModule module) throws ProjectBuildException {
    JpsSdk<JpsDummyElement> sdk = module.getSdk(JpsCoqSdkType.INSTANCE);
    if (sdk == null) {
      String errorMessage = "No SDK for module " + module.getName();
      context.processMessage(new CompilerMessage(CoqBuilder.NAME, BuildMessage.Kind.ERROR, errorMessage));
      throw new ProjectBuildException(errorMessage);
    }
    return sdk;
  }

  public static void addRootDescriptors(BuildTarget<CoqSourceRootDescriptor> target,
                                        JpsModule jpsModule,
                                        List<CoqSourceRootDescriptor> result) {
    for (JpsTypedModuleSourceRoot<JavaSourceRootProperties> root : jpsModule.getSourceRoots(JavaSourceRootType.SOURCE)) {
      result.add(new CoqSourceRootDescriptor(root.getFile(), target, jpsModule.getName(), false));
    }
    for (JpsTypedModuleSourceRoot<JavaSourceRootProperties> root : jpsModule.getSourceRoots(JavaSourceRootType.TEST_SOURCE)) {
      result.add(new CoqSourceRootDescriptor(root.getFile(), target, jpsModule.getName(), true));
    }
    for (JpsTypedModuleSourceRoot<JpsDummyElement> root : jpsModule.getSourceRoots(CoqIncludeSourceRootType.INSTANCE)) {
      result.add(new CoqSourceRootDescriptor(root.getFile(), target, jpsModule.getName(), false));
    }
  }

  @Nullable
  public static CoqSourceRootDescriptor findRootDescriptor(String rootId,
                                                           BuildRootIndex rootIndex,
                                                           BuildTargetType<? extends BuildTarget<CoqSourceRootDescriptor>> targetType) {
    return ContainerUtil.getFirstItem(rootIndex.getRootDescriptors(new File(rootId), Collections.singletonList(targetType), null));
  }
}
