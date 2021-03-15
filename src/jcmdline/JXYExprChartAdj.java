/*
 * MFP project, JXYExprChartAdj.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JXYExprChartAdj.java
 *
 * Created on 26/09/2013, 8:57:56 AM
 */
package jcmdline;

import com.cyzapps.PlotAdapter.FlatChartView;
import com.cyzapps.PlotAdapter.XYExprChart;
import com.cyzapps.PlotAdapter.XYExprChartOperator;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author tonyc
 */
public class JXYExprChartAdj extends javax.swing.JDialog {

    public static class AdjXYExprChartParams implements Serializable {

        public double mdXFrom = -5;
        public double mdXTo = 5;
        public double mdYFrom = -5;
        public double mdYTo = 5;
        public int mnNumOfSteps = XYExprChartOperator.DEFAULT_NUM_OF_STEPS;
        public boolean mbAutoStep = true;

        public boolean isNoAdj(AdjXYExprChartParams adjOriginal) {
            return (mdXFrom == adjOriginal.mdXFrom) && (mdXTo == adjOriginal.mdXTo)
                    && (mdYFrom == adjOriginal.mdYFrom) && (mdYTo == adjOriginal.mdYTo)
                    && (mnNumOfSteps == adjOriginal.mnNumOfSteps)
                    && (mbAutoStep == adjOriginal.mbAutoStep);
        }

        public boolean isNoAdj(double dOriginalXFrom, double dOriginalXTo, double dOriginalYFrom, double dOriginalYTo,
                int nOriginalNumOfSteps, boolean bOriginalAutoStep) {
            return (mdXFrom == dOriginalXFrom) && (mdXTo == dOriginalXTo)
                    && (mdYFrom == dOriginalYFrom) && (mdYTo == dOriginalYTo)
                    && (mnNumOfSteps == nOriginalNumOfSteps)
                    && (mbAutoStep == bOriginalAutoStep);
        }

    }

    public XYExprChart mxyChart = new XYExprChart();

    /**
     * Creates new form JXYExprChartAdj
     */
    public JXYExprChartAdj(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle(JCmdLineApp.getStringsClass().get_XYExpr_config());
    }

    public JXYExprChartAdj(java.awt.Frame parent, boolean modal, XYExprChart xyChart) {
        super(parent, modal);
        initComponents();
        if (xyChart != null) {
            mxyChart = xyChart;
        }
        setTitle(JCmdLineApp.getStringsClass().get_XYExpr_config() + " : " + mxyChart.mstrChartTitle);
        lblXFrom.setText(mxyChart.mstrXAxisName + " " + lblXFrom.getText());
        lblYFrom.setText(mxyChart.mstrYAxisName + " " + lblYFrom.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblXFrom = new javax.swing.JLabel();
        txtXFrom = new javax.swing.JTextField();
        lblXTo = new javax.swing.JLabel();
        txtXTo = new javax.swing.JTextField();
        lblYFrom = new javax.swing.JLabel();
        txtYFrom = new javax.swing.JTextField();
        lblYTo = new javax.swing.JLabel();
        txtYTo = new javax.swing.JTextField();
        lblNumOfSteps = new javax.swing.JLabel();
        txtNumOfSteps = new javax.swing.JTextField();
        chkDetectSing = new javax.swing.JCheckBox();
        btnClose = new javax.swing.JButton();
        lblYInputNote = new javax.swing.JLabel();
        lblXInputNote = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(JCmdLineApp.getStringsClass().get_XYExpr_config());
        setName("Form"); // NOI18N

        lblXFrom.setText(JCmdLineApp.getStringsClass().get_variable_from());
        lblXFrom.setName("lblXFrom"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getResourceMap(JXYExprChartAdj.class);
        txtXFrom.setText(resourceMap.getString("txtXFrom.text")); // NOI18N
        txtXFrom.setName("txtXFrom"); // NOI18N
        txtXFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtXFromFocusLost(evt);
            }
        });

        lblXTo.setText(JCmdLineApp.getStringsClass().get_variable_to());
        lblXTo.setName("lblXTo"); // NOI18N

        txtXTo.setText(resourceMap.getString("txtXTo.text")); // NOI18N
        txtXTo.setName("txtXTo"); // NOI18N
        txtXTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtXToFocusLost(evt);
            }
        });

        lblYFrom.setText(JCmdLineApp.getStringsClass().get_variable_from());
        lblYFrom.setName("lblYFrom"); // NOI18N

        txtYFrom.setText(resourceMap.getString("txtYFrom.text")); // NOI18N
        txtYFrom.setName("txtYFrom"); // NOI18N
        txtYFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtYFromFocusLost(evt);
            }
        });

        lblYTo.setText(JCmdLineApp.getStringsClass().get_variable_to());
        lblYTo.setName("lblYTo"); // NOI18N

        txtYTo.setText(resourceMap.getString("txtYTo.text")); // NOI18N
        txtYTo.setName("txtYTo"); // NOI18N
        txtYTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtYToFocusLost(evt);
            }
        });

        lblNumOfSteps.setText(JCmdLineApp.getStringsClass().get_variable_number_of_steps());
        lblNumOfSteps.setName("lblNumOfSteps"); // NOI18N

        txtNumOfSteps.setText(resourceMap.getString("txtNumOfSteps.text")); // NOI18N
        txtNumOfSteps.setName("txtNumOfSteps"); // NOI18N
        txtNumOfSteps.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumOfStepsFocusLost(evt);
            }
        });

        chkDetectSing.setText(JCmdLineApp.getStringsClass().get_detect_singular_points());
        chkDetectSing.setName("chkDetectSing"); // NOI18N
        chkDetectSing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDetectSingActionPerformed(evt);
            }
        });

        btnClose.setText(JCmdLineApp.getStringsClass().get_apply());
        btnClose.setName("btnClose"); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblYInputNote.setText(resourceMap.getString("lblYInputNote.text")); // NOI18N
        lblYInputNote.setName("lblYInputNote"); // NOI18N

        lblXInputNote.setText(resourceMap.getString("lblXInputNote.text")); // NOI18N
        lblXInputNote.setName("lblXInputNote"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblXInputNote, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblXFrom)
                        .addGap(18, 18, 18)
                        .addComponent(txtXFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblXTo)
                        .addGap(18, 18, 18)
                        .addComponent(txtXTo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblYFrom)
                        .addGap(18, 18, 18)
                        .addComponent(txtYFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblYTo)
                        .addGap(18, 18, 18)
                        .addComponent(txtYTo, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                    .addComponent(lblYInputNote, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnClose)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNumOfSteps)
                                .addGap(18, 18, 18)
                                .addComponent(txtNumOfSteps, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkDetectSing)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXFrom)
                    .addComponent(txtXFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblXTo)
                    .addComponent(txtXTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblXInputNote)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYFrom)
                    .addComponent(txtYFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblYTo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblYInputNote)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumOfSteps)
                    .addComponent(txtNumOfSteps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkDetectSing))
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtXFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtXFromFocusLost
        Double dInput = validateToFromTextInput(txtXFrom, txtXTo, getXFromInitialValue(), getXToInitialValue(), true);
        if (dInput != null) {
            mdXFrom = dInput;
            try {
                mdXTo = Double.parseDouble(txtXTo.getText().toString());
            } catch (NumberFormatException e) {
                // this will not happen coz the text has been validated in function validateToFromTextInput
            }
            mbValidXFrom = true;
            mbValidXTo = true;
        } else {
            mbValidXFrom = false;
            mbValidXTo = false;
        }
    }//GEN-LAST:event_txtXFromFocusLost

    private void txtXToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtXToFocusLost
        Double dInput = validateToFromTextInput(txtXFrom, txtXTo, getXFromInitialValue(), getXToInitialValue(), false);
        if (dInput != null) {
            mdXTo = dInput;
            try {
                mdXFrom = Double.parseDouble(txtXFrom.getText().toString());
            } catch (NumberFormatException e) {
                // this will not happen coz the text has been validated in function validateToFromTextInput
            }
            mbValidXTo = true;
            mbValidXFrom = true;
        } else {
            mbValidXTo = false;
            mbValidXFrom = false;
        }
    }//GEN-LAST:event_txtXToFocusLost

    private void txtYFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtYFromFocusLost
        Double dInput = validateToFromTextInput(txtYFrom, txtYTo, getYFromInitialValue(), getYToInitialValue(), true);
        if (dInput != null) {
            mdYFrom = dInput;
            try {
                mdYTo = Double.parseDouble(txtYTo.getText().toString());
            } catch (NumberFormatException e) {
                // this will not happen coz the text has been validated in function validateToFromTextInput
            }
            mbValidYFrom = true;
            mbValidYTo = true;
        } else {
            mbValidYFrom = false;
            mbValidYTo = false;
        }
    }//GEN-LAST:event_txtYFromFocusLost

    private void txtYToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtYToFocusLost
        Double dInput = validateToFromTextInput(txtYFrom, txtYTo, getYFromInitialValue(), getYToInitialValue(), false);
        if (dInput != null) {
            mdYTo = dInput;
            try {
                mdYFrom = Double.parseDouble(txtYFrom.getText().toString());
            } catch (NumberFormatException e) {
                // this will not happen coz the text has been validated in function validateToFromTextInput
            }
            mbValidYTo = true;
            mbValidYFrom = true;
        } else {
            mbValidYTo = false;
            mbValidYFrom = false;
        }
    }//GEN-LAST:event_txtYToFocusLost

    private void txtNumOfStepsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumOfStepsFocusLost
        Integer nInput = validateInclusiveIntRange(txtNumOfSteps, NUMOFSTEPS_INITIAL_VALUE, MIN_NUM_OF_STEPS, MAX_NUM_OF_STEPS);
        if (nInput != null) {
            mnNumOfSteps = nInput;
            mbValidNumOfSteps = true;
        } else {
            mbValidNumOfSteps = false;
        }
    }//GEN-LAST:event_txtNumOfStepsFocusLost

    private void chkDetectSingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDetectSingActionPerformed
        if (chkDetectSing.isSelected()) {
            String strStoredLCP = mstrLastChangedParam;
            txtNumOfSteps.setText("" + NUMOFSTEPS_INITIAL_VALUE);
            txtNumOfSteps.setBackground(NORMAL_BKGRND_COLOR);
            mbValidNumOfSteps = true;
            txtNumOfSteps.setEnabled(false);
            mstrLastChangedParam = strStoredLCP;    // restore last changed param.
        } else {
            txtNumOfSteps.setEnabled(true);
        }
        mbAutoStep = chkDetectSing.isSelected();
    }//GEN-LAST:event_chkDetectSingActionPerformed

    public void applyParams() {
        mxyChart.applyCfgChart(getAdjParams());
        ((FlatChartView) (mxyChart.getGraphContainer())).getCanvas().repaint();
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        applyParams();
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JXYExprChartAdj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JXYExprChartAdj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JXYExprChartAdj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JXYExprChartAdj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JXYExprChartAdj dialog = new JXYExprChartAdj(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnClose;
    protected javax.swing.JCheckBox chkDetectSing;
    protected javax.swing.JLabel lblNumOfSteps;
    protected javax.swing.JLabel lblXFrom;
    protected javax.swing.JLabel lblXInputNote;
    protected javax.swing.JLabel lblXTo;
    protected javax.swing.JLabel lblYFrom;
    protected javax.swing.JLabel lblYInputNote;
    protected javax.swing.JLabel lblYTo;
    protected javax.swing.JTextField txtNumOfSteps;
    protected javax.swing.JTextField txtXFrom;
    protected javax.swing.JTextField txtXTo;
    protected javax.swing.JTextField txtYFrom;
    protected javax.swing.JTextField txtYTo;
    // End of variables declaration//GEN-END:variables

    public static final Color NORMAL_BKGRND_COLOR = Color.WHITE;
    public static final Color ERROR_BKGRND_COLOR = Color.YELLOW;

    public static final int MAX_NUM_OF_STEPS = XYExprChartOperator.DEFAULT_NUM_OF_STEPS * 5;
    public static final int MIN_NUM_OF_STEPS = XYExprChartOperator.DEFAULT_NUM_OF_STEPS / 5;

    public static final double XFROM_INITIAL_VALUE = -5.0;
    public static final double XTO_INITIAL_VALUE = 5.0;
    public static final double YFROM_INITIAL_VALUE = -5.0;
    public static final double YTO_INITIAL_VALUE = 5.0;
    public static final int NUMOFSTEPS_INITIAL_VALUE = XYExprChartOperator.DEFAULT_NUM_OF_STEPS;
    public static final boolean AUTOSTEP_INITIAL_VALUE = true;

    protected double mdXFrom = getXFromInitialValue();
    protected double mdXTo = getXToInitialValue();
    protected double mdYFrom = getYFromInitialValue();
    protected double mdYTo = getYToInitialValue();
    protected int mnNumOfSteps = NUMOFSTEPS_INITIAL_VALUE;
    protected boolean mbAutoStep = AUTOSTEP_INITIAL_VALUE;

    public double getXFromInitialValue() {
        return XFROM_INITIAL_VALUE;
    }

    public double getXToInitialValue() {
        return XTO_INITIAL_VALUE;
    }

    public double getYFromInitialValue() {
        return YFROM_INITIAL_VALUE;
    }

    public double getYToInitialValue() {
        return YTO_INITIAL_VALUE;
    }

    protected String mstrLastChangedParam = "";
    protected boolean mbValidXFrom = true;
    protected boolean mbValidXTo = true;
    protected boolean mbValidYFrom = true;
    protected boolean mbValidYTo = true;
    protected boolean mbValidNumOfSteps = true;

    public static Double validateToFromTextInput(javax.swing.JTextField txtFrom, javax.swing.JTextField txtTo, Double dDefaultFrom, Double dDefaultTo, boolean bReturnFrom) {
        String strFrom = txtFrom.getText().toString();
        String strTo = txtTo.getText().toString();
        double dValueFrom = dDefaultFrom, dValueTo = dDefaultTo;
        Boolean bInputRight = true;
        try {
            dValueFrom = Double.parseDouble(strFrom);
            dValueTo = Double.parseDouble(strTo);
        } catch (NumberFormatException e) {
            bInputRight = false;
        }
        if (bInputRight && dValueFrom >= dValueTo) {
            bInputRight = false;
        }
        if (!bInputRight) {
            txtFrom.setBackground(ERROR_BKGRND_COLOR);
            txtTo.setBackground(ERROR_BKGRND_COLOR);
        } else {
            txtFrom.setBackground(NORMAL_BKGRND_COLOR);
            txtTo.setBackground(NORMAL_BKGRND_COLOR);
        }
        if (bInputRight) {
            return bReturnFrom ? dValueFrom : dValueTo;
        } else {
            return null;
        }
    }

    public static Integer validateInclusiveIntRange(javax.swing.JTextField txtInput, Integer nDefault, Integer nMin, Integer nMax) {
        String strText = txtInput.getText().toString();
        int nValue = nDefault;
        Boolean bInputRight = true;
        try {
            nValue = Integer.parseInt(strText);
        } catch (NumberFormatException e) {
            bInputRight = false;
        }
        if (nMin != null && nValue < nMin) {
            bInputRight = false;
        } else if (nMax != null && nValue > nMax) {
            bInputRight = false;
        }
        if (!bInputRight) {
            txtInput.setBackground(ERROR_BKGRND_COLOR);
        } else {
            txtInput.setBackground(NORMAL_BKGRND_COLOR);
        }
        if (bInputRight) {
            return nValue;
        } else {
            return null;
        }
    }

    public void resetParams() {
        mdXFrom = getXFromInitialValue();
        mdXTo = getXToInitialValue();
        mdYFrom = getYFromInitialValue();
        mdYTo = getYToInitialValue();
        mnNumOfSteps = NUMOFSTEPS_INITIAL_VALUE;
        mbAutoStep = AUTOSTEP_INITIAL_VALUE;

        txtXFrom.setText("" + mdXFrom);
        txtXTo.setText("" + mdXTo);
        txtYFrom.setText("" + mdYFrom);
        txtYTo.setText("" + mdYTo);
        txtNumOfSteps.setText("" + mnNumOfSteps);
        chkDetectSing.setSelected(mbAutoStep);    // txtNumOfSteps should be automatically enabled or disabled.

        validateInput();
    }

    public void resetParams(AdjXYExprChartParams adjXYExprChartParams) {
        mdXFrom = adjXYExprChartParams.mdXFrom;
        mdXTo = adjXYExprChartParams.mdXTo;
        mdYFrom = adjXYExprChartParams.mdYFrom;
        mdYTo = adjXYExprChartParams.mdYTo;
        mnNumOfSteps = adjXYExprChartParams.mnNumOfSteps;
        mbAutoStep = adjXYExprChartParams.mbAutoStep;

        txtXFrom.setText("" + mdXFrom);
        txtXTo.setText("" + mdXTo);
        txtYFrom.setText("" + mdYFrom);
        txtYTo.setText("" + mdYTo);
        txtNumOfSteps.setText("" + mnNumOfSteps);
        chkDetectSing.setSelected(mbAutoStep);    // txtNumOfSteps should be automatically enabled or disabled.

        validateInput();
    }

    public void validateInput() {
        txtXFromFocusLost(null);
        txtXToFocusLost(null);
        txtYFromFocusLost(null);
        txtYToFocusLost(null);
        txtNumOfStepsFocusLost(null);
        chkDetectSingActionPerformed(null);
    }

    public AdjXYExprChartParams getAdjParams() {
        AdjXYExprChartParams adjParams = new AdjXYExprChartParams();
        adjParams.mdXFrom = mdXFrom;
        adjParams.mdXTo = mdXTo;
        adjParams.mdYFrom = mdYFrom;
        adjParams.mdYTo = mdYTo;
        adjParams.mnNumOfSteps = mnNumOfSteps;
        adjParams.mbAutoStep = mbAutoStep;
        return adjParams;
    }
}
