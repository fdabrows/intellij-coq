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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.BuildTargetLoader;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.model.JpsModel;

import java.util.Collections;
import java.util.List;

public class CoqModuleBuildOrderTargetType extends BuildTargetType<CoqModuleBuildOrderTarget> {
  public static final CoqModuleBuildOrderTargetType INSTANCE = new CoqModuleBuildOrderTargetType();
  public static final String TYPE_ID = "erlang_build_order";

  private CoqModuleBuildOrderTargetType() {
    super(TYPE_ID);
  }

  @NotNull
  @Override
  public List<CoqModuleBuildOrderTarget> computeAllTargets(@NotNull JpsModel model) {
    return Collections.singletonList(new CoqModuleBuildOrderTarget(model.getProject(), this));
  }

  @NotNull
  @Override
  public BuildTargetLoader<CoqModuleBuildOrderTarget> createLoader(@NotNull final JpsModel model) {
    return new BuildTargetLoader<CoqModuleBuildOrderTarget>() {
      @Nullable
      @Override
      public CoqModuleBuildOrderTarget createTarget(@NotNull String targetId) {
        if (targetId.equals(model.getProject().getName())) {
          return new CoqModuleBuildOrderTarget(model.getProject(), CoqModuleBuildOrderTargetType.this);
        }
        return null;
      }
    };
  }
}
