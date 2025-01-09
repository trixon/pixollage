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
package se.trixon.pixollage.ui;

import java.io.IOException;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.loaders.SaveAsCapable;
import se.trixon.almond.nbp.Almond;
import se.trixon.almond.nbp.FileChooserHelper;
import se.trixon.pixollage.collage.Collage;

class CollageSavable extends AbstractSavable implements SaveAsCapable {

    private final CollageTopComponent tc;

    CollageSavable(CollageTopComponent tc) {
        this.tc = tc;
        register();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CollageSavable collageSavable) {
            return this.tc == collageSavable.tc;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return tc.hashCode();
    }

    @Override
    public void saveAs(FileObject fileObject, String name) throws IOException {
        System.out.println("TODO SaveAs");
        System.out.println(fileObject.getPath());
        System.out.println(name);
        tc.getInstanceContent().remove(this);
    }

    @Override
    protected String findDisplayName() {
        return getCollage().getName();
    }

    @Override
    protected void handleSave() throws IOException {
//        var file = new FileChooserBuilder(getCollage().getProperties().getId().toString())
        var file = new FileChooserBuilder(getClass())
                .setFileHiding(true)
                .setSelectionApprover(FileChooserHelper.getFileExistSelectionApprover(Almond.getFrame()))
                .showSaveDialog();
        if (file != null) {
            getCollage().save(file);
        }
        tc.getInstanceContent().remove(this);
    }

    private Collage getCollage() {
        return tc.getCollage();
    }

}
