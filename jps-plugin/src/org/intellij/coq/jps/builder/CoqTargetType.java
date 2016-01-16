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

import org.intellij.coq.jps.model.JpsCoqModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildTargetLoader;
import org.jetbrains.jps.builders.ModuleBasedBuildTargetType;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.module.JpsTypedModule;

import java.util.ArrayList;
import java.util.List;

public class CoqTargetType extends ModuleBasedBuildTargetType<CoqTarget> {
  public static final CoqTargetType INSTANCE = new CoqTargetType();
  public static final String TYPE_ID = "erlang modules";

  private CoqTargetType() {
    super(TYPE_ID);
  }

  @NotNull
  @Override
  public List<CoqTarget> computeAllTargets(@NotNull JpsModel model) {
    List<CoqTarget> targets = new ArrayList<CoqTarget>();
    for (JpsTypedModule<JpsDummyElement> module : model.getProject().getModules(JpsCoqModuleType.INSTANCE)) {
      targets.add(new CoqTarget(module, this));
    }
    return targets;
  }

  @NotNull
  @Override
  public BuildTargetLoader<CoqTarget> createLoader(@NotNull final JpsModel model) {
    return new BuildTargetLoader<CoqTarget>() {
      @Nullable
      @Override
      public CoqTarget createTarget(@NotNull String targetId) {
        for (JpsTypedModule<JpsDummyElement> module : model.getProject().getModules(JpsCoqModuleType.INSTANCE)) {
          if (module.getName().equals(targetId)) {
            return new CoqTarget(module, CoqTargetType.this);
          }
        }
        return null;
      }
    };
  }
}
