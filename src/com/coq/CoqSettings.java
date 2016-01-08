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

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(
        name = "CoqSettings", storages = {
                @Storage(id = "dir", file = "$APP_CONFIG$/coqsettings.xml", scheme = StorageScheme.DIRECTORY_BASED)

})

public class CoqSettings implements PersistentStateComponent<CoqSettings> {

    public String coqbin;

    public static CoqSettings getInstance(){
        return ServiceManager.getService(CoqSettings.class);
    }

    public CoqSettings getState() {
        return this;
    }

    public void loadState(CoqSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
