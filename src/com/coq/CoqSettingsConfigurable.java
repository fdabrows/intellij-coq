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
