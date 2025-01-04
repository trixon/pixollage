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

import java.awt.Color;
import java.io.File;
import se.trixon.pixollage.Options;
import static se.trixon.pixollage.Options.*;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class CollageProperties {

    public static final Options OPTIONS = Options.getInstance();
    private int mAspectRatioHeight = OPTIONS.getInt(KEY_HEIGHT, DEFAULT_HEIGHT);
    private int mAspectRatioWidth = OPTIONS.getInt(KEY_WIDTH, DEFAULT_WIDTH);
    private Color mBorderColor = Color.decode(OPTIONS.get(KEY_BORDER_COLOR, DEFAULT_BORDER_COLOR));
    private double mBorderSize = OPTIONS.getDouble(KEY_BORDER_SIZE, DEFAULT_BORDER_SIZE);
    private int mRenderHeight = 1600;
    private File mRenderPath;
    private int mRenderWidth = 1920;

    public CollageProperties() {
    }

    public void calculateRenderHeight() {
        mRenderHeight = (int) Math.round(mRenderWidth / getAspectRatio());
    }

    public void calculateRenderWidth() {
        mRenderWidth = (int) Math.round(mRenderHeight * getAspectRatio());
    }

    public double getAspectRatio() {
        return mAspectRatioWidth / (double) mAspectRatioHeight;
    }

    public int getAspectRatioHeight() {
        return mAspectRatioHeight;
    }

    public int getAspectRatioWidth() {
        return mAspectRatioWidth;
    }

    public Color getBorderColor() {
        return mBorderColor;
    }

    public double getBorderSize() {
        return mBorderSize;
    }

    public int getRenderHeight() {
        return mRenderHeight;
    }

    public File getRenderPath() {
        return mRenderPath;
    }

    public int getRenderWidth() {
        return mRenderWidth;
    }

    public void setAspectRatioHeight(int aspectRatioHeight) {
        mAspectRatioHeight = aspectRatioHeight;
    }

    public void setAspectRatioWidth(int aspectRatioWidth) {
        mAspectRatioWidth = aspectRatioWidth;
    }

    public void setBorderColor(Color borderColor) {
        mBorderColor = borderColor;
    }

    public void setBorderSize(double borderSize) {
        mBorderSize = borderSize;
    }

    public void setRenderHeight(int renderHeight) {
        mRenderHeight = renderHeight;
    }

    public void setRenderPath(File renderPath) {
        mRenderPath = renderPath;
    }

    public void setRenderWidth(int renderWidth) {
        mRenderWidth = renderWidth;
    }

}
