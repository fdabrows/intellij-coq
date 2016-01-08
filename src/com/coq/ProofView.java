package com.coq;

import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
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

/**
 * Created by dabrowski on 28/12/2015.
 */
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
