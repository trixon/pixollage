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
import java.util.Arrays;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle.Messages;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.collage.Collage;

@ActionID(
        category = "Collage",
        id = "se.trixon.pixollage.actions.AddAction"
)
@ActionRegistration(
        displayName = "#CTL_AddAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Collage", position = 100),
    @ActionReference(path = "Shortcuts", name = "D-INSERT")
})
@Messages("CTL_AddAction=Add image(s)...")
public final class AddAction extends BaseCollageAction {

    public AddAction(Collage context) {
        mContext = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var files = new FileChooserBuilder(AddAction.class)
                .addFileFilter(Pixollage.PHOTO_FILE_NAME_EXTENSION_FILTER)
                .setFileFilter(Pixollage.PHOTO_FILE_NAME_EXTENSION_FILTER)
                .setControlButtonsAreShown(true)
                .setFileHiding(true)
                .setFilesOnly(true)
                .showMultiOpenDialog();

        if (files != null) {
            mContext.addFiles(Arrays.asList(files));
        }
    }
}
