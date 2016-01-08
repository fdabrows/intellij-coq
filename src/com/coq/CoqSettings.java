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
