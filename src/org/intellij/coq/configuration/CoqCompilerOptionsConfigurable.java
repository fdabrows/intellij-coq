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

package org.intellij.coq.configuration;

import com.intellij.compiler.options.CompilerConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by dabrowski on 15/01/2016.
 */
public class CoqCompilerOptionsConfigurable extends CompilerConfigurable {

    private final CoqCompilerSettings mySettings;
    private final Project myProject;
    private JPanel myRootPanel;
    private JTextField textField1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;

    public CoqCompilerOptionsConfigurable(Project project) {
        super(project);
        myProject = project;
        mySettings = CoqCompilerSettings.getInstance(project);
        setupUiListeners();
    }

    private void setupUiListeners() {
    /*    myConfigureRebarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(@NotNull ActionEvent e) {
                DataContext context = DataManager.getInstance().getDataContext(myConfigureRebarButton);
                Settings settings = ObjectUtils.assertNotNull(Settings.KEY.getData(context));
                Configurable configurable = settings.find(ErlangExternalToolsConfigurable.ERLANG_RELATED_TOOLS);
                if (configurable != null) {
                    settings.select(configurable);
                }
            }
        });
        myRootPanel.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                reset();
            }
        });*/
    }

    @NotNull
    @Override
    public String getId() {
        return "Erlang compiler";
    }

    @Override
    public String getDisplayName() {
        return "Erlang Compiler";
    }

    @Override
    public JComponent createComponent() {
        return myRootPanel;
    }

    @Override
    public void reset() {
        /*boolean rebarPathIsSet = StringUtil.isNotEmpty(RebarSettings.getInstance(myProject).getRebarPath());
        myUseRebarCompilerCheckBox.setEnabled(rebarPathIsSet);
        myConfigureRebarButton.setVisible(!rebarPathIsSet);
        myUseRebarCompilerCheckBox.setSelected(rebarPathIsSet && mySettings.isUseRebarCompilerEnabled());
        myAddDebugInfoCheckBox.setSelected(mySettings.isAddDebugInfoEnabled());*/

    }

    @Override
    public void apply() throws ConfigurationException {
      //  mySettings.setUseRebarCompilerEnabled(myUseRebarCompilerCheckBox.isSelected());
      //  mySettings.setAddDebugInfoEnabled(myAddDebugInfoCheckBox.isSelected());
    }

    @Override
    public boolean isModified() {
       /* return myUseRebarCompilerCheckBox.isSelected() != mySettings.isUseRebarCompilerEnabled() ||
                myAddDebugInfoCheckBox.isSelected() != mySettings.isAddDebugInfoEnabled();*/
        return true;
    }
}
