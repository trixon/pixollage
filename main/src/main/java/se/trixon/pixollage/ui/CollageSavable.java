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
import se.trixon.pixollage.collage.Collage;

class CollageSavable extends AbstractSavable {

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
    protected String findDisplayName() {
        return getCollage().getName();
    }

    @Override
    protected void handleSave() throws IOException {
        getCollage().save();
        tc.getInstanceContent().remove(this);
    }

    private Collage getCollage() {
        return tc.getCollage();
    }

}
