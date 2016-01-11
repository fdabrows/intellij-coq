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

package com.coq.compiler;

import com.coq.CoqFileType;
import com.intellij.compiler.impl.CompilerContentIterator;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleFileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dabrowski on 11/01/2016.
 */
public class CoqCompiler implements SourceInstrumentingCompiler {
    @NotNull
    @Override
    public ProcessingItem[] getProcessingItems(CompileContext context) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(true);
        progressIndicator.setText("Preparing files for compiling...");

        final ArrayList<ProcessingItem> items = new ArrayList<ProcessingItem>();

        final Project project = context.getProject();
        final ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        final Module[] modules =ModuleManager.getInstance(project).getModules();
        for (final Module module : modules){
            final ModuleFileIndex fileIndex = ModuleRootManager.getInstance(module).getFileIndex();
            final Collection<VirtualFile> files = new ArrayList<VirtualFile>();
            fileIndex.iterateContent(new CompilerContentIterator(null, fileIndex, true, files));
            for (final VirtualFile file : files) {
                items.add(new CoqProcessingItem(file, new CoqValidityState(new TimestampValidityState(file.getTimeStamp()))));
            }
        }
        return items.toArray(new CoqProcessingItem[items.size()]);
    }



    @Override
    public ProcessingItem[] process(CompileContext context, ProcessingItem[] processingItems) {

        final ArrayList<ProcessingItem> processedItems = new ArrayList<ProcessingItem>();

        for (ProcessingItem item : processingItems){
            final VirtualFile file = item.getFile();
            final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(context.getProject()).getFileIndex();
            final VirtualFile destDir = getDestination(file, fileIndex);
            Sdk projectSdk = ProjectRootManager.getInstance(context.getProject()).getProjectSdk();

            String proc = projectSdk.getHomePath();

            System.out.println(proc+"/coqc " + file.getName());

        }
        return new ProcessingItem[0];
    }

    private VirtualFile getDestination(@NotNull final VirtualFile file, @NotNull final ProjectFileIndex fileIndex) {
        final VirtualFile sourcesDir = file.getParent();
        final VirtualFile sourceRoot = fileIndex.getSourceRootForFile(sourcesDir);
        final VirtualFile destRoot = sourceRoot;

        return null;
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Coq Compiler";
    }

    @Override
    public boolean validateConfiguration(CompileScope compileScope) {
        return true;
    }

    @Override
    public ValidityState createValidityState(DataInput dataInput) throws IOException {
        return null;
    }
}
