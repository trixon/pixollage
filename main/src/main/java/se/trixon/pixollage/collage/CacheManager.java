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

import se.trixon.pixollage.cli.Photo;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openide.modules.Places;
import org.openide.util.Exceptions;
import se.trixon.almond.util.GraphicsHelper;
import se.trixon.almond.util.ImageScaler;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class CacheManager {

    private final File mDirectory;
    private final ImageScaler mImageScaler = ImageScaler.getInstance();

    public static CacheManager getInstance() {
        return Holder.INSTANCE;
    }

    private CacheManager() {
        mDirectory = Places.getCacheSubdirectory("thumbnails");
    }

    public void addIfMissing(Photo photo) {
        var file = new File(mDirectory, photo.getThumbnailName());
        if (!file.isFile()) {
            System.out.println("thumbnail: " + photo.getFile());
            int thumbnailSize = 300;//TODO Move to global settings?

            try {
                var scaledImage = mImageScaler.getScaledImage(photo.getFile(), new Dimension(thumbnailSize, thumbnailSize));
                scaledImage = GraphicsHelper.rotate(scaledImage, photo.getOrientation());

                ImageIO.write(scaledImage, "jpg", file);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

    public String calculateChecksum(File file) throws IOException {
        return String.format("%08x", FileUtils.checksumCRC32(file));
    }

    private static class Holder {

        private static final CacheManager INSTANCE = new CacheManager();
    }
}
