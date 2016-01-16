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

package org.intellij.coq;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryAdapter;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.updateSettings.impl.UpdateChecker;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.io.HttpRequests;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by dabrowski on 16/01/2016.
 */
public class UpdateComponent implements ApplicationComponent, Disposable {

    private static final String PLUGIN_ID = "org.intellij.coq";
    private static final String KEY = "coq.last.update.timestamp";
    private static Logger LOG = Logger.getInstance(UpdateComponent.class);
    private final EditorFactoryAdapter myListener = new EditorFactoryAdapter() {
        @Override
        public void editorCreated(@NotNull EditorFactoryEvent event) {
            Document document = event.getEditor().getDocument();
            VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            if (file != null && file.getFileType() instanceof CoqFileType) {
                checkForUpdates();
            }
        }
    };

    @Override
    public void initComponent() {
        if (!ApplicationManager.getApplication().isUnitTestMode()) {
            System.out.println("UpdateComponent.initComponent");
            EditorFactory.getInstance().addEditorFactoryListener(myListener, this);
        }
    }

    @NotNull
    @SuppressWarnings("ConstantConditions")
    public static IdeaPluginDescriptor getPlugin() {

        return PluginManager.getPlugin(PluginId.getId(PLUGIN_ID));
    }

    private static void checkForUpdates() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        long lastUpdate = propertiesComponent.getOrInitLong(KEY, 0);
        if (lastUpdate == 0 || System.currentTimeMillis() - lastUpdate > TimeUnit.DAYS.toMillis(1)) {
            ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String buildNumber = ApplicationInfo.getInstance().getBuild().asString();
                        String pluginVersion = getPlugin().getVersion();
                        String pluginId = getPlugin().getPluginId().getIdString();
                        String os = URLEncoder.encode(SystemInfo.OS_NAME + " " + SystemInfo.OS_VERSION, CharsetToolkit.UTF8);
                        String uid = UpdateChecker.getInstallationUID(PropertiesComponent.getInstance());
                        final String url =
                                "https://plugins.jetbrains.com/plugins/list" +
                                        "?pluginId=" + pluginId +
                                        "&build=" + buildNumber +
                                        "&pluginVersion=" + pluginVersion +
                                        "&os=" + os +
                                        "&uuid=" + uid;
                        PropertiesComponent.getInstance().setValue(KEY, String.valueOf(System.currentTimeMillis()));
                        HttpRequests.request(url).connect(
                                new HttpRequests.RequestProcessor<Object>() {
                                    @Nullable
                                    @Override
                                    public Object process(@NotNull HttpRequests.Request request) throws IOException {
                                        try {
                                            JDOMUtil.load(request.getReader());
                                            LOG.info((request.isSuccessful() ? "Successful" : "Unsuccessful") + " update: " + url);
                                        }
                                        catch (JDOMException e) {
                                            LOG.warn(e);
                                        }
                                        return null;
                                    }
                                }
                        );
                    }
                    catch (IOException e) {
                        LOG.warn(e);
                    }
                }
            });
        }
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return getClass().getName();
    }

    @Override
    public void dispose() {
        disposeComponent();
    }



}
