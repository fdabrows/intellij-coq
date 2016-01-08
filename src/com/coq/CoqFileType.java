package com.coq;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by Dabrowski on 21/11/2015.
 */
public class CoqFileType extends LanguageFileType {

    public static final CoqFileType INSTANCE = new CoqFileType();

    private CoqFileType(){
        super(CoqLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName(){
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

}
