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

import org.openide.util.NbPreferences;
import se.trixon.almond.util.OptionsBase;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Options extends OptionsBase {

    public static final String DEFAULT_BORDER_COLOR = "#000000";
    public static final double DEFAULT_BORDER_SIZE = 1.0;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_WIDTH = 16;
    public static final String KEY_BORDER_COLOR = "default_border_color";
    public static final String KEY_BORDER_SIZE = "default_border_size";
    public static final String KEY_HEIGHT = "default_height";
    public static final String KEY_WIDTH = "default_width";

    public static Options getInstance() {
        return Holder.INSTANCE;
    }

    private Options() {
        mPreferences = NbPreferences.forModule(getClass());
    }

    private static class Holder {

        private static final Options INSTANCE = new Options();
    }

}
