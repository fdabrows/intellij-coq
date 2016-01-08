package com.coq.psi;

import com.coq.CoqLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Dabrowski on 27/11/2015.
 */
public class CoqElementType extends IElementType {

    public CoqElementType(@NotNull @NonNls String debugName) {
        super(debugName, CoqLanguage.INSTANCE);

    }
}

