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

import com.intellij.codeInsight.template.EverywhereContextType;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dabrowski on 11/01/2016.
 */
public abstract class CoqContextType extends TemplateContextType {


    protected CoqContextType(@NotNull @NonNls String id, @NotNull String presentableName, @Nullable Class<? extends TemplateContextType> baseContextType) {
        super(id, presentableName, baseContextType);
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        if (!PsiUtilCore.getLanguageAtOffset(file, offset).isKindOf(CoqLanguage.INSTANCE)) return false;
        PsiElement element = file.findElementAt(offset);
        if (element instanceof PsiWhiteSpace) {
            return false;
        }
        return element != null && isInContext(element);
    }

    protected abstract boolean isInContext(PsiElement element);

    public static class Generic extends CoqContextType {

        protected Generic() {
            super("COQ_CODE", "Coq", EverywhereContextType.class);
        }

        @Override
        protected boolean isInContext(PsiElement element) {
            return true;
        }
    }
}
