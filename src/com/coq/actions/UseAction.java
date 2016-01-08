package com.coq.actions;

import com.coq.CoqtopEngine;
import com.coq.InvalidState;
import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
import com.coq.errors.NoCoqProcess;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import java.io.IOException;

/**
 * Created by dabrowski on 05/01/2016.
 */
public class UseAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {

        try {
            Project p = e.getProject();
            Editor editor = FileEditorManager.getInstance(p).getSelectedTextEditor();
            CoqtopEngine coqtopEngine = null;
            coqtopEngine = CoqtopEngine.getEngine(editor);
            coqtopEngine.next();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InvalidPrompt invalidPrompt) {
            invalidPrompt.printStackTrace();
        } catch (NoCoqProcess noCoqProcess) {
            noCoqProcess.printStackTrace();
        } catch (InvalidState invalidState) {
            invalidState.printStackTrace();
        }
    }
}
