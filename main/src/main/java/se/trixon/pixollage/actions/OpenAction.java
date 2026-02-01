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
package se.trixon.pixollage.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.PxlDataObject;
import se.trixon.pixollage.collage.Collage;
import se.trixon.pixollage.ui.CollageTopComponent;

@ActionID(
        category = "File",
        id = "se.trixon.pixollage.actions.OpenAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 405),
    @ActionReference(path = "Shortcuts", name = "D-O")
})
@Messages("CTL_OpenAction=Open...")
public final class OpenAction implements ActionListener {

    public static void open(FileObject fileObject) {
        try {
            var collage = Pixollage.JSON.readValue(fileObject.asText("utf-8"), Collage.class);
            collage.setFileObject(fileObject);

            var windowManager = WindowManager.getDefault();

            for (var mode : windowManager.getModes()) {
                for (var topComponent : mode.getTopComponents()) {
                    if (topComponent instanceof CollageTopComponent tc) {
                        if (tc.getCollage().getProperties().getId().equals(collage.getProperties().getId())) {
                            tc.requestAttention(true);
                            tc.requestActive();
                            return;
                        }
                    }
                }
            }

            var tc = new CollageTopComponent(collage);
            tc.open();
            tc.requestActive();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var file = new FileChooserBuilder(OpenAction.class)
                .addFileFilter(PxlDataObject.FILE_NAME_EXTENSION_FILTER)
                .setFileFilter(PxlDataObject.FILE_NAME_EXTENSION_FILTER)
                .setControlButtonsAreShown(true)
                .setFileHiding(true)
                .setFilesOnly(true)
                .showOpenDialog();

        if (file != null) {
            var fileObject = FileUtil.toFileObject(FileUtil.normalizeFile(file));
            open(fileObject);
        }
    }
}
