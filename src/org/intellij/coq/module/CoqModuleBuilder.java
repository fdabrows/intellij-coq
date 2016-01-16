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

package org.intellij.coq.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CoqModuleBuilder extends ModuleBuilder {

    public ModuleType getModuleType() {

        return CoqModuleType.INSTANCE;
    }

    public void setupRootModel(ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        String srcpath = getContentEntryPath() + File.separator + "src";
        new File(srcpath).mkdirs();
        String docpath = getContentEntryPath() + File.separator + "doc";
        new File(docpath).mkdirs();
        String extpath = getContentEntryPath() + File.separator + "ext";
        new File(extpath).mkdirs();
        ContentEntry contentEntry = doAddContentEntry(modifiableRootModel);
        if (contentEntry != null) {
            final VirtualFile sourceRoot = LocalFileSystem.getInstance()
                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(srcpath));
            final VirtualFile docRoot = LocalFileSystem.getInstance()
                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(docpath));
            final VirtualFile extRoot = LocalFileSystem.getInstance()
                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(extpath));
            if (sourceRoot != null && docRoot != null && extpath != null) {
                contentEntry.addSourceFolder(sourceRoot, false, "");
                contentEntry.addExcludeFolder(docRoot);
                contentEntry.addExcludeFolder(extRoot);
            }
        }
        modifiableRootModel.inheritSdk();
        //super.setupRootModel(modifiableRootModel);
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{};
    }
}
