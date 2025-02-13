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
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import se.trixon.pixollage.collage.Collage;

@ActionID(
        category = "Collage",
        id = "se.trixon.pixollage.actions.RenderAction"
)
@ActionRegistration(
        displayName = "#CTL_RenderAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Collage", position = 1000),
    @ActionReference(path = "Shortcuts", name = "D-ENTER")
})
@Messages("CTL_RenderAction=Render...")
public final class RenderAction extends BaseCollageAction {

    public RenderAction(Collage context) {
        mContext = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mContext.showRenderDialog();
    }
}
