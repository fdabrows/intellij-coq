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

import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.JpsElementFactory;
import org.jetbrains.jps.model.JpsElementTypeWithDefaultProperties;
import org.jetbrains.jps.model.library.sdk.JpsSdkType;

import java.io.File;

/**
 * Created by dabrowski on 15/01/2016.
 */
public class JpsCoqSdkType extends JpsSdkType<JpsDummyElement> implements JpsElementTypeWithDefaultProperties<JpsDummyElement> {

    public static final JpsCoqSdkType INSTANCE = new JpsCoqSdkType();

    public static final String BYTECODE_INTERPRETER = "coqtop";
    public static final String BYTECODE_COMPILER = "coqc";
    public static final String SCRIPT_INTERPRETER = "coqtop";

    private static final String TESTS_SDK_PATH_PROPERTY = "erlang.sdk.path";
    private static final String DEFAULT_TESTS_SDK_PATH = "/usr/lib/coq/";


    @NotNull
    public static File getByteCodeInterpreterExecutable(@NotNull String sdkHome) {
        return getSdkExecutable(sdkHome, BYTECODE_INTERPRETER);
    }

    @NotNull
    public static File getByteCodeCompilerExecutable(@NotNull String sdkHome) {
        return getSdkExecutable(sdkHome, BYTECODE_COMPILER);
    }

    @NotNull
    public static File getScriptInterpreterExecutable(@NotNull String sdkHome) {
        return getSdkExecutable(sdkHome, SCRIPT_INTERPRETER);
    }

    @NotNull
    @Override
    public JpsDummyElement createDefaultProperties() {
        return JpsElementFactory.getInstance().createDummyElement();
    }

    @NotNull
    public static String getExecutableFileName(@NotNull String executableName) {
        return SystemInfo.isWindows ? executableName + ".exe" : executableName;
    }

    @TestOnly
    @NotNull
    public static String getTestsSdkPath() {
        String sdkPathFromProperty = StringUtil.nullize(System.getProperty(TESTS_SDK_PATH_PROPERTY));
        return StringUtil.notNullize(sdkPathFromProperty, DEFAULT_TESTS_SDK_PATH);
    }

    @TestOnly
    @NotNull
    public static String getSdkConfigurationFailureMessage() {
        return "Failed to setup an Erlang SDK at " + getTestsSdkPath() +
                "\nUse " + TESTS_SDK_PATH_PROPERTY + " system property to set sdk path";
    }

    @NotNull
    private static File getSdkExecutable(@NotNull String sdkHome, @NotNull String command) {
        System.out.println("JpsCoqSdkType");

        return new File(new File(sdkHome, "bin"), getExecutableFileName(command));
    }


}
