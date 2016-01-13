
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

package org.intellij.coq;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CoqFileType extends LanguageFileType {

    public static final CoqFileType INSTANCE = new CoqFileType();

    private CoqFileType() {
        super(CoqLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Coq File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Coq language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "v";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return CoqIcons.FILE;
    }

    @Override
    public String getCharset(@NotNull VirtualFile file, byte[] content) {
        return "UTF8";
    }
}
