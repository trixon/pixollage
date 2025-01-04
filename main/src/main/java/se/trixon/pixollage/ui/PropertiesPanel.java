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

import java.awt.Color;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.GraphicsHelper;
import se.trixon.pixollage.Options;
import static se.trixon.pixollage.Options.*;
import se.trixon.pixollage.collage.CollageProperties;

/**
 *
 * @author Patrik Karlström <patrik@trixon.se>
 */
public class PropertiesPanel extends javax.swing.JPanel {

    private Options mOptions = Options.getInstance();

    /**
     * Creates new form CollagePropertiesPanel
     */
    public PropertiesPanel() {
        initComponents();
        borderColorPanel.setPreferredSize(borderSpinner.getPreferredSize());
        borderColorPanel.setMinimumSize(borderSpinner.getMinimumSize());
        borderColorPanel.setSize(borderSpinner.getSize());
    }

    public void apply(CollageProperties properties) {
        properties.setBorderSize((double) borderSpinner.getValue());
        properties.setBorderColor(Color.yellow);
        properties.setAspectRatioHeight((int) heightSpinner.getValue());
        properties.setAspectRatioWidth((int) widthSpinner.getValue());
    }

    public void load(CollageProperties properties) {
        heightSpinner.setValue(properties.getAspectRatioHeight());
        widthSpinner.setValue(properties.getAspectRatioWidth());
        borderSpinner.setValue(properties.getBorderSize());
        borderColorPanel.setColor(properties.getBorderColor());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ratioLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        widthSpinner = new javax.swing.JSpinner();
        heightLabel = new javax.swing.JLabel();
        heightSpinner = new javax.swing.JSpinner();
        borderLabel = new javax.swing.JLabel();
        borderSpinner = new javax.swing.JSpinner();
        borderColorLabel = new javax.swing.JLabel();
        borderColorPanel = new se.trixon.almond.util.swing.ColorPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        ratioLabel.setFont(ratioLabel.getFont().deriveFont(ratioLabel.getFont().getStyle() | java.awt.Font.BOLD, ratioLabel.getFont().getSize()+2));
        org.openide.awt.Mnemonics.setLocalizedText(ratioLabel, org.openide.util.NbBundle.getMessage(PropertiesPanel.class, "PropertiesPanel.ratioLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(widthLabel, Dict.Geometry.WIDTH.toString());

        widthSpinner.setModel(new javax.swing.SpinnerNumberModel(2, 1, null, 1));

        org.openide.awt.Mnemonics.setLocalizedText(heightLabel, Dict.Geometry.HEIGHT.toString());

        heightSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, null, 1));

        borderLabel.setFont(borderLabel.getFont().deriveFont(borderLabel.getFont().getStyle() | java.awt.Font.BOLD, borderLabel.getFont().getSize()+2));
        org.openide.awt.Mnemonics.setLocalizedText(borderLabel, Dict.BORDER.toString());

        borderSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, 10.0d, 1.0d));

        org.openide.awt.Mnemonics.setLocalizedText(borderColorLabel, Dict.COLOR.toString());

        borderColorPanel.setMinimumSize(new java.awt.Dimension(96, 36));
        borderColorPanel.setPreferredSize(new java.awt.Dimension(150, 36));

        javax.swing.GroupLayout borderColorPanelLayout = new javax.swing.GroupLayout(borderColorPanel);
        borderColorPanel.setLayout(borderColorPanelLayout);
        borderColorPanelLayout.setHorizontalGroup(
            borderColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        borderColorPanelLayout.setVerticalGroup(
            borderColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, ":"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, Dict.SIZE.toString());

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(PropertiesPanel.class, "PropertiesPanel.saveButton.text")); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(borderSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(borderColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addComponent(ratioLabel)
                            .addComponent(borderLabel)
                            .addComponent(jLabel6)
                            .addComponent(widthLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(heightLabel)
                                    .addComponent(borderColorLabel))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ratioLabel)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(widthLabel)
                    .addComponent(heightLabel))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borderLabel)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(borderColorLabel))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borderSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borderColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        mOptions.put(KEY_HEIGHT, (int) heightSpinner.getValue());
        mOptions.put(KEY_WIDTH, (int) widthSpinner.getValue());
        mOptions.put(KEY_BORDER_SIZE, (double) borderSpinner.getValue());
        mOptions.put(KEY_BORDER_COLOR, GraphicsHelper.colorToString(borderColorPanel.getColor()));

    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel borderColorLabel;
    private se.trixon.almond.util.swing.ColorPanel borderColorPanel;
    private javax.swing.JLabel borderLabel;
    private javax.swing.JSpinner borderSpinner;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel ratioLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JSpinner widthSpinner;
    // End of variables declaration//GEN-END:variables
}
