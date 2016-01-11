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

package com.coq.compiler;

import com.intellij.openapi.compiler.FileProcessingCompiler;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dabrowski on 11/01/2016.
 */
public class CoqProcessingItem implements FileProcessingCompiler.ProcessingItem{

    @NotNull private final VirtualFile myFile;
    @Nullable private final ValidityState myState;

    public CoqProcessingItem(@NotNull final VirtualFile file, @Nullable final ValidityState state) {
        myFile = file;
        myState = state;
    }

    @NotNull
    @Override
    public VirtualFile getFile() {
        return myFile;
    }

    @Nullable
    @Override
    public ValidityState getValidityState() {
        return myState;
    }
}
