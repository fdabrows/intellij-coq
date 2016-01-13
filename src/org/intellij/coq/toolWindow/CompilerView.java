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

import org.intellij.coq.compiler.CompilerTextListener;
import org.intellij.coq.compiler.CoqBaseCompiler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by dabrowski on 12/01/2016.
 */
public class CompilerView implements ToolWindowFactory, CompilerTextListener {
    private JTextArea textArea1;
    private JPanel panel1;
    private ToolWindow myToolWindow;


    @Override
    public void compilerTextChangee(String txt) {
        textArea1.setText(textArea1.getText()+"\n" + txt);
    }

    public void addTextln(String txt){
        addText(txt);
    }


    public void addText(String txt){
        textArea1.setText(textArea1.getText() +"\n"+ txt);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel1, "", false);
        myToolWindow.getContentManager().addContent(content);
        CoqBaseCompiler.compilerView=this;

    }
}
