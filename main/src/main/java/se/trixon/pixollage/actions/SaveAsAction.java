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
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import se.trixon.almond.util.Dict;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.collage.Collage;

@ActionID(
        category = "File",
        id = "se.trixon.pixollage.actions.SaveAsAction"
)
@ActionRegistration(
        displayName = "#CTL_SaveAsAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1650),
    @ActionReference(path = "Shortcuts", name = "DS-S")
})
@Messages("CTL_SaveAsAction=Save as...")
public final class SaveAsAction extends BaseCollageAction {

    public SaveAsAction(Collage context) {
        mContext = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            var file = Pixollage.requestFile(Dict.SAVE_AS.toString());

            if (file != null) {
                mContext.save(file);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
