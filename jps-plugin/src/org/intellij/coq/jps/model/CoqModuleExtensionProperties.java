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

import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.AbstractCollection;
import com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoqModuleExtensionProperties {
  @Tag("parseTransforms")
  @AbstractCollection(surroundWithTag = false, elementTag = "transform")
  //should not contain duplicate elements
  public List<String> myParseTransforms = ContainerUtil.newArrayList();

  public CoqModuleExtensionProperties() {
  }

  public CoqModuleExtensionProperties(@NotNull CoqModuleExtensionProperties props) {
    myParseTransforms = ContainerUtil.newArrayList(props.myParseTransforms);
  }
}
