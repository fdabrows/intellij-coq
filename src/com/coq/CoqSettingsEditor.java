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
