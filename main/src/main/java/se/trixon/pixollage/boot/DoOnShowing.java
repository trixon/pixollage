/*
 * Copyright 2025 Patrik Karlström <patrik@trixon.se>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.pixollage.boot;

import java.net.MalformedURLException;
import java.net.URI;
import javax.swing.JFrame;
import org.openide.awt.Actions;
import org.openide.awt.HtmlBrowser;
import org.openide.util.Exceptions;
import org.openide.windows.OnShowing;
import org.openide.windows.WindowManager;
import se.trixon.almond.nbp.Almond;
import se.trixon.almond.util.SystemHelper;
import se.trixon.almond.util.swing.SwingHelper;

/**
 *
 * @author Patrik Karlström
 */
@OnShowing
public class DoOnShowing implements Runnable {

    @Override
    public void run() {
        SystemHelper.setDesktopBrowser(url -> {
            try {
                HtmlBrowser.URLDisplayer.getDefault().showURL(URI.create(url).toURL());
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        });

        var windowManager = WindowManager.getDefault();
        Almond.setFrame((JFrame) windowManager.getMainWindow());
        SwingHelper.runLaterDelayed(100, () -> {
            var editorMode = windowManager.findMode("editor");

            if (windowManager.getOpenedTopComponents(editorMode).length == 0) {
                Actions.forID("File", "se.trixon.pixollage.actions.NewAction").actionPerformed(null);
            }
        });
    }
}
