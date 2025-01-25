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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openide.util.Exceptions;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Photo {

    private String mChecksum;
    private final File mFile;
    private int mOrientation;
    private BufferedImage mThumbnailBufferedImage;

    public Photo(File file) {
        mFile = file;
        initMetadata();
    }

    public String getChecksum() {
        if (mChecksum == null) {
            try {
                mChecksum = String.format("%08x", FileUtils.checksumCRC32(mFile));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        return mChecksum;
    }

    public File getFile() {
        return mFile;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public BufferedImage getThumbnailBufferedImage() {
        return mThumbnailBufferedImage;
    }

    public String getThumbnailName() {
        return "%s.%s".formatted(getChecksum(), FilenameUtils.getExtension(mFile.getName()));
    }

    public void setChecksum(String checksum) {
        mChecksum = checksum;
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public void setThumbnailBufferedImage(BufferedImage thumbnailBufferedImage) {
        mThumbnailBufferedImage = thumbnailBufferedImage;
    }

    private void initMetadata() {
        try {
            var mMetadata = ImageMetadataReader.readMetadata(mFile);
            var rotationDirectory = mMetadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            mOrientation = rotationDirectory.getInt(ExifSubIFDDirectory.TAG_ORIENTATION);
        } catch (MetadataException | NullPointerException | ImageProcessingException | IOException ex) {
            mOrientation = 1;
        }
    }
}
