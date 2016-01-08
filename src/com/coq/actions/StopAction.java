
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

package com.coq.actions;

import com.coq.CoqtopEngine;
import com.coq.InvalidState;
import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import java.io.IOException;

/**
 * Created by dabrowski on 05/01/2016.
 */
public class StopAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Project p = e.getProject();
            Editor editor = FileEditorManager.getInstance(p).getSelectedTextEditor();
            CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
            coqtopEngine.stop();
        } catch (IOException ioException){
            ioException.printStackTrace();
        } catch (InvalidPrompt invalidPrompt) {
            System.out.println("Invalid Prompt " + invalidPrompt.str);
            invalidPrompt.printStackTrace();
        } catch (InvalidState invalidState) {
            invalidState.printStackTrace();
        }
    }
}
