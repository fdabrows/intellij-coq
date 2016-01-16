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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsCompositeElementBase;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;
import org.jetbrains.jps.model.module.JpsModule;

import java.util.Collections;
import java.util.List;

public class JpsCoqModuleExtension extends JpsCompositeElementBase<JpsCoqModuleExtension> {
  public static final JpsElementChildRole<JpsCoqModuleExtension> ROLE = JpsElementChildRoleBase.create("Erlang");

  private final CoqModuleExtensionProperties myProperties;

  @SuppressWarnings("UnusedDeclaration")
  public JpsCoqModuleExtension() {
    myProperties = new CoqModuleExtensionProperties();
  }

  public JpsCoqModuleExtension(CoqModuleExtensionProperties properties) {
    myProperties = properties;
  }

  public JpsCoqModuleExtension(JpsCoqModuleExtension moduleExtension) {
    myProperties = new CoqModuleExtensionProperties(moduleExtension.myProperties);
  }

  @NotNull
  @Override
  public JpsCoqModuleExtension createCopy() {
    return new JpsCoqModuleExtension(this);
  }

  public CoqModuleExtensionProperties getProperties() {
    return myProperties;
  }

  public List<String> getParseTransforms() {
    return Collections.unmodifiableList(myProperties.myParseTransforms);
  }

  @Nullable
  public static JpsCoqModuleExtension getExtension(@Nullable JpsModule module) {
    return module != null ? module.getContainer().getChild(ROLE) : null;
  }
}
