package com.coq;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import sun.security.pkcs11.Secmod;

import javax.swing.*;

/**
 * Created by dabrowski on 06/01/2016.
 */
public class CoqModuleType extends ModuleType{

    public CoqModuleType(){
     super("Coq");
    }

    @NotNull
    @Override
    public ModuleBuilder createModuleBuilder() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "Coq Project";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Create a new Coq Project";
    }

    @Override
    public Icon getBigIcon() {
        return IconLoader.getIcon("/com/coq/icons/r.png");
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return IconLoader.getIcon("/com/coq/icons/r.png");
    }
}
