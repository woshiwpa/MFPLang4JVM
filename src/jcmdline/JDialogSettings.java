/*
 * MFP project, JDialogSettings.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * JDialogSettings.java
 *
 * Created on Apr 26, 2012, 9:19:26 PM
 */
package jcmdline;

import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jfcalc.MFPNumeric;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.adapter.MFPAdapter;
import com.cyzapps.adapter.MFPAdapter.DefLibPath;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author tonyc
 */
public class JDialogSettings extends javax.swing.JDialog {

    /**
     * Creates new form JDialogSettings
     */
    public JDialogSettings(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        txtFieldScriptFolder.setText(MFP4JavaFileMan.msstrOriginalScriptPath);
        txtAreaAdditionalUsrLibs.setText("");
        for (String str : MFP4JavaFileMan.moriginaladdUsrLibs) {
            txtAreaAdditionalUsrLibs.append(str + "\n");
        }
        txtFieldChartFolder.setText(MFP4JavaFileMan.msstrOriginalChartPath);

        if (JCmdLineApp.msnNumberofRecords <= 10) {
            cboHistoryLength.setSelectedIndex(0);
        } else if (JCmdLineApp.msnNumberofRecords <= 20) {
            cboHistoryLength.setSelectedIndex(1);
        } else if (JCmdLineApp.msnNumberofRecords <= 50) {
            cboHistoryLength.setSelectedIndex(2);
        } else {
            cboHistoryLength.setSelectedIndex(3);
        }

        txtFieldBitsOfPrecision.setText(String.valueOf(MFPAdapter.msnBitsofPrecision));

        if (MFPAdapter.msnBigSmallThresh <= 4) {
            cboSciNotationThresh.setSelectedIndex(0);
        } else if (MFPAdapter.msnBigSmallThresh <= 8) {
            cboSciNotationThresh.setSelectedIndex(1);
        } else {
            cboSciNotationThresh.setSelectedIndex(2);
        }

        txtFieldPlotRangeFrom.setText(String.valueOf(MFPAdapter.msdPlotChartVariableFrom));
        txtFieldPlotRangeTo.setText(String.valueOf(MFPAdapter.msdPlotChartVariableTo));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFieldScriptFolder = new javax.swing.JTextField();
        lblScriptFolder = new javax.swing.JLabel();
        btnSelectScriptFolder = new javax.swing.JButton();
        lblChartFolder = new javax.swing.JLabel();
        txtFieldChartFolder = new javax.swing.JTextField();
        btnSelectChartFolder = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        lblHistoryLength = new javax.swing.JLabel();
        cboHistoryLength = new javax.swing.JComboBox();
        lblBitsOfPrecision = new javax.swing.JLabel();
        lblSciNotationThresh = new javax.swing.JLabel();
        txtFieldBitsOfPrecision = new javax.swing.JTextField();
        cboSciNotationThresh = new javax.swing.JComboBox();
        lblPlotChartVariableRange = new javax.swing.JLabel();
        txtFieldPlotRangeFrom = new javax.swing.JTextField();
        lblPlotChartVariableRangeTo = new javax.swing.JLabel();
        txtFieldPlotRangeTo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaAdditionalUsrLibs = new javax.swing.JTextArea();
        lblAdditionalUsrLibs = new javax.swing.JLabel();
        btnAddAdditionalFolder = new javax.swing.JButton();
        btnAddAdditionalMFPS = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(JCmdLineApp.getStringsClass().get_settings_dialog_title());
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getResourceMap(JDialogSettings.class);
        txtFieldScriptFolder.setText(resourceMap.getString("txtFieldScriptFolder.text")); // NOI18N
        txtFieldScriptFolder.setName("txtFieldScriptFolder"); // NOI18N

        lblScriptFolder.setText(JCmdLineApp.getStringsClass().get_select_user_defined_functions_folder_prompt());
        lblScriptFolder.setName("lblScriptFolder"); // NOI18N

        btnSelectScriptFolder.setText(JCmdLineApp.getStringsClass().get_select_btn_text());
        btnSelectScriptFolder.setName("btnSelectScriptFolder"); // NOI18N
        btnSelectScriptFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectScriptFolderActionPerformed(evt);
            }
        });

        lblChartFolder.setText(JCmdLineApp.getStringsClass().get_select_charts_folder_prompt());
        lblChartFolder.setName("lblChartFolder"); // NOI18N

        txtFieldChartFolder.setText(resourceMap.getString("txtFieldChartFolder.text")); // NOI18N
        txtFieldChartFolder.setName("txtFieldChartFolder"); // NOI18N

        btnSelectChartFolder.setText(JCmdLineApp.getStringsClass().get_select_btn_text());
        btnSelectChartFolder.setName("btnSelectChartFolder"); // NOI18N
        btnSelectChartFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectChartFolderActionPerformed(evt);
            }
        });

        btnCancel.setText(JCmdLineApp.getStringsClass().get_cancel());
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOK.setText(JCmdLineApp.getStringsClass().get_ok());
        btnOK.setName("btnOK"); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        lblHistoryLength.setText(JCmdLineApp.getStringsClass().get_setting_record_length_prompt());
        lblHistoryLength.setName("lblHistoryLength"); // NOI18N

        cboHistoryLength.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "20", "50", "100" }));
        cboHistoryLength.setName("cboHistoryLength"); // NOI18N

        lblBitsOfPrecision.setText(JCmdLineApp.getStringsClass().get_setting_number_format_prompt());
        lblBitsOfPrecision.setName("lblBitsOfPrecision"); // NOI18N

        lblSciNotationThresh.setText(JCmdLineApp.getStringsClass().get_setting_scientific_notation_thresh_prompt());
        lblSciNotationThresh.setName("lblSciNotationThresh"); // NOI18N

        txtFieldBitsOfPrecision.setText(resourceMap.getString("txtFieldBitsOfPrecision.text")); // NOI18N
        txtFieldBitsOfPrecision.setName("txtFieldBitsOfPrecision"); // NOI18N

        cboSciNotationThresh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10**4", "10**8", "10**16" }));
        cboSciNotationThresh.setSelectedIndex(1);
        cboSciNotationThresh.setName("cboSciNotationThresh"); // NOI18N

        lblPlotChartVariableRange.setText(JCmdLineApp.getStringsClass().get_plot_chart_variable_range_prompt());
        lblPlotChartVariableRange.setName("lblPlotChartVariableRange"); // NOI18N

        txtFieldPlotRangeFrom.setText(resourceMap.getString("txtFieldPlotRangeFrom.text")); // NOI18N
        txtFieldPlotRangeFrom.setName("txtFieldPlotRangeFrom"); // NOI18N

        lblPlotChartVariableRangeTo.setText(JCmdLineApp.getStringsClass().get_plot_chart_variable_range_to_prompt());
        lblPlotChartVariableRangeTo.setName("lblPlotChartVariableRangeTo"); // NOI18N

        txtFieldPlotRangeTo.setText(resourceMap.getString("txtFieldPlotRangeTo.text")); // NOI18N
        txtFieldPlotRangeTo.setName("txtFieldPlotRangeTo"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtAreaAdditionalUsrLibs.setColumns(20);
        txtAreaAdditionalUsrLibs.setRows(5);
        txtAreaAdditionalUsrLibs.setName("txtAreaAdditionalUsrLibs"); // NOI18N
        jScrollPane1.setViewportView(txtAreaAdditionalUsrLibs);

        lblAdditionalUsrLibs.setText(JCmdLineApp.getStringsClass().get_additional_user_defined_libs_prompt());
        lblAdditionalUsrLibs.setName("lblAdditionalUsrLibs"); // NOI18N

        btnAddAdditionalFolder.setText(JCmdLineApp.getStringsClass().get_additional_usr_lib_folder_text());
        btnAddAdditionalFolder.setName("btnAddAdditionalFolder"); // NOI18N
        btnAddAdditionalFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAdditionalFolderActionPerformed(evt);
            }
        });

        btnAddAdditionalMFPS.setText(JCmdLineApp.getStringsClass().get_additional_usr_lib_mfps_text());
        btnAddAdditionalMFPS.setName("btnAddAdditionalMFPS"); // NOI18N
        btnAddAdditionalMFPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAdditionalMFPSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblScriptFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(txtFieldScriptFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnSelectScriptFolder))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblAdditionalUsrLibs, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(lblChartFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(lblHistoryLength, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBitsOfPrecision, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSciNotationThresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(txtFieldChartFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPlotChartVariableRange, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFieldPlotRangeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboSciNotationThresh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtFieldBitsOfPrecision, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboHistoryLength, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSelectChartFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAddAdditionalFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                    .addComponent(btnAddAdditionalMFPS, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(lblPlotChartVariableRangeTo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(txtFieldPlotRangeTo, 0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblScriptFolder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldScriptFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectScriptFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAdditionalUsrLibs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblChartFolder))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddAdditionalFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddAdditionalMFPS)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldChartFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectChartFolder))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHistoryLength)
                    .addComponent(cboHistoryLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBitsOfPrecision)
                    .addComponent(txtFieldBitsOfPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSciNotationThresh)
                    .addComponent(cboSciNotationThresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPlotChartVariableRange)
                        .addComponent(txtFieldPlotRangeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPlotChartVariableRangeTo))
                    .addComponent(txtFieldPlotRangeTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectScriptFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectScriptFolderActionPerformed
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle(JCmdLineApp.getStringsClass().get_select_folder());
            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog(null);
            File fSelected = fc.getSelectedFile();
            if (fSelected != null) {
                txtFieldScriptFolder.setText(fSelected.getAbsolutePath());
            }
    }//GEN-LAST:event_btnSelectScriptFolderActionPerformed

    private void btnAddAdditionalFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAdditionalFolderActionPerformed
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle(JCmdLineApp.getStringsClass().get_select_folder());
            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog(null);
            File fSelected = fc.getSelectedFile();
            if (fSelected != null) {
                txtAreaAdditionalUsrLibs.insert(fSelected.getAbsolutePath() + "\n", 0);
            }
    }//GEN-LAST:event_btnAddAdditionalFolderActionPerformed

    private void btnAddAdditionalMFPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAdditionalMFPSActionPerformed
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle(JCmdLineApp.getStringsClass().get_select_file());
            fc.setFileFilter(new FileNameExtensionFilter("MFPS " + JCmdLineApp.getStringsClass().get_file(), "mfps", "MFPS", "Mfps"));
            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog(null);
            File fSelected = fc.getSelectedFile();
            if (fSelected != null) {
                txtAreaAdditionalUsrLibs.insert(fSelected.getAbsolutePath() + "\n", 0);
            }
    }//GEN-LAST:event_btnAddAdditionalMFPSActionPerformed

    private void btnSelectChartFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectChartFolderActionPerformed
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle(JCmdLineApp.getStringsClass().get_select_folder());
            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog(null);
            File fSelected = fc.getSelectedFile();
            if (fSelected != null) {
                txtAreaAdditionalUsrLibs.insert(fSelected.getAbsolutePath() + "\n", 0);
            }
    }//GEN-LAST:event_btnSelectChartFolderActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
            JCmdLineApp.msnNumberofRecords = Integer.parseInt(cboHistoryLength.getSelectedItem().toString());

            // unload libs first.
            for (DefLibPath defLibPath : MFPAdapter.m_slistUsrDefLibPath) {
                MFPAdapter.unloadLib(defLibPath, JCmdLineApp.mMFP4JavaFileMan);
            }
            if (!txtFieldScriptFolder.getText().trim().equals("")) {
                MFP4JavaFileMan.msstrOriginalScriptPath = LangFileManager.msstrScriptFolder = txtFieldScriptFolder.getText().trim();
                LangFileManager.msstrScriptFolder = JCmdLineApp.getApplication().convt2AbsCanPath(LangFileManager.msstrScriptFolder);
            }
            String[] strarrayLibs = txtAreaAdditionalUsrLibs.getText().trim().split("\n");
            MFP4JavaFileMan.madditionalUsrLibs.clear();
            MFP4JavaFileMan.moriginaladdUsrLibs.clear();
            for (String libPath : strarrayLibs) {
                libPath = libPath.trim();
                if (libPath.length() == 0) {
                    continue;
                }
                MFP4JavaFileMan.moriginaladdUsrLibs.add(libPath);
                try {
                    String strCanonicalLibPath = JCmdLineApp.getApplication().convt2AbsCanPath(libPath);
                    if (IOLib.isExistingFile(strCanonicalLibPath)) { // file exist.
                        if (!MFP4JavaFileMan.madditionalUsrLibs.contains(strCanonicalLibPath)
                                && !JCmdLineApp.mMFP4JavaFileMan.getScriptFolderFullPath().equals(strCanonicalLibPath)) {
                            // if it is not a duplicate, load it.
                            MFP4JavaFileMan.madditionalUsrLibs.add(strCanonicalLibPath);
                        }
                    }
                } catch (JFCALCExpErrException ex) {
                    // the path is not valid, so ignore it.
                    Logger.getLogger(JCmdLineApp.class.getName()).log(Level.WARNING, null, ex);
                }
            }
            // now reload user libs.
            MFPAdapter.m_slistUsrDefLibPath.clear();
            MFPAdapter.getUsrLibFiles(JCmdLineApp.mMFP4JavaFileMan, JCmdLineApp.mMFP4JavaFileMan.getScriptFolderFullPath());
            for (String strPath : MFP4JavaFileMan.madditionalUsrLibs) {
                MFPAdapter.getUsrLibFiles(JCmdLineApp.mMFP4JavaFileMan, strPath);
            }
            MFPAdapter.loadUsrLib(JCmdLineApp.mMFP4JavaFileMan);
            // hide this function because we want to analyse statements on the fly            
            //MFPAdapter.analyseStatements(); // analyse the statements (using abstractexpr instead of string).

            if (txtFieldChartFolder.getText().trim().equals("") == false) {
                MFP4JavaFileMan.msstrOriginalChartPath = LangFileManager.msstrChartFolder = txtFieldChartFolder.getText().trim();
                LangFileManager.msstrChartFolder = JCmdLineApp.getApplication().convt2AbsCanPath(LangFileManager.msstrChartFolder);
            }
            int nOldBitsofPrecision = MFPAdapter.msnBitsofPrecision;
            try {
                MFPAdapter.msnBitsofPrecision = Integer.parseInt(txtFieldBitsOfPrecision.getText());
                if (MFPAdapter.msnBitsofPrecision < 0) {
                    MFPAdapter.msnBitsofPrecision = nOldBitsofPrecision;  // restore previous value.
                }
            } catch (NumberFormatException e) {
                MFPAdapter.msnBitsofPrecision = nOldBitsofPrecision;  // restore previous value.
            }

            if (cboSciNotationThresh.getSelectedIndex() == 0) {
                MFPAdapter.msnBigSmallThresh = 4;
            } else if (cboSciNotationThresh.getSelectedIndex() == 2) {
                MFPAdapter.msnBigSmallThresh = 16;
            } else {
                MFPAdapter.msnBigSmallThresh = 8;
            }
            MFPAdapter.mmfpNumBigThresh = MFPNumeric.pow(MFPNumeric.TEN, new MFPNumeric(MFPAdapter.msnBigSmallThresh));
            MFPAdapter.mmfpNumSmallThresh = MFPNumeric.pow(MFPNumeric.ONE_TENTH, new MFPNumeric(MFPAdapter.msnBigSmallThresh));

            double dOldPlotExprsVariableFrom = MFPAdapter.msdPlotChartVariableFrom;
            double dOldPlotExprsVariableTo = MFPAdapter.msdPlotChartVariableTo;
            try {
                MFPAdapter.msdPlotChartVariableFrom = Double.parseDouble(txtFieldPlotRangeFrom.getText());
                MFPAdapter.msdPlotChartVariableTo = Double.parseDouble(txtFieldPlotRangeTo.getText());
                if (!(MFPAdapter.msdPlotChartVariableFrom < MFPAdapter.msdPlotChartVariableTo)) {
                    MFPAdapter.msdPlotChartVariableFrom = dOldPlotExprsVariableFrom;
                    MFPAdapter.msdPlotChartVariableTo = dOldPlotExprsVariableTo;
                }
            } catch (NumberFormatException e) {
                MFPAdapter.msdPlotChartVariableFrom = dOldPlotExprsVariableFrom;
                MFPAdapter.msdPlotChartVariableTo = dOldPlotExprsVariableTo;
            }
            JCmdLineApp.getApplication().writeJsonSettings();
            setVisible(false);
            dispose();
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
            setVisible(false);
            dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            setVisible(false);
            dispose();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAdditionalFolder;
    private javax.swing.JButton btnAddAdditionalMFPS;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnSelectChartFolder;
    private javax.swing.JButton btnSelectScriptFolder;
    private javax.swing.JComboBox cboHistoryLength;
    private javax.swing.JComboBox cboSciNotationThresh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdditionalUsrLibs;
    private javax.swing.JLabel lblBitsOfPrecision;
    private javax.swing.JLabel lblChartFolder;
    private javax.swing.JLabel lblHistoryLength;
    private javax.swing.JLabel lblPlotChartVariableRange;
    private javax.swing.JLabel lblPlotChartVariableRangeTo;
    private javax.swing.JLabel lblSciNotationThresh;
    private javax.swing.JLabel lblScriptFolder;
    private javax.swing.JTextArea txtAreaAdditionalUsrLibs;
    private javax.swing.JTextField txtFieldBitsOfPrecision;
    private javax.swing.JTextField txtFieldChartFolder;
    private javax.swing.JTextField txtFieldPlotRangeFrom;
    private javax.swing.JTextField txtFieldPlotRangeTo;
    private javax.swing.JTextField txtFieldScriptFolder;
    // End of variables declaration//GEN-END:variables
}