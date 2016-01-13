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

package org.intellij.coq.compiler;

import org.intellij.coq.CoqFileType;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dabrowski on 11/01/2016.
 */


public class CoqCompiler extends CoqBaseCompiler implements SourceInstrumentingCompiler {
    @NotNull
    @Override
    public ProcessingItem[] getProcessingItems(CompileContext context) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(true);
        progressIndicator.setText("Preparing files for compiling...");

        VirtualFile[] files = context.getCompileScope().getFiles(CoqFileType.INSTANCE, true);

        List<VirtualFile> vfiles = Arrays.asList(files);
        VirtualFile base = context.getProject().getBaseDir().findChild("src");
        List<VirtualFile> subdirs = getSubdirs(base);
        try {
            List<VirtualFile> todo = getDependencies(context.getProject(), new ArrayList<>(Arrays.asList(files)), subdirs);
            final ArrayList<ProcessingItem> items = new ArrayList<ProcessingItem>();
            for (final VirtualFile file : todo) {
                items.add(new CoqProcessingItem(file, new CoqValidityState(new TimestampValidityState(file.getTimeStamp()))));
            }
            return items.toArray(new CoqProcessingItem[items.size()]);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProcessingItem[] process(CompileContext context, ProcessingItem[] processingItems) {

        final ArrayList<ProcessingItem> processedItems = new ArrayList<ProcessingItem>();
        VirtualFile base = context.getProject().getBaseDir().findChild("src");
        List<VirtualFile> subdirs = getSubdirs(base);

        for (ProcessingItem item : processingItems){
            VirtualFile file = item.getFile();
            try {
                compile(context.getProject(), file, subdirs);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return new ProcessingItem[0];
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
