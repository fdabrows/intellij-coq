package com.coq;

import com.intellij.openapi.editor.Editor;

import java.util.EventListener;

/**
 * Created by dabrowski on 23/12/2015.
 */
public interface CoqStateListener extends EventListener {
    void coqStateChangee(CoqState c);
}
