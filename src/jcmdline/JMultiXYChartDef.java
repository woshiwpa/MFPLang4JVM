/*
 * MFP project, JMultiXYChartDef.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JMultiXYChartDef.java
 *
 * Created on 24/04/2012, 10:32:09 AM
 */
package jcmdline;

import com.cyzapps.Jfcalc.PlotLib;
import java.awt.Color;
import javax.swing.BoxLayout;

/**
 *
 * @author tonyc
 */
public class JMultiXYChartDef extends javax.swing.JDialog {

    /**
     * Creates new form JMultiXYChartDef
     */
    public JMultiXYChartDef(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        panelCurves.setLayout(new BoxLayout(panelCurves, BoxLayout.PAGE_AXIS));
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

        lblChartName = new javax.swing.JLabel();
        txtFieldChartName = new javax.swing.JTextField();
        lblChartTitle = new javax.swing.JLabel();
        txtFieldChartTitle = new javax.swing.JTextField();
        lblXTitle = new javax.swing.JLabel();
        txtFieldXTitle = new javax.swing.JTextField();
        lblYTitle = new javax.swing.JLabel();
        txtFieldYTitle = new javax.swing.JTextField();
        chkShowGrid = new javax.swing.JCheckBox();
        btnAddCurve = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnCopy2CmdLine = new javax.swing.JButton();
        scrollPanelCurves = new javax.swing.JScrollPane();
        panelCurves = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(JCmdLineApp.getStringsClass().get_XY_chart_def_title());
        setName("Form"); // NOI18N
        setResizable(false);

        lblChartName.setText(JCmdLineApp.getStringsClass().get_graph_name_prompt());
        lblChartName.setName("lblChartName"); // NOI18N

        txtFieldChartName.setName("txtFieldChartName"); // NOI18N
        txtFieldChartName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFieldChartNameFocusLost(evt);
            }
        });

        lblChartTitle.setText(JCmdLineApp.getStringsClass().get_graph_title_prompt());
        lblChartTitle.setName("lblChartTitle"); // NOI18N

        txtFieldChartTitle.setName("txtFieldChartTitle"); // NOI18N

        lblXTitle.setText(JCmdLineApp.getStringsClass().get_graph_Xtitle_prompt());
        lblXTitle.setName("lblXTitle"); // NOI18N

        txtFieldXTitle.setName("txtFieldXTitle"); // NOI18N

        lblYTitle.setText(JCmdLineApp.getStringsClass().get_graph_Ytitle_prompt());
        lblYTitle.setName("lblYTitle"); // NOI18N

        txtFieldYTitle.setName("txtFieldYTitle"); // NOI18N

        chkShowGrid.setSelected(true);
        chkShowGrid.setText(JCmdLineApp.getStringsClass().get_graph_show_grid_chkbox_prompt());
        chkShowGrid.setName("chkShowGrid"); // NOI18N

        btnAddCurve.setText(JCmdLineApp.getStringsClass().get_add_curve_btn_text());
        btnAddCurve.setName("btnAddCurve"); // NOI18N
        btnAddCurve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCurveActionPerformed(evt);
            }
        });

        btnClear.setText(JCmdLineApp.getStringsClass().get_clear_all_btn_text());
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnCopy2CmdLine.setText(JCmdLineApp.getStringsClass().get_copy_to_main_window());
        btnCopy2CmdLine.setName("btnCopy2CmdLine"); // NOI18N
        btnCopy2CmdLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopy2CmdLineActionPerformed(evt);
            }
        });

        scrollPanelCurves.setName("scrollPanelCurves"); // NOI18N

        panelCurves.setName("panelCurves"); // NOI18N

        javax.swing.GroupLayout panelCurvesLayout = new javax.swing.GroupLayout(panelCurves);
        panelCurves.setLayout(panelCurvesLayout);
        panelCurvesLayout.setHorizontalGroup(
            panelCurvesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        panelCurvesLayout.setVerticalGroup(
            panelCurvesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 289, Short.MAX_VALUE)
        );

        scrollPanelCurves.setViewportView(panelCurves);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddCurve)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear)
                        .addGap(18, 18, 18)
                        .addComponent(btnCopy2CmdLine))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblXTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFieldXTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblYTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFieldYTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblChartName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFieldChartName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblChartTitle)
                                .addGap(9, 9, 9)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(chkShowGrid))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFieldChartTitle))))
                    .addComponent(scrollPanelCurves, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChartName)
                    .addComponent(txtFieldChartName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChartTitle)
                    .addComponent(txtFieldChartTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXTitle)
                    .addComponent(txtFieldXTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkShowGrid)
                    .addComponent(lblYTitle)
                    .addComponent(txtFieldYTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCurve)
                    .addComponent(btnClear)
                    .addComponent(btnCopy2CmdLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPanelCurves, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCurveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCurveActionPerformed
        if (mnNumofCurves >= MAX_NUMBER_OF_CURVES) {
            return;
        }
        int nCount = panelCurves.getComponentCount();
        while (nCount > mnNumofCurves) {
            panelCurves.remove(--nCount);
        }
        marrayCurveDef[mnNumofCurves] = initNewCurveDef();
        marrayCurveDef[mnNumofCurves].mnIndex = mnNumofCurves;
        panelCurves.add(marrayCurveDef[mnNumofCurves], mnNumofCurves);
        panelCurves.revalidate();
        mnNumofCurves++;
    }//GEN-LAST:event_btnAddCurveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        for (int i = 0; i < MAX_NUMBER_OF_CURVES; i++) {
            marrayCurveDef[i] = null;
        }
        mnNumofCurves = 0;
        panelCurves.removeAll();
        panelCurves.revalidate();

        chkShowGrid.setSelected(true);  // by default it is true
        txtFieldChartName.setText("");
        txtFieldChartTitle.setText("");
        txtFieldXTitle.setText("");
        txtFieldYTitle.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    //add Escapes in ActivityPlotGraph works differently from same name function in JFXYChartViewer.
    public static String addEscapes(String strInput) {
        String strOutput = "";
        if (strInput != null) {
            for (int i = 0; i < strInput.length(); i++) {
                char cCurrent = strInput.charAt(i);
                if (cCurrent == '\"') {
                    strOutput += "\\\"";
                } else if (cCurrent == '\\') {
                    strOutput += "\\\\";
                } else {
                    strOutput += cCurrent;
                }
            }
        }
        return strOutput;
    }

    private void btnCopy2CmdLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopy2CmdLineActionPerformed
        if (JCmdLineApp.getApplication().isIdle()) {
            String strChartName = txtFieldChartName.getText();
            String strChartTitle = txtFieldChartTitle.getText();
            String strXTitle = txtFieldXTitle.getText();
            String strYTitle = txtFieldYTitle.getText();
            String strShowGrid = chkShowGrid.isSelected() ? "true" : "false";
            String strPlotCmdLine = getPlotFunctionName() + "(\"" + addEscapes(strChartName).trim() + "\",\""
                    + addEscapes(strChartTitle).trim() + "\",\""
                    + addEscapes(strXTitle).trim() + "\",\""
                    + addEscapes(strYTitle).trim() + "\",\""
                    + "black" + "\",\"" // background chart color is not implemented yet
                    + addEscapes(strShowGrid).trim() + "\"";

            for (int i = 0; i < mnNumofCurves; i++) {
                String strStep = marrayCurveDef[i].getTStep();
                if (strStep.trim().length() == 0) {    // empty string
                    strStep = "0";    //0 means automatic;
                }
                strPlotCmdLine += ",\"" + addEscapes(marrayCurveDef[i].getCurveTitle()).trim()
                        + "\",\"" + addEscapes(marrayCurveDef[i].getCurveColor()).trim()
                        + "\",\"" + addEscapes(marrayCurveDef[i].getCurvePntStyle()).trim()
                        + "\"," + 1 // curve point size
                        + ",\"" + addEscapes(marrayCurveDef[i].getCurveColor()).trim()
                        + "\",\"" + "solid" // curve line style
                        + "\"," + (marrayCurveDef[i].getCurveShowLn() ? 1 : 0)
                        + ",\"t\"," + marrayCurveDef[i].getTFrom()
                        + "," + marrayCurveDef[i].getTTo()
                        + "," + strStep
                        + ",\"" + addEscapes(marrayCurveDef[i].getXt()).trim()
                        + "\",\"" + addEscapes(marrayCurveDef[i].getYt()).trim() + "\"";
            }
            strPlotCmdLine += ")";
            JCmdLineView cmdLineView = (JCmdLineView) JCmdLineApp.getApplication().getMainView();
            cmdLineView.pasteNewCmd(strPlotCmdLine);
        }
    }//GEN-LAST:event_btnCopy2CmdLineActionPerformed

    private void txtFieldChartNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFieldChartNameFocusLost
        String strChartName = txtFieldChartName.getText();
        if (strChartName.contains("\\") || strChartName.contains(":") || strChartName.contains("*")
                || strChartName.contains("?") || strChartName.contains("\"") || strChartName.contains("<")
                || strChartName.contains(">") || strChartName.contains("|")) {
            // invalid chart name character
            txtFieldChartName.setForeground(Color.red);
            btnCopy2CmdLine.setEnabled(false);
        } else {
            txtFieldChartName.setForeground(Color.black);
            if (JCmdLineApp.getApplication().isIdle()) {
                btnCopy2CmdLine.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtFieldChartNameFocusLost

    public void deleteCurve(int nIndex) {
        marrayCurveDef[nIndex] = null;
        for (int i = nIndex + 1; i < MAX_NUMBER_OF_CURVES; i++) {
            marrayCurveDef[i - 1] = marrayCurveDef[i];
            if (marrayCurveDef[i - 1] != null) {
                marrayCurveDef[i - 1].mnIndex = i - 1;
            }
        }
        marrayCurveDef[MAX_NUMBER_OF_CURVES - 1] = null;
        panelCurves.remove(nIndex);
        mnNumofCurves--;
        panelCurves.revalidate();
        panelCurves.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnAddCurve;
    protected javax.swing.JButton btnClear;
    protected javax.swing.JButton btnCopy2CmdLine;
    protected javax.swing.JCheckBox chkShowGrid;
    protected javax.swing.JLabel lblChartName;
    protected javax.swing.JLabel lblChartTitle;
    protected javax.swing.JLabel lblXTitle;
    protected javax.swing.JLabel lblYTitle;
    protected javax.swing.JPanel panelCurves;
    protected javax.swing.JScrollPane scrollPanelCurves;
    protected javax.swing.JTextField txtFieldChartName;
    protected javax.swing.JTextField txtFieldChartTitle;
    protected javax.swing.JTextField txtFieldXTitle;
    protected javax.swing.JTextField txtFieldYTitle;
    // End of variables declaration//GEN-END:variables

    protected int mnNumofCurves = 0;
    protected static final int MAX_NUMBER_OF_CURVES = 8;    // was PlotLib.MAX_NUMBER_OF_2D_CURVES_TO_PLOT;
    public static final String PLOT_FUNCTION_NAME = "plot_2d_curves";    // variable cannot be overridden, has to use function.

    public String getPlotFunctionName() {
        return PLOT_FUNCTION_NAME;
    }

    public JMultiXYCurveDef initNewCurveDef() {
        return new JMultiXYCurveDef(this);
    }

    protected JMultiXYCurveDef[] marrayCurveDef = new JMultiXYCurveDef[MAX_NUMBER_OF_CURVES];

    public javax.swing.JButton getCopy2CmdLineBtn() {
        return btnCopy2CmdLine;
    }
}