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

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.incremental.CompileContext;

import java.io.File;
import java.io.IOException;


public class CoqBuilderUtil {
  public static final String BUILDER_DIRECTORY = "erlang-builder";
  public static final String BUILD_ORDER_FILE_NAME = "deps-tree.xml";

  static final Logger LOG = Logger.getInstance(CoqBuilder.class);

  private CoqBuilderUtil() {
  }

  @Nullable
  public static <T> T readFromXML(@NotNull CompileContext context, @NotNull String filename, @NotNull Class<T> tClass) {
    try {
      File xmlFile = getXmlFile(context, filename);
      if (!xmlFile.exists()) return null;

      Document document = JDOMUtil.loadDocument(xmlFile);
      return XmlSerializer.deserialize(document, tClass);
    }
    catch (JDOMException e) {
      LOG.error("Can't read XML from " + filename, e);
    }
    catch (IOException e) {
      LOG.warn("Can't read " + filename, e);
    }
    return null;
  }

  @NotNull
  public static File getXmlFile(@NotNull CompileContext context, @NotNull String filename) {
    File dataStorageRoot = context.getProjectDescriptor().dataManager.getDataPaths().getDataStorageRoot();
    File parentDirectory = new File(dataStorageRoot, BUILDER_DIRECTORY);
    return new File(parentDirectory, filename);
  }

  public static boolean isSource(@NotNull String fileName) {
    return fileName.endsWith(".erl");
  }

  public static boolean isAppConfigFileName(@NotNull String fileName) {
    return fileName.endsWith(".app") || fileName.endsWith(".app.src");
  }

  public static boolean isHeader(@NotNull String fileName) {
    return fileName.endsWith(".hrl");
  }
}
