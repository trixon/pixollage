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
package se.trixon.pixollage.ui;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import org.apache.commons.lang3.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.swing.SwingHelper;
import se.trixon.pixollage.Pixollage;
import se.trixon.pixollage.collage.Collage;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//se.trixon.pixollage.main.ui//Collage//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "CollageTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED
)
public final class CollageTopComponent extends TopComponent {

    private Collage mCollage;
    private final CollagePanel mCollagePanel = new CollagePanel();
    private CollageSavable mCollageSavable;
    private final InstanceContent mInstanceContent = new InstanceContent();

    public CollageTopComponent() {
        initComponents();
        initDragAndDrop();
        mCollage = new Collage();
        collageTopComponent();
    }

    public CollageTopComponent(Collage collage) {
        initComponents();
        initDragAndDrop();
        mCollage = collage;
        collageTopComponent();
    }

    @Override
    public boolean canClose() {
        if (!isDirty()) {
            return true;
        }

        var saveButton = new JButton(Dict.SAVE.toString());
        var d = new DialogDescriptor(
                "File is modified. Save it?",
                "File modified",
                true,
                new Object[]{Dict.CANCEL.toString(), Dict.DISCARD.toString(), saveButton},
                saveButton,
                0,
                null,
                null
        );

        SwingHelper.runLaterDelayed(100, () -> saveButton.requestFocus());
        var result = DialogDisplayer.getDefault().notify(d);

        if (result == saveButton) {
            var canClose = false;

            var file = mCollage.getFile();
            try {
                if (file != null) {
                    mCollage.save(file);
                    canClose = true;
                } else {
                    file = Pixollage.requestFile(Dict.SAVE_AS.toString());

                    if (file != null && !file.isDirectory()) {
                        mCollage.save(file);
                        canClose = true;
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (canClose) {
                removeSavable();
            }

            return canClose;
        } else if (result == Dict.DISCARD.toString()) {
            removeSavable();
            return true;
        } else {
            return false;
        }
    }

    public void collageTopComponent() {
        mCollage.setTopComponent(this);
        loadCollage();
        try {
            associateLookup(new AbstractLookup(mInstanceContent));
        } catch (IllegalStateException e) {
            //nvm - already set
        }
        mInstanceContent.add(mCollage);
    }

    public Collage getCollage() {
        return mCollage;
    }

    public InstanceContent getInstanceContent() {
        return mInstanceContent;
    }

    @Override
    public int getPersistenceType() {
        if (mCollage.getFile() == null) {
            return TopComponent.PERSISTENCE_NEVER;
        } else {
            return TopComponent.PERSISTENCE_ONLY_OPENED;
        }
    }

    public void modify() {
        if (getLookup().lookup(CollageSavable.class) == null) {
            mCollageSavable = new CollageSavable(CollageTopComponent.this);
            mInstanceContent.add(mCollageSavable);
        }
    }

    public void refreshNames() {
        setName(mCollage.getName());
        if (mCollage.getFileObject() != null) {
            setToolTipText(mCollage.getFileObject().getPath());
        }
    }

    public void removeSavable() {
        getInstanceContent().remove(mCollageSavable);
        mCollageSavable.removeFromRegistry();
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version

        String path = p.getProperty("path");

        if (StringUtils.isNotBlank(path) && new File(path).isFile()) {
            var file = new File(path);
            try {
                loadFromFile(file);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
        var file = mCollage.getFile();
        if (file != null) {
            p.put("path", file.getAbsolutePath());
        }
    }

    private void initComponents() {
        setBackground(new java.awt.Color(255, 255, 153));
        setLayout(new BorderLayout());

        mCollagePanel.setLayout(null);
        add(mCollagePanel, BorderLayout.CENTER);
    }

    private void initDragAndDrop() {
        var dropTarget = new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    @SuppressWarnings("unchecked")
                    var files = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    mCollage.addFiles(files);
                } catch (UnsupportedFlavorException | IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        };

        setDropTarget(dropTarget);
    }

    private boolean isDirty() {
        return getLookup().lookupAll(CollageSavable.class).stream().anyMatch(c -> c == mCollageSavable);
    }

    private void loadCollage() {
        refreshNames();
        mCollagePanel.load(mCollage);
    }

    private void loadFromFile(File file) throws IOException {
        mInstanceContent.remove(mCollage);
        var fileObject = FileUtil.createData(file);
        mCollage = Pixollage.JSON.readValue(file, Collage.class);
        mCollage.setFileObject(fileObject);
        collageTopComponent();
    }
}
