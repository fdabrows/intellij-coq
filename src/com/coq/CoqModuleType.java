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

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import sun.security.pkcs11.Secmod;

import javax.swing.*;

public class CoqModuleType extends ModuleType{

    public CoqModuleType(){

        super("Coq");
    }

    @NotNull
    @Override
    public ModuleBuilder createModuleBuilder() {
        //return new CoqProjectWizard();
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "Coq Project";
    }

    @NotNull
    @Override
    public String getDescription() {

        return "Create a new Coq Project";
    }

    @Override
    public Icon getBigIcon() {

        return IconLoader.getIcon("/com/coq/icons/r.png");
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {

        return IconLoader.getIcon("/com/coq/icons/r.png");
    }
}
