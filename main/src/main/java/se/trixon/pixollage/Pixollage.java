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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.io.File;
import org.openide.filesystems.FileChooserBuilder;
import se.trixon.almond.nbp.Almond;
import se.trixon.almond.nbp.FileChooserHelper;
import se.trixon.almond.util.GlobalState;
import se.trixon.almond.util.gson_adapter.AwtColorAdapter;
import se.trixon.almond.util.gson_adapter.FileAdapter;
import se.trixon.almond.util.swing.SwingHelper;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Pixollage {

    public static final Gson GSON = new GsonBuilder()
            .setVersion(1.0)
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Color.class, new AwtColorAdapter())
            .registerTypeAdapter(File.class, new FileAdapter())
            .create();
    public static final int ICON_SIZE_TOOLBAR = 32;

    private static final GlobalState sGlobalState = new GlobalState();

    public static GlobalState getGlobalState() {
        return sGlobalState;
    }

    public static int getIconSizeToolBar() {
        return SwingHelper.getUIScaled(ICON_SIZE_TOOLBAR);
    }

    public static File requestFile(String title) {
        var defaultExt = "pxl";
        var file = new FileChooserBuilder(Pixollage.class)
                .addFileFilter(PxlDataObject.FILE_NAME_EXTENSION_FILTER)
                .setFileFilter(PxlDataObject.FILE_NAME_EXTENSION_FILTER)
                .setFileHiding(true)
                .setTitle(title)
                .setSelectionApprover(FileChooserHelper.getFileExistSelectionApprover(Almond.getFrame(), defaultExt))
                .showSaveDialog();

        file = FileChooserHelper.addExtIfMissing(file, defaultExt);

        return file;
    }

}
