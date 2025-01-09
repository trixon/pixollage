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
import javax.swing.border.EmptyBorder;
import org.apache.commons.io.FileUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.swing.SwingHelper;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.ui.CollageTopComponent;
import se.trixon.pixollage.ui.PropertiesPanel;
import se.trixon.pixollage.ui.RenderPanel;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class Collage {

    private transient String mName;
    @SerializedName("header")
    private final CollageProperties mProperties = new CollageProperties();
    private transient final PropertiesPanel mPropertiesPanel = new PropertiesPanel();
    private transient final RenderPanel mRenderPanel = new RenderPanel();
    private transient final CollageTopComponent mTopComponent;

    public Collage(CollageTopComponent tc) {
        mTopComponent = tc;
        mPropertiesPanel.setBorder(new EmptyBorder(SwingHelper.getUIScaledInsets(8)));
    }

    public void clear() {
        System.out.println("Clearing " + mName);
    }

    public BufferedImage generateImage(int w, int h) {
        var image = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        var g2 = image.createGraphics();
        g2.setPaint(mProperties.getBorderColor());
        g2.fillRect(0, 0, w, h);

        //TODO Add tiles...
        g2.dispose();

        return image;
    }

    public String getName() {
        return mName;
    }

    public CollageProperties getProperties() {
        return mProperties;
    }

    public void save(File file) throws IOException {
        FileUtils.write(file, Pixollage.GSON.toJson(this), "utf-8");
    }

    public void setName(String name) {
        mName = name;
    }

    public void showAddImageDialog() {
        System.out.println("Add " + mName);
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
            mPropertiesPanel.apply(mProperties);
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
}
