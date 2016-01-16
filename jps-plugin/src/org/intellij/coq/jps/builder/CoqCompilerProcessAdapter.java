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

package org.intellij.coq.jps.builder;

import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.messages.BuildMessage;
import org.jetbrains.jps.incremental.messages.CompilerMessage;

public class CoqCompilerProcessAdapter extends BuilderProcessAdapter {
  public CoqCompilerProcessAdapter(@NotNull CompileContext context,
                                   @NotNull String builderName,
                                   @NotNull String compileTargetRootPath) {
    super(context, builderName, compileTargetRootPath);
  }

  @Override
  public void onTextAvailable(@NotNull ProcessEvent event, Key outputType) {
    showMessage(createCompilerMessage(myBuilderName, myCompileTargetRootPath, event.getText()));
  }

  @NotNull
  public static CompilerMessage createCompilerMessage(@NotNull String builderName,
                                                      @NotNull String compileTargetRootPath,
                                                      @NotNull String text) {
    BuildMessage.Kind kind = BuildMessage.Kind.INFO;
    String messageText = text;
    String sourcePath = null;
    long line = -1L;

    CoqCompilerError error = CoqCompilerError.create(compileTargetRootPath, text);
    if (error != null) {
      boolean isError = error.getCategory() == CompilerMessageCategory.ERROR;
      kind = isError ? BuildMessage.Kind.ERROR : BuildMessage.Kind.WARNING;
      messageText = error.getErrorMessage();
      sourcePath = VirtualFileManager.extractPath(error.getUrl());
      line = error.getLine();
    }
    return new CompilerMessage(builderName, kind, messageText, sourcePath, -1L, -1L, -1L, line, -1L);
  }
}
