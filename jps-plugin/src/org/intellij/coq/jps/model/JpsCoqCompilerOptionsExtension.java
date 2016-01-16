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

package org.intellij.coq.jps.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.JpsProject;
import org.jetbrains.jps.model.ex.JpsCompositeElementBase;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;

/**
 * Created by dabrowski on 15/01/2016.
 */
public class JpsCoqCompilerOptionsExtension extends JpsCompositeElementBase<JpsCoqCompilerOptionsExtension> {

    public static final JpsElementChildRole<JpsCoqCompilerOptionsExtension> ROLE = JpsElementChildRoleBase.create("CoqCompilerOptions");


    private CoqCompilerOptions myOptions;

    public JpsCoqCompilerOptionsExtension(@NotNull CoqCompilerOptions options) {
        myOptions = options;
    }

    @NotNull
    @Override
    public JpsCoqCompilerOptionsExtension createCopy() {
        System.out.println("JpsCoqCompilerOptionsExtension.createCopy");
        return new JpsCoqCompilerOptionsExtension(new CoqCompilerOptions(myOptions));
    }

    @NotNull
    public CoqCompilerOptions getOptions() {
        return myOptions;
    }

    public void setOptions(CoqCompilerOptions options) {
        myOptions = options;
    }

    @NotNull
    public static JpsCoqCompilerOptionsExtension getOrCreateExtension(@NotNull JpsProject project) {
        JpsCoqCompilerOptionsExtension extension = project.getContainer().getChild(ROLE);
        if (extension == null) {
            extension = project.getContainer().setChild(ROLE, new JpsCoqCompilerOptionsExtension(new CoqCompilerOptions()));
        }
        return extension;
    }
}
