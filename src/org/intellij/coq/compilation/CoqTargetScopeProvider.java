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

package org.intellij.coq.compilation;

import com.intellij.compiler.impl.BuildTargetScopeProvider;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerFilter;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.ContainerUtil;
import com.sun.istack.internal.NotNull;
import org.intellij.coq.jps.builder.CoqModuleBuildOrderTargetType;
import org.intellij.coq.configuration.CoqCompilerSettings;
import org.intellij.coq.module.CoqModuleType;
import org.jetbrains.jps.api.CmdlineProtoUtil;
import org.jetbrains.jps.api.CmdlineRemoteProto;

import java.util.Collections;
import java.util.List;

/**
 * Created by dabrowski on 15/01/2016.
 */
public class CoqTargetScopeProvider extends BuildTargetScopeProvider {

    @NotNull
    @Override
    public List<CmdlineRemoteProto.Message.ControllerMessage.ParametersMessage.TargetTypeBuildScope> getBuildTargetScopes(@NotNull CompileScope baseScope,
                                                                                                                          @NotNull CompilerFilter filter,
                                                                                                                 @NotNull Project project, boolean forceBuild) {
        System.out.println("CoqTargetScopeProvider.getBuildTargetScopes");
        if (CoqCompilerSettings.getInstance(project).isUseRebarCompilerEnabled() || !hasCoqModules(baseScope)) {
            System.out.println("CoqTargetScopeProvider.empty");
            return ContainerUtil.emptyList();
        }
        System.out.println("CoqTargetScopeProvider.notempty");
        List<CmdlineRemoteProto.Message.ControllerMessage.ParametersMessage.TargetTypeBuildScope> list;
        list =
        Collections.singletonList(CmdlineProtoUtil.createTargetsScope(CoqModuleBuildOrderTargetType.TYPE_ID,
                Collections.singletonList(project.getName()),
                forceBuild));
        return list;
    }

    private static boolean hasCoqModules(@NotNull CompileScope baseScope) {
        for (Module module : baseScope.getAffectedModules()) {
            if (ModuleType.get(module) instanceof CoqModuleType) {
                return true;
            }
        }
        return false;
    }
}
