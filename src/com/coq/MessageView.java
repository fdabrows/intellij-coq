package com.coq;

import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
import com.coq.errors.NoCoqProcess;
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

/**
 * Created by dabrowski on 28/12/2015.
 */
public class MessageView implements ToolWindowFactory, MessageTextListener, CoqStateListener {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JTextField textField1;
    private JTextArea textArea2;
    private ToolWindow myToolWindow;

    private Project project;


    public MessageView() {
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