/*
 * MFP project, JMultiXYZCurveDef.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JMultiXYZCurveDef.java
 *
 * Created on 24/04/2012, 10:03:02 AM
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JMultiXYZCurveDef extends javax.swing.JPanel {

    private class PntStyleItem extends Object {

        public PntStyleItem(String strNameSeenBySys, String strNameSeenByUser) {
            mstrPntStyleNameSeenBySys = strNameSeenBySys;
            mstrPntStyleNameSeenByUser = strNameSeenByUser;
        }
        public String mstrPntStyleNameSeenBySys = "point";
        public String mstrPntStyleNameSeenByUser = JCmdLineApp.getStringsClass().get_point_point_style();

        @Override
        public String toString() {
            return mstrPntStyleNameSeenByUser;
        }
    }

    private class ColorItem extends Object {

        public ColorItem(String strNameSeenBySys, String strNameSeenByUser) {
            mstrColorNameSeenBySys = strNameSeenBySys;
            mstrColorNameSeenByUser = strNameSeenByUser;
        }
        public String mstrColorNameSeenBySys = "white";
        public String mstrColorNameSeenByUser = JCmdLineApp.getStringsClass().get_white_color();

        @Override
        public String toString() {
            return mstrColorNameSeenByUser;
        }
    }

    /**
     * Creates new form JMultiXYZCurveDef
     */
    public JMultiXYZCurveDef(JMultiXYZChartDef chartDef) {
        initComponents();
        mChartDefParent = chartDef;
        cboMaxColor.removeAllItems();
        cboMaxColor.addItem(new ColorItem("white", JCmdLineApp.getStringsClass().get_white_color()));
        cboMaxColor.addItem(new ColorItem("black", JCmdLineApp.getStringsClass().get_black_color()));
        cboMaxColor.addItem(new ColorItem("blue", JCmdLineApp.getStringsClass().get_blue_color()));
        cboMaxColor.addItem(new ColorItem("cyan", JCmdLineApp.getStringsClass().get_cyan_color()));
        cboMaxColor.addItem(new ColorItem("dkgray", JCmdLineApp.getStringsClass().get_dkgray_color()));
        cboMaxColor.addItem(new ColorItem("gray", JCmdLineApp.getStringsClass().get_gray_color()));
        cboMaxColor.addItem(new ColorItem("green", JCmdLineApp.getStringsClass().get_green_color()));
        cboMaxColor.addItem(new ColorItem("ltgray", JCmdLineApp.getStringsClass().get_ltgray_color()));
        cboMaxColor.addItem(new ColorItem("magenta", JCmdLineApp.getStringsClass().get_magenta_color()));
        cboMaxColor.addItem(new ColorItem("red", JCmdLineApp.getStringsClass().get_red_color()));
        cboMaxColor.addItem(new ColorItem("yellow", JCmdLineApp.getStringsClass().get_yellow_color()));
        cboMinColor.removeAllItems();
        cboMinColor.addItem(new ColorItem("white", JCmdLineApp.getStringsClass().get_white_color()));
        cboMinColor.addItem(new ColorItem("black", JCmdLineApp.getStringsClass().get_black_color()));
        cboMinColor.addItem(new ColorItem("blue", JCmdLineApp.getStringsClass().get_blue_color()));
        cboMinColor.addItem(new ColorItem("cyan", JCmdLineApp.getStringsClass().get_cyan_color()));
        cboMinColor.addItem(new ColorItem("dkgray", JCmdLineApp.getStringsClass().get_dkgray_color()));
        cboMinColor.addItem(new ColorItem("gray", JCmdLineApp.getStringsClass().get_gray_color()));
        cboMinColor.addItem(new ColorItem("green", JCmdLineApp.getStringsClass().get_green_color()));
        cboMinColor.addItem(new ColorItem("ltgray", JCmdLineApp.getStringsClass().get_ltgray_color()));
        cboMinColor.addItem(new ColorItem("magenta", JCmdLineApp.getStringsClass().get_magenta_color()));
        cboMinColor.addItem(new ColorItem("red", JCmdLineApp.getStringsClass().get_red_color()));
        cboMinColor.addItem(new ColorItem("yellow", JCmdLineApp.getStringsClass().get_yellow_color()));
        cboMaxColor1.removeAllItems();
        cboMaxColor1.addItem(new ColorItem("white", JCmdLineApp.getStringsClass().get_white_color()));
        cboMaxColor1.addItem(new ColorItem("black", JCmdLineApp.getStringsClass().get_black_color()));
        cboMaxColor1.addItem(new ColorItem("blue", JCmdLineApp.getStringsClass().get_blue_color()));
        cboMaxColor1.addItem(new ColorItem("cyan", JCmdLineApp.getStringsClass().get_cyan_color()));
        cboMaxColor1.addItem(new ColorItem("dkgray", JCmdLineApp.getStringsClass().get_dkgray_color()));
        cboMaxColor1.addItem(new ColorItem("gray", JCmdLineApp.getStringsClass().get_gray_color()));
        cboMaxColor1.addItem(new ColorItem("green", JCmdLineApp.getStringsClass().get_green_color()));
        cboMaxColor1.addItem(new ColorItem("ltgray", JCmdLineApp.getStringsClass().get_ltgray_color()));
        cboMaxColor1.addItem(new ColorItem("magenta", JCmdLineApp.getStringsClass().get_magenta_color()));
        cboMaxColor1.addItem(new ColorItem("red", JCmdLineApp.getStringsClass().get_red_color()));
        cboMaxColor1.addItem(new ColorItem("yellow", JCmdLineApp.getStringsClass().get_yellow_color()));
        cboMinColor1.removeAllItems();
        cboMinColor1.addItem(new ColorItem("white", JCmdLineApp.getStringsClass().get_white_color()));
        cboMinColor1.addItem(new ColorItem("black", JCmdLineApp.getStringsClass().get_black_color()));
        cboMinColor1.addItem(new ColorItem("blue", JCmdLineApp.getStringsClass().get_blue_color()));
        cboMinColor1.addItem(new ColorItem("cyan", JCmdLineApp.getStringsClass().get_cyan_color()));
        cboMinColor1.addItem(new ColorItem("dkgray", JCmdLineApp.getStringsClass().get_dkgray_color()));
        cboMinColor1.addItem(new ColorItem("gray", JCmdLineApp.getStringsClass().get_gray_color()));
        cboMinColor1.addItem(new ColorItem("green", JCmdLineApp.getStringsClass().get_green_color()));
        cboMinColor1.addItem(new ColorItem("ltgray", JCmdLineApp.getStringsClass().get_ltgray_color()));
        cboMinColor1.addItem(new ColorItem("magenta", JCmdLineApp.getStringsClass().get_magenta_color()));
        cboMinColor1.addItem(new ColorItem("red", JCmdLineApp.getStringsClass().get_red_color()));
        cboMinColor1.addItem(new ColorItem("yellow", JCmdLineApp.getStringsClass().get_yellow_color()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCurveTitle = new javax.swing.JLabel();
        txtFieldCurveTitle = new javax.swing.JTextField();
        lblMinColor = new javax.swing.JLabel();
        cboMinColor = new javax.swing.JComboBox();
        lblUFrom = new javax.swing.JLabel();
        txtFieldUFrom = new javax.swing.JTextField();
        lblUTo = new javax.swing.JLabel();
        txtFieldUTo = new javax.swing.JTextField();
        lblUStep = new javax.swing.JLabel();
        txtFieldUStep = new javax.swing.JTextField();
        lblXt = new javax.swing.JLabel();
        txtFieldXuv = new javax.swing.JTextField();
        lblYt = new javax.swing.JLabel();
        txtFieldYuv = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblMaxColor = new javax.swing.JLabel();
        cboMaxColor = new javax.swing.JComboBox();
        lblMaxColorValue = new javax.swing.JLabel();
        txtFieldMaxColorValue = new javax.swing.JTextField();
        lblMinColorValue = new javax.swing.JLabel();
        txtFieldMinColorValue = new javax.swing.JTextField();
        lblVFrom = new javax.swing.JLabel();
        txtFieldVFrom = new javax.swing.JTextField();
        lblVTo = new javax.swing.JLabel();
        txtFieldVTo = new javax.swing.JTextField();
        lblVStep = new javax.swing.JLabel();
        txtFieldVStep = new javax.swing.JTextField();
        lblXEqual = new javax.swing.JLabel();
        lblYEqual = new javax.swing.JLabel();
        txtFieldZuv = new javax.swing.JTextField();
        lblZEqual = new javax.swing.JLabel();
        cboMaxColor1 = new javax.swing.JComboBox();
        cboMinColor1 = new javax.swing.JComboBox();
        chkIsGrid = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        lblCurveTitle.setText(JCmdLineApp.getStringsClass().get_curve_title_prompt());
        lblCurveTitle.setName("lblCurveTitle"); // NOI18N

        txtFieldCurveTitle.setName("txtFieldCurveTitle"); // NOI18N

        lblMinColor.setText(JCmdLineApp.getStringsClass().get_min_color_prompt());
        lblMinColor.setName("lblMinColor"); // NOI18N

        cboMinColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMinColor.setName("cboMinColor"); // NOI18N

        lblUFrom.setText(JCmdLineApp.getStringsClass().get_u_from_prompt());
        lblUFrom.setName("lblUFrom"); // NOI18N

        txtFieldUFrom.setName("txtFieldUFrom"); // NOI18N

        lblUTo.setText(JCmdLineApp.getStringsClass().get_u_to_prompt());
        lblUTo.setName("lblUTo"); // NOI18N

        txtFieldUTo.setName("txtFieldUTo"); // NOI18N

        lblUStep.setText(JCmdLineApp.getStringsClass().get_u_step_prompt());
        lblUStep.setName("lblUStep"); // NOI18N

        txtFieldUStep.setName("txtFieldUStep"); // NOI18N

        lblXt.setName("lblXt"); // NOI18N

        txtFieldXuv.setName("txtFieldXuv"); // NOI18N

        lblYt.setName("lblYt"); // NOI18N

        txtFieldYuv.setName("txtFieldYuv"); // NOI18N

        btnDelete.setText(JCmdLineApp.getStringsClass().get_delete_curve_btn_text());
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText(JCmdLineApp.getStringsClass().get_clear_curve_btn_text());
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jSeparator1.setName("jSeparator1"); // NOI18N

        lblMaxColor.setText(JCmdLineApp.getStringsClass().get_max_color_prompt());
        lblMaxColor.setName("lblMaxColor"); // NOI18N

        cboMaxColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaxColor.setName("cboMaxColor"); // NOI18N

        lblMaxColorValue.setText(JCmdLineApp.getStringsClass().get_max_color_value_prompt());
        lblMaxColorValue.setName("lblMaxColorValue"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getResourceMap(JMultiXYZCurveDef.class);
        txtFieldMaxColorValue.setText(resourceMap.getString("txtFieldMaxColorValue.text")); // NOI18N
        txtFieldMaxColorValue.setName("txtFieldMaxColorValue"); // NOI18N

        lblMinColorValue.setText(JCmdLineApp.getStringsClass().get_min_color_value_prompt());
        lblMinColorValue.setName("lblMinColorValue"); // NOI18N

        txtFieldMinColorValue.setText(resourceMap.getString("txtFieldMinColorValue.text")); // NOI18N
        txtFieldMinColorValue.setName("txtFieldMinColorValue"); // NOI18N

        lblVFrom.setText(JCmdLineApp.getStringsClass().get_v_from_prompt());
        lblVFrom.setName("lblVFrom"); // NOI18N

        txtFieldVFrom.setName("txtFieldVFrom"); // NOI18N

        lblVTo.setText(JCmdLineApp.getStringsClass().get_t_to_prompt());
        lblVTo.setName("lblVTo"); // NOI18N

        txtFieldVTo.setName("txtFieldVTo"); // NOI18N

        lblVStep.setText(JCmdLineApp.getStringsClass().get_v_step_prompt());
        lblVStep.setName("lblVStep"); // NOI18N

        txtFieldVStep.setName("txtFieldVStep"); // NOI18N

        lblXEqual.setText(resourceMap.getString("lblXEqual.text")); // NOI18N
        lblXEqual.setName("lblXEqual"); // NOI18N

        lblYEqual.setText(resourceMap.getString("lblYEqual.text")); // NOI18N
        lblYEqual.setName("lblYEqual"); // NOI18N

        txtFieldZuv.setName("txtFieldZuv"); // NOI18N

        lblZEqual.setText(resourceMap.getString("lblZEqual.text")); // NOI18N
        lblZEqual.setName("lblZEqual"); // NOI18N

        cboMaxColor1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaxColor1.setName("cboMaxColor1"); // NOI18N

        cboMinColor1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMinColor1.setName("cboMinColor1"); // NOI18N

        chkIsGrid.setText(JCmdLineApp.getStringsClass().get_is_surface_grid());
        chkIsGrid.setName("chkIsGrid"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblMinColor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMinColor, 0, 97, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(cboMinColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMinColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFieldMinColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblXt)
                                .addGap(107, 107, 107)
                                .addComponent(lblYt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblMaxColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboMaxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboMaxColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMaxColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblCurveTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFieldCurveTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(chkIsGrid)))
                        .addGap(18, 18, 18)
                        .addComponent(txtFieldMaxColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblVFrom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldVFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldVTo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVStep, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUFrom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldUFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldUTo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUStep, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtFieldVStep, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFieldUStep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblXEqual)
                        .addGap(18, 18, 18)
                        .addComponent(txtFieldXuv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(lblYEqual)
                        .addGap(18, 18, 18)
                        .addComponent(txtFieldYuv, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblZEqual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldZuv, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDelete)
                .addGap(22, 22, 22)
                .addComponent(btnClear)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurveTitle)
                    .addComponent(txtFieldCurveTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkIsGrid))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxColor)
                    .addComponent(txtFieldMaxColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaxColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaxColorValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMinColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinColor)
                    .addComponent(cboMinColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinColorValue)
                    .addComponent(txtFieldMinColorValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUFrom)
                            .addComponent(txtFieldUFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUTo)
                            .addComponent(txtFieldUTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUStep))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVFrom)
                            .addComponent(txtFieldVFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVTo)
                            .addComponent(txtFieldVTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVStep)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtFieldUStep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFieldVStep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXEqual)
                    .addComponent(txtFieldXuv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblYEqual)
                    .addComponent(txtFieldYuv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblZEqual)
                    .addComponent(txtFieldZuv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXt)
                    .addComponent(lblYt))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (mChartDefParent != null) {
            mChartDefParent.deleteCurve(mnIndex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtFieldCurveTitle.setText("");
        chkIsGrid.setSelected(true);
        cboMaxColor.setSelectedIndex(0);
        cboMaxColor1.setSelectedIndex(0);
        txtFieldMaxColorValue.setText("");
        cboMinColor.setSelectedIndex(0);
        cboMinColor1.setSelectedIndex(0);
        txtFieldMinColorValue.setText("");
        txtFieldUFrom.setText("");
        txtFieldUStep.setText("");
        txtFieldUTo.setText("");
        txtFieldVFrom.setText("");
        txtFieldVStep.setText("");
        txtFieldVTo.setText("");
        txtFieldXuv.setText("");
        txtFieldYuv.setText("");
        txtFieldZuv.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JComboBox cboMaxColor;
    private javax.swing.JComboBox cboMaxColor1;
    private javax.swing.JComboBox cboMinColor;
    private javax.swing.JComboBox cboMinColor1;
    private javax.swing.JCheckBox chkIsGrid;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCurveTitle;
    private javax.swing.JLabel lblMaxColor;
    private javax.swing.JLabel lblMaxColorValue;
    private javax.swing.JLabel lblMinColor;
    private javax.swing.JLabel lblMinColorValue;
    private javax.swing.JLabel lblUFrom;
    private javax.swing.JLabel lblUStep;
    private javax.swing.JLabel lblUTo;
    private javax.swing.JLabel lblVFrom;
    private javax.swing.JLabel lblVStep;
    private javax.swing.JLabel lblVTo;
    private javax.swing.JLabel lblXEqual;
    private javax.swing.JLabel lblXt;
    private javax.swing.JLabel lblYEqual;
    private javax.swing.JLabel lblYt;
    private javax.swing.JLabel lblZEqual;
    private javax.swing.JTextField txtFieldCurveTitle;
    private javax.swing.JTextField txtFieldMaxColorValue;
    private javax.swing.JTextField txtFieldMinColorValue;
    private javax.swing.JTextField txtFieldUFrom;
    private javax.swing.JTextField txtFieldUStep;
    private javax.swing.JTextField txtFieldUTo;
    private javax.swing.JTextField txtFieldVFrom;
    private javax.swing.JTextField txtFieldVStep;
    private javax.swing.JTextField txtFieldVTo;
    private javax.swing.JTextField txtFieldXuv;
    private javax.swing.JTextField txtFieldYuv;
    private javax.swing.JTextField txtFieldZuv;
    // End of variables declaration//GEN-END:variables

    public String getCurveTitle() {
        return txtFieldCurveTitle.getText();
    }

    public Boolean getIsGrid() {
        return chkIsGrid.isSelected();
    }

    public String getMaxColor() {
        return ((ColorItem) (cboMaxColor.getSelectedItem())).mstrColorNameSeenBySys;
    }

    public String getMaxColor1() {
        return ((ColorItem) (cboMaxColor1.getSelectedItem())).mstrColorNameSeenBySys;
    }

    public String getMaxColorValue() {
        return txtFieldMaxColorValue.getText();
    }

    public Double getMaxColorDblValue() {
        String strMaxColorValue = txtFieldMaxColorValue.getText().trim();
        Double dMaxColorValue = null;
        try {
            dMaxColorValue = Double.parseDouble(strMaxColorValue);
        } catch (NumberFormatException e) {
            dMaxColorValue = null;
        }
        if (dMaxColorValue != null && (dMaxColorValue.isInfinite() || dMaxColorValue.isNaN())) {
            dMaxColorValue = null;
        }
        return dMaxColorValue;
    }

    public String getMinColor() {
        return ((ColorItem) (cboMinColor.getSelectedItem())).mstrColorNameSeenBySys;
    }

    public String getMinColor1() {
        return ((ColorItem) (cboMinColor1.getSelectedItem())).mstrColorNameSeenBySys;
    }

    public String getMinColorValue() {
        return txtFieldMinColorValue.getText();
    }

    public Double getMinColorDblValue() {
        String strMinColorValue = txtFieldMinColorValue.getText().trim();
        Double dMinColorValue = null;
        try {
            dMinColorValue = Double.parseDouble(strMinColorValue);
        } catch (NumberFormatException e) {
            dMinColorValue = null;
        }
        if (dMinColorValue != null && (dMinColorValue.isInfinite() || dMinColorValue.isNaN())) {
            dMinColorValue = null;
        }
        return dMinColorValue;
    }

    public String getUFrom() {
        return txtFieldUFrom.getText();
    }

    public String getUTo() {
        return txtFieldUTo.getText();
    }

    public String getUStep() {
        return txtFieldUStep.getText();
    }

    public String getVFrom() {
        return txtFieldVFrom.getText();
    }

    public String getVTo() {
        return txtFieldVTo.getText();
    }

    public String getVStep() {
        return txtFieldVStep.getText();
    }

    public String getXuv() {
        return txtFieldXuv.getText();
    }

    public String getYuv() {
        return txtFieldYuv.getText();
    }

    public String getZuv() {
        return txtFieldZuv.getText();
    }

    public int mnIndex = 0;

    private JMultiXYZChartDef mChartDefParent = null;
}
