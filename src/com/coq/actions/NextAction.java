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

import com.coq.toplevel.CoqtopEngine;
import com.coq.errors.InvalidState;
import com.coq.errors.InvalidPrompt;
import com.coq.errors.NoCoqProcess;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import java.io.IOException;

public class NextAction extends AnAction{

    @Override
    public void actionPerformed(AnActionEvent e){
        try {
            Project p = e.getProject();
            Editor editor = FileEditorManager.getInstance(p).getSelectedTextEditor();
            // Editor my be null if no editor is selected
            if (editor == null) return;
            CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
            if (coqtopEngine == null) return;
            coqtopEngine.next();
        } catch (IOException ioException){
            ioException.printStackTrace();
        } catch (NoCoqProcess noCoqProcess) {
            noCoqProcess.printStackTrace();
        } catch (InvalidPrompt invalidPrompt) {
            invalidPrompt.printStackTrace();
        } catch (InvalidState invalidState) {
            invalidState.printStackTrace();
        }
    }

    @Override
    public void update(final AnActionEvent event)
    {
        DataContext dataContext = event.getDataContext();
        boolean enabled = true; //whatever criteria you have for when it's visible/enabled
        Presentation presentation = event.getPresentation();
        presentation.setVisible(enabled);
        presentation.setEnabled(enabled);
    }
}
