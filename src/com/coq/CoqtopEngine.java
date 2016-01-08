package com.coq;

import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
import com.coq.errors.NoCoqProcess;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.indexing.FileContentImpl;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoqtopEngine implements CoqStateListener {

    // With every editor, we associate an engine which is recorded
    // in the user data of the contained document. The key editorKey
    // is used to manage access to this editor through method
    // getEngine. At any time, at most one engine can be running,
    // this engine is stored in running

    private static final Key<CoqtopEngine> editorKey = new Key<CoqtopEngine>("engine");
    private static CoqtopEngine running = null;


    public static CoqtopEngine getEngine(Editor editor) throws IOException, InvalidPrompt, InvalidState {

        CoqtopEngine coqtopEngine = editor.getDocument().getUserData(editorKey);
        if (coqtopEngine != null && coqtopEngine == running) return running;
        if (running != null) {
            int retour = JOptionPane.showConfirmDialog(null, "Editor change", "Editor change", JOptionPane.OK_CANCEL_OPTION);
            if (retour == JOptionPane.CANCEL_OPTION) return null;
            running.stop();
        }
        if (coqtopEngine == null) {
            try {
                running = new CoqtopEngine(editor);
                editor.getDocument().putUserData(editorKey, running);
                return running;
            } catch (CoqtopPathError coqtopPathError) {
                JOptionPane.showMessageDialog(null, "Set coq path!");
                coqtopPathError.printStackTrace();
                return null;
            }
        } else throw new InvalidState();
    }

    private final EventListenerList listeners = new EventListenerList();

    // TODO : find another way to record listeners and remove these two fields
    public static ProofTextListener proofView;
    public static MessageView messageView;

    private String messageText, proofText;
    private final List<CoqState> coqStates = new ArrayList<>();
    private CoqTopLevel proofTopLevel;


    // View
    private Editor editor;

    private CoqtopEngine(Editor editor) throws IOException, InvalidPrompt, CoqtopPathError {

        CoqTopLevelResponse response;

        this.editor = editor;

        proofTopLevel = CoqTopLevel.getCoqTopLevel();
        response = proofTopLevel.start();
        coqStates.add(new CoqState(response.prompt, 0));


        // TODO : FIND ANOTHER WAY TO RECORD LISTENERS
        addCoqStateListener(this);
        if (proofView != null) addProofViewListener(proofView);
        if (messageView != null) {
            addMessageViewListener(messageView);
            addCoqStateListener(messageView);
        }
    }


    private void setMessageText(String str){
        messageText = str;
        fireMessageViewChanged();
    }

    private void setProofText(String str){
        proofText = str;
        fireProofViewChanged();
    }

    public void saveCoqState(CoqState c){
        if (coqStates.get(0).proofCounter != 0 && c.proofCounter == 0)
            while (coqStates.get(0).proofCounter != 0)
                coqStates.remove(0);
        coqStates.add(0, c);
        fireCoqStateChanged();
    }

    public void restoreCoqState(int counter){
        assert counter >= 1;
        while (coqStates.get(0).globalCounter != counter)
            coqStates.remove(0);
        fireCoqStateChanged();
    }

    public CoqState currentCoqState(){
        return coqStates.get(0);
    }

    private static boolean isCommand (String str){
        int cpt = 0;
        for (int i =0; i < str.length() - 1; i++){
            if (str.charAt(i) == '(' && str.charAt(i+1) == '*') cpt++;
            if (str.charAt(i) == '*' && str.charAt(i+1) == ')') cpt--;
        }
        return (cpt == 0);
    }


    public boolean next() throws IOException, NoCoqProcess, InvalidPrompt {

        Document document = editor.getDocument();

        String txt = document.getText();

        int startOffset = currentCoqState().offset;
        int endOffset = txt.indexOf('.', startOffset) + 1;

        if (endOffset <= 0) {
            setMessageText("No command to proceed");
            setProofText("");
            return false;
        }

        String cmd = document.getText(new TextRange(startOffset, endOffset)).replace('\n', ' ');
        int searchOffset = startOffset;
        while (true) {
            boolean b = false;
            if (endOffset < document.getText().length()){
                b = b || document.getText().charAt(endOffset) == '\n';
                b = b || document.getText().charAt(endOffset) == '\r';
                b = b || document.getText().charAt(endOffset) == '\t';
                b = b || document.getText().charAt(endOffset) == ' ';
            }
            if (isCommand(cmd) && b) break;

            searchOffset = endOffset + 1;
            endOffset = txt.indexOf('.', searchOffset) + 1;
            if (endOffset <= 0) {
                setMessageText("No command to proceed");
                setProofText("");
                return false;
            }
            cmd = document.getText(new TextRange(startOffset, endOffset)).replace('\n', ' ');
        }
        CoqTopLevelResponse response = proofTopLevel.send(cmd);

        CoqTopLevelPrompt prompt = response.prompt;

        if (prompt.getGlobalCounter() == currentCoqState().globalCounter) {

        }
            if (prompt.getGlobalCounter() > currentCoqState().globalCounter) {
            saveCoqState(new CoqState(prompt, endOffset));

        } else if (prompt.getGlobalCounter() < currentCoqState().globalCounter){
            restoreCoqState(prompt.getGlobalCounter());
        } else {
            // TODO : Put in red
            setMessageText(response.message.message());
            setProofText("");
            return false;
        }
        if (response.prompt.getProofCounter() == 0) { // TODO : METHODE DE TEST KIND DANS RESPONSE
            setMessageText(response.message.message());
            setProofText("");
        }
        else {
            setMessageText("");
            setProofText(response.message.message());
        }
        return true;
    }


    public void undo() throws NoCoqProcess, IOException, InvalidPrompt {

        if (coqStates.size() == 1)  return;

        int lastState = coqStates.get(1).globalCounter;
        CoqTopLevelResponse response = proofTopLevel.backTo(lastState);
        if (response.prompt.getProofCounter() == 0) // TODO : METHODE DE TEST KIND DANS RESPONSE
        // TODO : MAUVAIS CRITERE LE COMPTEUR PEUT ETRE nul EN CAS D'erreur dans une preuve
            setMessageText(response.message.message());
        else setProofText(response.message.message());
        restoreCoqState(response.prompt.getGlobalCounter());
    }

    public void use() throws NoCoqProcess, IOException, InvalidPrompt {
        while (next());
    }

    public void retract() throws NoCoqProcess, IOException, InvalidPrompt {
        CoqTopLevelResponse response = proofTopLevel.backTo(1);
        if (response.prompt.getProofCounter() == 0) // TODO : METHODE DE TEST KIND DANS RESPONSE
            setMessageText(response.message.message());
        else setProofText(response.message.message());
        restoreCoqState(response.prompt.getGlobalCounter());
    }

    public void gotoCursor(){

    }

    public void stop() throws IOException {
        proofTopLevel.stop();
        editor.getMarkupModel().removeAllHighlighters();
        editor.getDocument().putUserData(editorKey, null);
    }



    @Override
    public void coqStateChangee(CoqState c) {
        TextAttributes attr = new TextAttributes();
        attr.setBackgroundColor(UIUtil.getTreeSelectionBackground());
        attr.setForegroundColor(UIUtil.getTreeSelectionForeground());
        editor.getMarkupModel().removeAllHighlighters();
        editor.getMarkupModel().addRangeHighlighter(
                0,c.offset, 3333, attr, HighlighterTargetArea.EXACT_RANGE);
        RangeMarker marker = editor.getDocument().getOffsetGuard(0);
        if (marker != null) editor.getDocument().removeGuardedBlock(marker);
        editor.getDocument().createGuardedBlock(0, c.offset);
    }

    public void addCoqStateListener(CoqStateListener listener){
        listeners.add(CoqStateListener.class, listener);
    }

    public void addProofViewListener(ProofTextListener listener){
        listeners.add(ProofTextListener.class, listener);
    }


    public void addMessageViewListener(MessageTextListener listener){
        listeners.add(MessageTextListener.class, listener);
    }

    private void fireProofViewChanged(){
        for (ProofTextListener listener : listeners.getListeners(ProofTextListener.class)){
            listener.proofViewChangee(proofText);
        }
    }

    private void fireMessageViewChanged(){
        for (MessageTextListener listener : listeners.getListeners(MessageTextListener.class)){
            listener.messageViewChangee(messageText);
        }
    }

    public void fireCoqStateChanged(){
        for (CoqStateListener listener : listeners.getListeners(CoqStateListener.class)){
            listener.coqStateChangee(currentCoqState());
        }
    }
}

