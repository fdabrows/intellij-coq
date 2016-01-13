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

package org.intellij.coq.toolWindow;

import org.intellij.coq.errors.InvalidPrompt;
import org.intellij.coq.listeners.ProofTextListener;
import org.intellij.coq.errors.InvalidState;
import org.intellij.coq.toplevel.CoqtopEngine;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.io.IOException;

public class ProofView implements ToolWindowFactory, ProofTextListener {
    private JTextArea textArea1;
    private JPanel panel1;

    private ToolWindow myToolWindow;


    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel1, "", false);
        myToolWindow.getContentManager().addContent(content);
        // A ajouter a chaque editor
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        CoqtopEngine coqtopEngine = null;
        try {
            coqtopEngine = CoqtopEngine.getEngine(editor);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidPrompt invalidPrompt) {
            invalidPrompt.printStackTrace();
        } catch (InvalidState invalidState) {
            invalidState.printStackTrace();
        }
        if (coqtopEngine != null) coqtopEngine.addProofViewListener(this);
        else CoqtopEngine.proofView = this;
        //coqtopEngine.addInfoViewListener(this);
    }

    private void createUIComponents() {
    }

    @Override
    public void proofViewChangee(String txt) {
        textArea1.setText(txt);
    }


}
