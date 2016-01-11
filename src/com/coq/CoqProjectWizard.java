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
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoqProjectWizard extends ModuleBuilder {

    private List<Pair<String,String>> mySourcePaths;

    public void setupRootModel(ModifiableRootModel modifiableRootModel) throws ConfigurationException {

        ContentEntry contentEntry = doAddContentEntry(modifiableRootModel);
        if (contentEntry != null) {
            final List<Pair<String,String>> sourcePaths = getSourcePaths();

            if (sourcePaths != null) {
                for (final Pair<String, String> sourcePath : sourcePaths) {
                    String first = sourcePath.first;
                    new File(first).mkdirs();
                    final VirtualFile sourceRoot = LocalFileSystem.getInstance()
                            .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first));
                    if (sourceRoot != null) {
                        contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second);
                    }
                }
            }
        }
    }

    public List<Pair<String,String>> getSourcePaths() {
        if (mySourcePaths == null) {
            final List<Pair<String, String>> paths = new ArrayList<Pair<String, String>>();
            @NonNls final String path = getContentEntryPath() + File.separator + "src";
            new File(path).mkdirs();
            paths.add(Pair.create(path, ""));
            return paths;
        }
        return mySourcePaths;
    }

    public void setSourcePaths(List<Pair<String,String>> sourcePaths) {
        mySourcePaths = sourcePaths != null? new ArrayList<Pair<String, String>>(sourcePaths) : null;
    }

    public void addSourcePath(Pair<String,String> sourcePathInfo) {
        if (mySourcePaths == null) {
            mySourcePaths = new ArrayList<Pair<String, String>>();
        }
        mySourcePaths.add(sourcePathInfo);
    }

    public ModuleType getModuleType() {

        return new CoqModuleType();
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{};
    }
}
