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

import com.coq.errors.CoqtopPathError;
import com.intellij.mock.MockComponentManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.components.ServiceManager;
import org.junit.Test;

/**
 * Created by dabrowski on 08/01/2016.
 */
public class CoqTopLevelTest {

    public class SimpleDisposable implements Disposable{

        @Override
        public void dispose() {

        }
    }

    @Test
    public void testReadPrompt() throws Exception {
        //try {
            Application application = ApplicationManager.getApplication();
            System.out.println("<<" + application+ ">>");
           /* MockComponentManager componentManager = new MockComponentManager(null, new SimpleDisposable());
            componentManager.registerService(CoqSettings.class);
            ServiceManager.createLazyKey(CoqSettings.class);
            CoqSettings settings2 = ServiceManager.getService(CoqSettings.class);
            CoqSettings settings = new CoqSettings();
            settings.coqbin="/usr/local/bin";
            CoqTopLevel coqTopLevel = CoqTopLevel.getCoqTopLevel(settings);*/
       // } catch (CoqtopPathError coqtopPathError) {
       //     coqtopPathError.printStackTrace();
       // }
    }


}