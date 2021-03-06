/*
 * MFP project, JDerivativeInput.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JDerivativeInput.java
 *
 * Created on 27/06/2016, 4:24:56 PM
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JDerivativeInput extends javax.swing.JDialog {

    public static final int SELECT_1ST_ORDER_DERIVATIVE = 0;
    public static final int SELECT_2ND_ORDER_DERIVATIVE = 1;
    public static final int SELECT_3RD_ORDER_DERIVATIVE = 2;

    /**
     * Creates new form JDerivativeInput
     */
    public JDerivativeInput(java.awt.Frame parent) {
        super(parent);
        initComponents();
        cboDeriType.removeAllItems();
        cboDeriType.insertItemAt(new Object() {
            @Override
            public String toString() {
                return JCmdLineApp.getStringsClass().get_1st_order_derivative();
            }
        }, SELECT_1ST_ORDER_DERIVATIVE);
        cboDeriType.insertItemAt(new Object() {
            @Override
            public String toString() {
                return JCmdLineApp.getStringsClass().get_2nd_order_derivative();
            }
        }, SELECT_2ND_ORDER_DERIVATIVE);
        cboDeriType.insertItemAt(new Object() {
            @Override
            public String toString() {
                return JCmdLineApp.getStringsClass().get_3rd_order_derivative();
            }
        }, SELECT_3RD_ORDER_DERIVATIVE);
        cboDeriType.setSelectedIndex(SELECT_1ST_ORDER_DERIVATIVE);
        getRootPane().setDefaultButton(btnCopy2CmdLine);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cboDeriType = new javax.swing.JComboBox();
        lblDerivativeType = new javax.swing.JLabel();
        lblExpression = new javax.swing.JLabel();
        txtExpression = new javax.swing.JTextField();
        lblVarName = new javax.swing.JLabel();
        txtVarName = new javax.swing.JTextField();
        lblVarValue = new javax.swing.JLabel();
        txtVarValue = new javax.swing.JTextField();
        btnCopy2CmdLine = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(JCmdLineApp.getStringsClass().get_derivative_input_title());
        setName("Form"); // NOI18N

        cboDeriType.setName("cboDeriType"); // NOI18N

        lblDerivativeType.setText(JCmdLineApp.getStringsClass().get_you_want_to_calculate());
        lblDerivativeType.setName("lblDerivativeType"); // NOI18N

        lblExpression.setText(JCmdLineApp.getStringsClass().get_derivative_expr_prompt());
        lblExpression.setName("lblExpression"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getResourceMap(JDerivativeInput.class);
        txtExpression.setText(resourceMap.getString("txtExpression.text")); // NOI18N
        txtExpression.setName("txtExpression"); // NOI18N

        lblVarName.setText(JCmdLineApp.getStringsClass().get_variable_name());
        lblVarName.setName("lblVarName"); // NOI18N

        txtVarName.setText(resourceMap.getString("txtVarName.text")); // NOI18N
        txtVarName.setName("txtVarName"); // NOI18N

        lblVarValue.setText(JCmdLineApp.getStringsClass().get_variable_value());
        lblVarValue.setName("lblVarValue"); // NOI18N

        txtVarValue.setText(resourceMap.getString("txtVarValue.text")); // NOI18N
        txtVarValue.setName("txtVarValue"); // NOI18N

        btnCopy2CmdLine.setText(JCmdLineApp.getStringsClass().get_copy_to_main_window());
        btnCopy2CmdLine.setName("btnCopy2CmdLine"); // NOI18N
        btnCopy2CmdLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopy2CmdLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblVarName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExpression, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDerivativeType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                    .addComponent(lblVarValue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboDeriType, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtVarValue, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtVarName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btnCopy2CmdLine))
                    .addComponent(txtExpression, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboDeriType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDerivativeType))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpression)
                    .addComponent(txtExpression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVarName)
                    .addComponent(txtVarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnCopy2CmdLine)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVarValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVarValue))
                        .addGap(27, 27, 27))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCopy2CmdLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopy2CmdLineActionPerformed
        if (JCmdLineApp.getApplication().isIdle()) {
            // no command is running now so that we can copy.
            String strVarVal = txtVarValue.getText().trim();
            String strTmp = "";
            switch (cboDeriType.getSelectedIndex()) {
                case SELECT_1ST_ORDER_DERIVATIVE:
                    if (strVarVal.length() == 0) {
                        strTmp = "derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\")";
                    } else {
                        strTmp = "derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\", " + strVarVal + ", true)";
                    }
                    break;
                case SELECT_2ND_ORDER_DERIVATIVE:
                    if (strVarVal.length() == 0) {
                        strTmp = "derivative(derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\"), \"" + txtVarName.getText() + "\")";
                    } else {
                        strTmp = "deri_ridders(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\", " + strVarVal + ", 2)";
                    }
                    break;
                case SELECT_3RD_ORDER_DERIVATIVE:
                    if (strVarVal.length() == 0) {
                        strTmp = "derivative(derivative(derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\"), \"" + txtVarName.getText() + "\"), \"" + txtVarName.getText() + "\")";
                    } else {
                        strTmp = "deri_ridders(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\", " + strVarVal + ", 3)";
                    }
                    break;
                default:    //SELECT_1ST_ORDER_DERIVATIVE
                    if (strVarVal.length() == 0) {
                        strTmp = "derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\")";
                    } else {
                        strTmp = "derivative(\"" + txtExpression.getText() + "\", \"" + txtVarName.getText() + "\", " + strVarVal + ", true)";
                    }
                    break;
            }
            JCmdLineView cmdLineView = (JCmdLineView) JCmdLineApp.getApplication().getMainView();
            cmdLineView.pasteNewCmd(strTmp);
        }
    }//GEN-LAST:event_btnCopy2CmdLineActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy2CmdLine;
    private javax.swing.JComboBox cboDeriType;
    private javax.swing.JLabel lblDerivativeType;
    private javax.swing.JLabel lblExpression;
    private javax.swing.JLabel lblVarName;
    private javax.swing.JLabel lblVarValue;
    private javax.swing.JTextField txtExpression;
    private javax.swing.JTextField txtVarName;
    private javax.swing.JTextField txtVarValue;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getCopy2CmdLineBtn() {
        return btnCopy2CmdLine;
    }
}
