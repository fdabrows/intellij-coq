package com.coq;

import javax.swing.*;

/**
 * Created by dabrowski on 06/01/2016.
 */
public class CoqSettingsEditor {
    private JPanel myPanel;
    private JTextField textField1;
    private JButton button1;

    protected JComponent createEditor() {
        return myPanel;
    }

    public CoqSettings getContent(){
        CoqSettings config = new CoqSettings();
        config.coqbin = textField1.getText();
        return config;
    }

    public void setContent(CoqSettings config){
        textField1.setText(config.coqbin);
    }



}
