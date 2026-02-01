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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Strings;
import org.openide.util.Exceptions;
import se.trixon.almond.util.GraphicsHelper;
import se.trixon.almond.util.ImageScaler;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Engine {

    public static final String[] SUPPORTED_IMAGE_EXT = {"jpg", "png"};
    private BufferedImage mImage;
    private final Predicate<File> mImageFileExtPredicate = f -> Strings.CI.equalsAny(FilenameUtils.getExtension(f.getName()), Engine.SUPPORTED_IMAGE_EXT);
    private final ImageScaler mImageScaler = ImageScaler.getInstance();

    public static Engine getInstance() {
        return Holder.INSTANCE;
    }

    private Engine() {
    }

    public void addPhotos(List<Photo> photos, Settings settings) {
        var semaphore = new Semaphore(Runtime.getRuntime().availableProcessors() * 2);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var photo : photos) {
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        addPhoto(photo, settings);
                    } catch (IOException | InterruptedException ex) {
                        Exceptions.printStackTrace(ex);
                    } finally {
                        semaphore.release();
                    }
                });
            }
        }
    }

    public void createCollage(List<Photo> photos, Settings settings) {
        int w = settings.width();
        int h = settings.height();
        mImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        var g2 = mImage.createGraphics();
        g2.setPaint(settings.borderColor());
        g2.fillRect(0, 0, w, h);
        g2.dispose();

        //TODO Calculateimage layout
    }

    public List<Photo> generatePhotoList(List<File> files) {
        return files.stream()
                .map(f -> new Photo(f))
                .filter(p -> p.isValid())
                .toList();
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

    public void write(File file) {
        try {
            var extension = FilenameUtils.getExtension(file.getName());
            ImageIO.write(mImage, extension, file);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void addPhoto(Photo photo, Settings settings) throws IOException {
        var g2 = mImage.createGraphics();
        var file = photo.getFile();
        var orientation = photo.getOrientation();
        int thumbnailSize = 400;

        var scaledImage = mImageScaler.getScaledImage(file, new Dimension(thumbnailSize, thumbnailSize));
        scaledImage = GraphicsHelper.rotate(scaledImage, orientation);

        var iw = scaledImage.getWidth();
        var ih = scaledImage.getHeight();
        var cw = settings.width();
        var ch = settings.height();

        var random = new Random();
        var y = random.nextInt(ch - ih);
        var x = random.nextInt(cw - iw);

        g2.drawImage(scaledImage, x, y, null);
        g2.dispose();
    }

    private static class Holder {

        private static final Engine INSTANCE = new Engine();
    }
}
