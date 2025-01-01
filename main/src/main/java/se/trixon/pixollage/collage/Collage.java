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
package se.trixon.pixollage.collage;

import java.io.IOException;
import se.trixon.pixollage.ui.CollageTopComponent;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Collage {

    private String mName;
    private final CollageTopComponent mTopComponent;

    public Collage(CollageTopComponent tc) {
        mTopComponent = tc;
    }

    public void clear() {
        System.out.println("Clearing " + mName);
    }

    public String getName() {
        return mName;
    }

    public void save() throws IOException {
        System.out.println("SAVE " + mName);
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void showAddImageDialog() {
        System.out.println("Add " + mName);
    }

    public void showPropertiesDialog() {
        System.out.println("Properties " + mName);
    }

    public void showRenderDialog() {
        System.out.println("Render " + mName);
    }
}
