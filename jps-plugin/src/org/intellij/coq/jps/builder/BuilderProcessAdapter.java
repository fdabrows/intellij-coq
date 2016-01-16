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

import com.intellij.execution.process.ProcessAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.incremental.messages.CompilerMessage;


public class BuilderProcessAdapter extends ProcessAdapter {
  private final CompileContext myContext;
  protected final String myBuilderName;
  protected final String myCompileTargetRootPath;

  protected BuilderProcessAdapter(@NotNull CompileContext context,
                                  @NotNull String builderName,
                                  @NotNull String compileTargetRootPath) {
    myContext = context;
    myBuilderName = builderName;
    myCompileTargetRootPath = compileTargetRootPath;
  }

  protected void showMessage(@NotNull CompilerMessage message) {
    myContext.processMessage(message);
  }
}
