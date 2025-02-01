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
package se.trixon.pixollage.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Engine {

    public static final String[] SUPPORTED_IMAGE_EXT = {"jpg", "png"};
    private final Predicate<File> mImageFileExtPredicate = f -> StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(f.getName()), Engine.SUPPORTED_IMAGE_EXT);

    public static Engine getInstance() {
        return Holder.INSTANCE;
    }

    private Engine() {
    }

    public List<Photo> generatePhotoList(List<File> files) {
        return files.stream().map(f -> new Photo(f)).filter(p -> p.isValid()).toList();
    }

    public Predicate<File> getImageFileExtPredicate() {
        return mImageFileExtPredicate;
    }

    public boolean isValidForWrite(File file) {
        try {
            FileUtils.touch(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static class Holder {

        private static final Engine INSTANCE = new Engine();
    }
}
