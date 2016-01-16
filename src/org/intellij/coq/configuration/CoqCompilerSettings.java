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

package org.intellij.coq.configuration;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.intellij.coq.jps.model.JpsCoqCompilerOptionsSerializer;
import org.intellij.coq.jps.model.CoqCompilerOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dabrowski on 15/01/2016.
 */

@State(
        name = JpsCoqCompilerOptionsSerializer.COMPILER_OPTIONS_COMPONENT_NAME,
        storages = {
                @Storage(file = StoragePathMacros.PROJECT_FILE),
                @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/compiler.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)

public class CoqCompilerSettings implements PersistentStateComponent<CoqCompilerOptions> {

    private CoqCompilerOptions myCompilerOptions = new CoqCompilerOptions();

    @Nullable
    @Override
    public CoqCompilerOptions getState() {
        return myCompilerOptions;
    }

    @Override
    public void loadState(CoqCompilerOptions state) {
        myCompilerOptions = state;
    }

    public boolean isUseRebarCompilerEnabled() {
        return myCompilerOptions.myUseRebarCompiler;
    }

    public void setUseRebarCompilerEnabled(boolean useRebarCompiler) {
        myCompilerOptions.myUseRebarCompiler = useRebarCompiler;
    }

    public boolean isAddDebugInfoEnabled() {
        return myCompilerOptions.myAddDebugInfoEnabled;
    }

    public void setAddDebugInfoEnabled(boolean useDebugInfo) {
        myCompilerOptions.myAddDebugInfoEnabled = useDebugInfo;
    }

    @NotNull
    public static CoqCompilerSettings getInstance(@NotNull Project project) {
        CoqCompilerSettings persisted = ServiceManager.getService(project, CoqCompilerSettings.class);
        return persisted != null ? persisted : new CoqCompilerSettings();
    }

}
