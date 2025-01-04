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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
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
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
public final class CollageTopComponent extends TopComponent {

    private static int sDocumentCounter;
    private final Collage mCollage = new Collage(this);
    private final InstanceContent mInstanceContent = new InstanceContent();

    public CollageTopComponent() {
        initComponents();
        var name = "%s #%d".formatted("Collage", ++sDocumentCounter);
        setName(name);
        mCollage.setName(name);
        associateLookup(new AbstractLookup(mInstanceContent));
        mInstanceContent.add(mCollage);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                modify();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modify();
            }
        });
    }

    public Collage getCollage() {
        return mCollage;
    }

    public InstanceContent getInstanceContent() {
        return mInstanceContent;
    }

    void modify() {
        if (getLookup().lookup(CollageSavable.class) == null) {
            mInstanceContent.add(new CollageSavable(CollageTopComponent.this));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField = new javax.swing.JTextField();
        collagePanel = new se.trixon.pixollage.ui.CollagePanel();

        setBackground(new java.awt.Color(255, 255, 153));
        setLayout(new java.awt.BorderLayout());
        add(textField, java.awt.BorderLayout.PAGE_START);
        add(collagePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.trixon.pixollage.ui.CollagePanel collagePanel;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO showAddImageDialog custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO showAddImageDialog custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
