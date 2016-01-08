package com.coq.psi;

import com.coq.CoqFileType;
import com.coq.CoqLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by Dabrowski on 27/11/2015.
 */
public class CoqFile extends PsiFileBase {

    public CoqFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CoqLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CoqFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
