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

import com.google.gson.annotations.SerializedName;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.border.EmptyBorder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.swing.SwingHelper;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.PxlDataObject;
import se.trixon.pixollage.actions.OpenAction;
import se.trixon.pixollage.ui.CollageTopComponent;
import se.trixon.pixollage.ui.PropertiesPanel;
import se.trixon.pixollage.ui.RenderPanel;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Collage {

    private static int sDocumentCounter;

    private transient FileObject mFileObject;
    private transient String mName;
    @SerializedName("header")
    private final CollageProperties mProperties = new CollageProperties();
    private transient final PropertiesPanel mPropertiesPanel = new PropertiesPanel();
    private transient final RenderPanel mRenderPanel = new RenderPanel();
    private transient CollageTopComponent mTopComponent;
    private transient final CacheManager mCacheManager = CacheManager.getInstance();

    public Collage() {
        mPropertiesPanel.setBorder(new EmptyBorder(SwingHelper.getUIScaledInsets(8)));
        mName = "%s #%d".formatted("Collage", ++sDocumentCounter);
    }

    public void addFiles(List<File> files) {
        files = files.stream()
                .filter(f -> {
                    if (StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(f.getName()), PxlDataObject.FILE_NAME_EXTENSION_FILTER.getExtensions())) {
                        OpenAction.open(FileUtil.toFileObject(FileUtil.normalizeFile(f)));
                    }
                    return true;
                })
                .filter(f -> StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(f.getName()), Pixollage.SUPPORTED_IMAGE_EXT))
                .toList();

        if (files.isEmpty()) {
            return;
        }
        for (var file : files) {
            System.out.println("add: " + file);
            var photo = new Photo(file);
            mCacheManager.addIfMissing(photo);
        }

        markDirty();
    }

    public void clear() {
        System.out.println("Clearing " + mName);
//        markDirty();
        mTopComponent.removeSavable();
//        DataObject.getRegistry().getModified();
    }

    public BufferedImage generateImage(int w, int h) {
        if (w < 1 || h < 1) {
            return null;
        }

        var image = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        var g2 = image.createGraphics();
        g2.setPaint(mProperties.getBorderColor());
        g2.fillRect(0, 0, w, h);

        //TODO Add tiles...
        g2.dispose();

        return image;
    }

    public File getFile() {
        try {
            return new File(mFileObject.getPath());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public FileObject getFileObject() {
        return mFileObject;
    }

    public String getName() {
        return mName;
    }

    public CollageProperties getProperties() {
        return mProperties;
    }

    public void save(File file) throws IOException {
        FileUtils.write(file, Pixollage.GSON.toJson(this), "utf-8");

        var fileObject = FileUtil.toFileObject(FileUtil.normalizeFile(file));
        setFileObject(fileObject);

        mTopComponent.refreshNames();
        mTopComponent.removeSavable();
        StatusDisplayer.getDefault().setStatusText("%s %s.".formatted(file.getName(), "SAVED"));
    }

    public void setFileObject(FileObject fileObject) {
        mFileObject = fileObject;
        mName = mFileObject.getName();
        sDocumentCounter--;
    }

    public void setTopComponent(CollageTopComponent topComponent) {
        mTopComponent = topComponent;
    }

    public void showPropertiesDialog() {
        mPropertiesPanel.load(mProperties);

        var d = new NotifyDescriptor(
                mPropertiesPanel,
                Dict.PROPERTIES.toString(),
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                null,
                null
        );

        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.OK_OPTION) {
            if (mPropertiesPanel.apply(mProperties)) {
                markDirty();
            }
        }
    }

    public void showRenderDialog() {
        mRenderPanel.load(mProperties);

        var d = new NotifyDescriptor(
                mRenderPanel,
                Dict.RENDER.toString(),
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                new String[]{Dict.CANCEL.toString(), Dict.RENDER.toString()},
                Dict.RENDER.toString()
        );

        if (DialogDisplayer.getDefault().notify(d) == Dict.RENDER.toString()) {
            mRenderPanel.apply(mProperties);
            System.out.println("TODO: Save document and the actual rendering");
        }
    }

    private void markDirty() {
        mTopComponent.modify();
    }
}
