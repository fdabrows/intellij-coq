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
import org.intellij.coq.errors.InvalidState;
import org.intellij.coq.errors.NoCoqProcess;
import org.intellij.coq.listeners.MessageTextListener;
import org.intellij.coq.toplevel.CoqState;
import org.intellij.coq.toplevel.CoqStateListener;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CoqMessageView implements ToolWindowFactory, MessageTextListener, CoqStateListener {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JTextField textField1;
    private ToolWindow myToolWindow;

    private Project project;


    public CoqMessageView() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    if (coqtopEngine == null) return;
                    coqtopEngine.next();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NoCoqProcess noCoqProcess) {
                    noCoqProcess.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    coqtopEngine.undo();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NoCoqProcess noCoqProcess) {
                    noCoqProcess.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    coqtopEngine.use();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NoCoqProcess noCoqProcess) {
                    noCoqProcess.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    coqtopEngine.retract();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NoCoqProcess noCoqProcess) {
                    noCoqProcess.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    coqtopEngine.gotoCursor();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    CoqtopEngine coqtopEngine = CoqtopEngine.getEngine(editor);
                    coqtopEngine.stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvalidPrompt invalidPrompt) {
                    invalidPrompt.printStackTrace();
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                } catch (InvalidState invalidState) {
                    invalidState.printStackTrace();
                }
            }
        });
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow)  {
        this.project = project;
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
        if (coqtopEngine != null) {
            coqtopEngine.addMessageViewListener(this);
            coqtopEngine.addCoqStateListener(this);
        }
        else CoqtopEngine.messageView = this;


    }

    private void createUIComponents() {
    }

    @Override
    public void messageViewChangee(String txt) {
        textArea1.setText(txt);
    }


    @Override
    public void coqStateChangee(CoqState c) {
        textField1.setText(c.toString());
    }
}
