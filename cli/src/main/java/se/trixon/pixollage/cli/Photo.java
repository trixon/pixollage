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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openide.util.Exceptions;
import se.trixon.almond.util.GraphicsHelper;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Photo {

    private Aspect mAspect;
    private String mChecksum;
    private final File mFile;
    private int mOrientation;
    private Dimension mOriginalDimension = new Dimension(1, 1);
    private BufferedImage mThumbnailBufferedImage;

    public Photo(File file) {
        mFile = file;
        initMetadata();
    }

    public Aspect getAspect() {
        return mAspect;
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

    public Dimension getOriginalDimension() {
        return mOriginalDimension;
    }

    public BufferedImage getThumbnailBufferedImage() {
        return mThumbnailBufferedImage;
    }

    public String getThumbnailName() {
        return "%s.%s".formatted(getChecksum(), FilenameUtils.getExtension(mFile.getName()));
    }

    public boolean isValid() {
        return mOriginalDimension != null;
    }

    public void print() {
        System.out.println(getFile());
        System.out.println("\t" + getOrientation());
        System.out.println("\t" + getOriginalDimension());
        System.out.println("\t" + getAspect());
        System.out.println("");
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

        try {
            mOriginalDimension = GraphicsHelper.getImgageDimension(mFile);
            if (mOrientation == 6 || mOrientation == 8) {
                mOriginalDimension = new Dimension(mOriginalDimension.height, mOriginalDimension.width);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (mOriginalDimension == null) {
            System.err.println(new IllegalArgumentException("Invalid file: " + mFile).getMessage());
            return;
        }

        var h = mOriginalDimension.height;
        var w = mOriginalDimension.width;

        var aspect = Aspect.SQUARE;
        if (h > w) {
            aspect = Aspect.PORTRAIT;
        } else if (w > h) {
            aspect = Aspect.LANDSCAPE;
        }
        mAspect = aspect;
    }

    public enum Aspect {
        PORTRAIT, LANDSCAPE, SQUARE;
    }
}
