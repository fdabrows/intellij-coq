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

package org.intellij.coq.jps.rebar;

import org.intellij.coq.jps.builder.CoqSourceRootDescriptor;
import org.intellij.coq.jps.builder.CoqTarget;
import org.intellij.coq.jps.builder.CoqTargetType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildOutputConsumer;
import org.jetbrains.jps.builders.DirtyFilesHolder;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.ProjectBuildException;
import org.jetbrains.jps.incremental.TargetBuilder;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by dabrowski on 16/01/2016.
 */
public class RebarBuilder extends TargetBuilder<CoqSourceRootDescriptor, CoqTarget> {

    public RebarBuilder() {
        super(Collections.singleton(CoqTargetType.INSTANCE));
    }

    @Override
    public void build(@NotNull CoqTarget coqTarget, @NotNull DirtyFilesHolder<CoqSourceRootDescriptor, CoqTarget> dirtyFilesHolder, @NotNull BuildOutputConsumer buildOutputConsumer, @NotNull CompileContext compileContext) throws ProjectBuildException, IOException {
        System.out.println("Build rebar");
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return null;
    }
}
