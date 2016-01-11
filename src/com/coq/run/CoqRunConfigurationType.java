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

package com.coq.run;

import com.coq.CoqIcons;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by dabrowski on 11/01/2016.
 */
public class CoqRunConfigurationType implements ConfigurationType{
    @Override
    public String getDisplayName() {
        return "Coq";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Cor Run Configuration Type";
    }

    @Override
    public Icon getIcon() {
        return CoqIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "COQ_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new CoqConfigurationFactory(this)};
    }
}
