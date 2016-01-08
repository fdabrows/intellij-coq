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

package com.coq;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by dabrowski on 06/01/2016.
 */
public class CoqSettingsConfigurable implements Configurable {

    CoqSettingsEditor settings;

    @Nls
    @Override
    public String getDisplayName() {
        return "Coq";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settings = new CoqSettingsEditor();
        CoqSettings config = CoqSettings.getInstance();
        settings.setContent(config.getState());
        return settings.createEditor();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        CoqSettings config = CoqSettings.getInstance();
        config.loadState(settings.getContent());
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

}
