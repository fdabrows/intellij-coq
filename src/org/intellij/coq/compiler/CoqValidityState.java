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

package org.intellij.coq.compiler;

import com.intellij.openapi.compiler.TimestampValidityState;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.vfs.VirtualFile;
import com.sun.istack.internal.NotNull;

import java.io.*;

/**
 * Created by dabrowski on 11/01/2016.
 */
public class CoqValidityState implements ValidityState {

    @NotNull
    private final TimestampValidityState myTimestampValidityState;

    public CoqValidityState(@NotNull final TimestampValidityState timestampValidityState) {
        myTimestampValidityState = timestampValidityState;
    }

    @Override
    public boolean equalsTo(ValidityState otherState) {

        return false;

//        if (otherState == null || !(otherState instanceof CoqValidityState)) {
//            return false;
//        }
//        final CoqValidityState thatState = (CoqValidityState) otherState;
//        return myTimestampValidityState.equalsTo(thatState.myTimestampValidityState);
    }

    @Override
    public void save(DataOutput dataOutput) throws IOException {

    }

    @NotNull
    public static ValidityState load(@NotNull final DataInput in) throws IOException {
        try {
            final TimestampValidityState timestampValidityState = TimestampValidityState.load(in);

                return new CoqValidityState(timestampValidityState);

        } catch (final EOFException e) {
            return NeedRecompilationValidityState.INSTANCE;
        }
    }

    @NotNull
    public static ValidityState create(@NotNull final VirtualFile file,
                                       @NotNull final File compiledFile,
                                       final boolean forceRecompilation) {
        final TimestampValidityState timestampValidityState = new TimestampValidityState(file.getTimeStamp());
        //if (OCamlFileUtil.isOCamlFile(file)) {
            if (forceRecompilation || !compiledFile.exists()) {
                return NeedRecompilationValidityState.INSTANCE;
            }
            return new CoqValidityState(timestampValidityState);
        //}
        //else {
        //    return timestampValidityState;
        //}
    }


    private static class NeedRecompilationValidityState implements ValidityState {
        @NotNull public static final NeedRecompilationValidityState INSTANCE = new NeedRecompilationValidityState();

        public boolean equalsTo(@NotNull final ValidityState otherState) {
            return false;
        }

        public void save(@NotNull final DataOutput out) throws IOException {
        }
    }

}
