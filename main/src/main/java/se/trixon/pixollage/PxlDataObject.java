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
package se.trixon.pixollage;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.NbBundle.Messages;
import se.trixon.pixollage.actions.OpenAction;

@Messages({
    "LBL_Pxl_LOADER=Files of Pixollage"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_Pxl_LOADER",
        mimeType = "text/x-pixollage",
        extension = {"pxl", "PXL"},
        showInFileChooser = {"Pixollage"},
        position = 1
)
@DataObject.Registration(
        mimeType = "text/x-pixollage",
        iconBase = "se/trixon/pixollage/pxl.png",
        displayName = "#LBL_Pxl_LOADER",
        position = 1
)
public class PxlDataObject extends MultiDataObject {

    public PxlDataObject(FileObject fileObject, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(fileObject, loader);
        OpenAction.open(fileObject);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

}
