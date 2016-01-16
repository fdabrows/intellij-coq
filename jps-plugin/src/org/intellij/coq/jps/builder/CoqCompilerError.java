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

import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoqCompilerError {
  private static final Pattern COMPILER_MESSAGE_PATTERN = Pattern.compile("^(.+?):(?:(\\d+):)?(\\s*Warning:)?\\s*(.+)$");
  private final String myErrorMessage;
  private final String myUrl;
  private final int myLine;
  private final CompilerMessageCategory myCategory;

  private CoqCompilerError(@NotNull String errorMessage,
                           @NotNull String url,
                           int line,
                           @NotNull CompilerMessageCategory category) {
    this.myErrorMessage = errorMessage;
    this.myUrl = url;
    this.myLine = line;
    this.myCategory = category;
  }

  @NotNull
  public String getErrorMessage() {
    return myErrorMessage;
  }

  @NotNull
  public String getUrl() {
    return myUrl;
  }

  public int getLine() {
    return myLine;
  }

  @NotNull
  public CompilerMessageCategory getCategory() {
    return myCategory;
  }

  @Nullable
  public static CoqCompilerError create(@NotNull String rootPath, @NotNull String erlcMessage) {
    Matcher matcher = COMPILER_MESSAGE_PATTERN.matcher(StringUtil.trimTrailing(erlcMessage));
    if (!matcher.matches()) return null;

    String relativeFilePath = FileUtil.toSystemIndependentName(matcher.group(1));
    File path = StringUtil.isEmpty(rootPath) ? new File(relativeFilePath) : new File(FileUtil.toSystemIndependentName(rootPath), relativeFilePath);
    if(!path.exists()) return null;

    String line = matcher.group(2);
    String warning = matcher.group(3);
    String details = matcher.group(4);
    return createCompilerError(path.getPath(), line, warning, details);
  }

  @NotNull
  private static CoqCompilerError createCompilerError(@NotNull String filePath,
                                                      @Nullable String line,
                                                      @Nullable String warning,
                                                      @NotNull String details) {
    int lineNumber = StringUtil.parseInt(line, -1);
    CompilerMessageCategory category = warning != null ? CompilerMessageCategory.WARNING : CompilerMessageCategory.ERROR;
    return new CoqCompilerError(details, VfsUtilCore.pathToUrl(filePath), lineNumber, category);
  }
}
