/*
 * MFP project, JCmdLineView.java : Designed and developed by Tony Cui in 2021
 * JCmdLineView.java
 */
package jcmdline;

import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.PlotAdapter.ChartOperator;
import com.cyzapps.PlotAdapter.FlatChartView;
import com.cyzapps.PlotAdapter.OGLChartOperator;
import com.cyzapps.PlotAdapter.OGLChartView;
import com.cyzapps.PlotAdapter.OGLChart;
import com.cyzapps.adapter.MFPAdapter;
import java.awt.Desktop;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import javax.swing.event.DocumentEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import com.cyzapps.PlotAdapter.MFPChart;
import com.cyzapps.PlotAdapter.OGLExprChart;
import com.cyzapps.PlotAdapter.OGLExprChartOperator;
import com.cyzapps.PlotAdapter.PolarChart;
import com.cyzapps.PlotAdapter.PolarChartOperator;
import com.cyzapps.PlotAdapter.PolarExprChart;
import com.cyzapps.PlotAdapter.PolarExprChartOperator;
import com.cyzapps.PlotAdapter.XYChart;
import com.cyzapps.PlotAdapter.XYChartOperator;
import com.cyzapps.PlotAdapter.XYExprChart;
import com.cyzapps.PlotAdapter.XYExprChartOperator;
import com.cyzapps.adapter.ChartOperator.ChartCreationParam;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.DocumentListener;
import jcmdline.JCmdLineApp.JMCmdRecord;

/**
 * The application's main frame.
 */
public class JCmdLineView extends FrameView {
    
    public JCmdLineView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        // initialize command line
        txtAreaOutput.getCaret().setVisible(true);
        txtAreaOutput.setText(msstrPrompt);
        mnEditableStart = msstrPrompt.length();

        // Intercept all key events prior sending them to the focused component
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent evt) {
                if (evt.getComponent() == null || evt.getComponent().getName() == null
                        || evt.getComponent().getName().equals("txtAreaOutput") == false) {
                    // this dispatcher only works with txtAreaOutput
                    return false;
                } else {
                    // intercept the following keys
                    if (evt.getKeyCode() == KeyEvent.VK_UP && evt.getID() == KeyEvent.KEY_PRESSED) {
                        // select last cmd or input
                        if (JCmdLineApp.getApplication().isWaiting()) {
                            String strLast = JCmdLineApp.getApplication().getLastInput();
                            if (strLast != null) {
                                txtAreaOutput.setText(txtAreaOutput.getText().substring(0, mnEditableStart) + strLast);
                            }
                        } else {
                            JMCmdRecord crLast = JCmdLineApp.getApplication().getLastCommandInfo();
                            if (crLast != null) {
                                String strLast = crLast.mstrCmd;
                                if (KeyEvent.getKeyModifiersText(evt.getModifiers()).equals("Shift")) {
                                    // select last result
                                    String strLastReturn = crLast.mstrReturn;
                                    if (strLastReturn != null && !strLastReturn.equalsIgnoreCase("returns nothing")
                                            && !strLastReturn.equalsIgnoreCase("Error") && strLastReturn.trim().length() != 0) {
                                        strLast = strLastReturn;
                                    }
                                }
                                if (strLast != null) {
                                    txtAreaOutput.setText(txtAreaOutput.getText().substring(0, mnEditableStart) + strLast);
                                }
                            }
                        }
                        return true;
                    } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && evt.getID() == KeyEvent.KEY_PRESSED) {
                        // select next cmd or input
                        if (JCmdLineApp.getApplication().isWaiting()) {
                            String strNext = JCmdLineApp.getApplication().getNextInput();
                            if (strNext != null) {
                                txtAreaOutput.setText(txtAreaOutput.getText().substring(0, mnEditableStart) + strNext);
                            }
                        } else {
                            JMCmdRecord crNext = JCmdLineApp.getApplication().getNextCommandInfo();
                            if (crNext != null) {
                                String strNext = crNext.mstrCmd;
                                if (KeyEvent.getKeyModifiersText(evt.getModifiers()).equals("Shift")) {
                                    // select last result
                                    String strNextReturn = crNext.mstrReturn;
                                    if (strNextReturn != null && !strNextReturn.equalsIgnoreCase("returns nothing")
                                            && !strNextReturn.equalsIgnoreCase("Error") && strNextReturn.trim().length() != 0) {
                                        strNext = strNextReturn;
                                    }
                                }
                                if (strNext != null) {
                                    txtAreaOutput.setText(txtAreaOutput.getText().substring(0, mnEditableStart) + strNext);
                                }
                            }
                        }
                        return true;
                    } else if (KeyEvent.getKeyModifiersText(evt.getModifiers()).equals("Ctrl")
                            && evt.getKeyCode() == KeyEvent.VK_V && evt.getID() == KeyEvent.KEY_PRESSED) {
                        // intercept ctrl-V
                        pasteText();
                        return true;
                    } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && evt.getID() == KeyEvent.KEY_PRESSED) {
                        // intercept key-pressed backspace because it beeps when txtarea is read only
                        return true;
                    } else if (evt.getKeyCode() == KeyEvent.VK_DELETE && evt.getID() == KeyEvent.KEY_PRESSED) {
                        // intercept key-pressed delete because it beeps when txtarea is read only
                        return true;
                    }
                }
                return false;
            }
        });

        txtAreaOutput.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                onTextChange();
            }

            public void removeUpdate(DocumentEvent e) {
                onTextChange();
            }

            public void changedUpdate(DocumentEvent e) {
                onTextChange();
            }

        });

        txtAreaOutput.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            private void doPop(MouseEvent e) {
                JPopupMenu contextMenu = new JPopupMenu();
                JMenuItem selectAllItem = new JMenuItem(JCmdLineApp.getStringsClass().get_menu_select_all());
                selectAllItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        selectAllMenuItemActionPerformed(evt);
                    }
                });
                JMenuItem copyItem = new JMenuItem(JCmdLineApp.getStringsClass().get_menu_copy());
                copyItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        copyMenuItemActionPerformed(evt);
                    }
                });
                JMenuItem pasteItem = new JMenuItem(JCmdLineApp.getStringsClass().get_menu_paste());
                pasteItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        pasteMenuItemActionPerformed(evt);
                    }
                });
            
                contextMenu.add(selectAllItem);
                contextMenu.add(copyItem);
                contextMenu.add(pasteItem);
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        // need not to disable txtAreaOutput internal copy function.
        /* JTextComponent.KeyBinding[] newBindings = {
            new JTextComponent.KeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK),
                                        DefaultEditorKit.beepAction),
        };
            
        Keymap k = txtAreaOutput.getKeymap();
        JTextComponent.loadKeymap(k, newBindings, txtAreaOutput.getActions());*/
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = JCmdLineApp.getApplication().getMainFrame();
            aboutBox = new JCmdLineAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        JCmdLineApp.getApplication().show(aboutBox);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaOutput = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        reloadLibMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        interruptCmdMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        wrapLineMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        selectAllMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        clearMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        calculusMenu = new javax.swing.JMenu();
        derivativeMenuItem = new javax.swing.JMenuItem();
        integrateMenuItem = new javax.swing.JMenuItem();
        plotMultiXYChartMenuItem = new javax.swing.JMenuItem();
        plotPolarChartMenuItem = new javax.swing.JMenuItem();
        plotMultiXYZChartMenuItem = new javax.swing.JMenuItem();
        viewChartMenuItem = new javax.swing.JMenuItem();
        howtoMenu = new javax.swing.JMenu();
        getConstantMenuItem = new javax.swing.JMenuItem();
        cvtUnitMenuItem = new javax.swing.JMenuItem();
        rootsMenuItem = new javax.swing.JMenuItem();
        createApkMenuItem = new javax.swing.JMenuItem();
        settingsMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        contentMenu = new javax.swing.JMenu();
        contentEnglishMenuItem = new javax.swing.JMenuItem();
        contentSChineseMenuItem = new javax.swing.JMenuItem();
        contentTChineseMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtAreaOutput.setColumns(20);
        txtAreaOutput.setEditable(false);
        txtAreaOutput.setLineWrap(true);
        txtAreaOutput.setRows(5);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getResourceMap(JCmdLineView.class);
        txtAreaOutput.setText(resourceMap.getString("txtAreaOutput.text")); // NOI18N
        txtAreaOutput.setName("txtAreaOutput"); // NOI18N
        txtAreaOutput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaOutputFocusGained(evt);
            }
        });
        txtAreaOutput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaOutputKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaOutput);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(JCmdLineApp.getStringsClass().get_menu_file());
        fileMenu.setName("fileMenu"); // NOI18N

        reloadLibMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        reloadLibMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_reload_lib());
        reloadLibMenuItem.setName("reloadLibMenuItem"); // NOI18N
        reloadLibMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadLibMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(reloadLibMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        interruptCmdMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        interruptCmdMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_interrupt_cmd());
        interruptCmdMenuItem.setEnabled(false);
        interruptCmdMenuItem.setName("interruptCmdMenuItem"); // NOI18N
        interruptCmdMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interruptCmdMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(interruptCmdMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        fileMenu.add(jSeparator3);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(jcmdline.JCmdLineApp.class).getContext().getActionMap(JCmdLineView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_exit());
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(JCmdLineApp.getStringsClass().get_menu_edit());
        editMenu.setName("editMenu"); // NOI18N

        wrapLineMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        wrapLineMenuItem.setSelected(true);
        wrapLineMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_wrap_line());
        wrapLineMenuItem.setName("wrapLineMenuItem"); // NOI18N
        wrapLineMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wrapLineMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(wrapLineMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        editMenu.add(jSeparator2);

        selectAllMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        selectAllMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_select_all());
        selectAllMenuItem.setName("selectAllMenuItem"); // NOI18N
        selectAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(selectAllMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_copy());
        copyMenuItem.setName("copyMenuItem"); // NOI18N
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_paste());
        pasteMenuItem.setName("pasteMenuItem"); // NOI18N
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(pasteMenuItem);

        clearMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_clear());
        clearMenuItem.setName("clearMenuItem"); // NOI18N
        clearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(clearMenuItem);

        menuBar.add(editMenu);

        toolsMenu.setText(JCmdLineApp.getStringsClass().get_menu_tools());
        toolsMenu.setName("toolsMenu"); // NOI18N

        calculusMenu.setText(JCmdLineApp.getStringsClass().get_menu_calculus());
        calculusMenu.setName("calculusMenu"); // NOI18N

        derivativeMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_derivative());
        derivativeMenuItem.setName("derivativeMenuItem"); // NOI18N
        derivativeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derivativeMenuItemActionPerformed(evt);
            }
        });
        calculusMenu.add(derivativeMenuItem);

        integrateMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        integrateMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_integrate());
        integrateMenuItem.setName("integrateMenuItem"); // NOI18N
        integrateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                integrateMenuItemActionPerformed(evt);
            }
        });
        calculusMenu.add(integrateMenuItem);

        toolsMenu.add(calculusMenu);

        plotMultiXYChartMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        plotMultiXYChartMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_plot_multixy_graph());
        plotMultiXYChartMenuItem.setName("plotMultiXYChartMenuItem"); // NOI18N
        plotMultiXYChartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotMultiXYChartMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(plotMultiXYChartMenuItem);

        plotPolarChartMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_plot_polar_graph());
        plotPolarChartMenuItem.setName("plotPolarChartMenuItem"); // NOI18N
        plotPolarChartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotPolarChartMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(plotPolarChartMenuItem);

        plotMultiXYZChartMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        plotMultiXYZChartMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_plot_multixyz_graph());
        plotMultiXYZChartMenuItem.setName("plotMultiXYZChartMenuItem"); // NOI18N
        plotMultiXYZChartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotMultiXYZChartMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(plotMultiXYZChartMenuItem);

        viewChartMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        viewChartMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_view_chart());
        viewChartMenuItem.setName("viewChartMenuItem"); // NOI18N
        viewChartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewChartMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(viewChartMenuItem);

        howtoMenu.setText(JCmdLineApp.getStringsClass().get_menu_howto());
        howtoMenu.setName("howtoMenu"); // NOI18N

        getConstantMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_get_constant());
        getConstantMenuItem.setName("getConstantMenuItem"); // NOI18N
        getConstantMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getConstantMenuItemActionPerformed(evt);
            }
        });
        howtoMenu.add(getConstantMenuItem);

        cvtUnitMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_convert_unit());
        cvtUnitMenuItem.setName("cvtUnitMenuItem"); // NOI18N
        cvtUnitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cvtUnitMenuItemActionPerformed(evt);
            }
        });
        howtoMenu.add(cvtUnitMenuItem);

        rootsMenuItem.setText(JCmdLineApp.getApplication().getStringsClass().get_menu_calc_polynomial_roots());
        rootsMenuItem.setName("rootsMenuItem"); // NOI18N
        rootsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootsMenuItemActionPerformed(evt);
            }
        });
        howtoMenu.add(rootsMenuItem);

        toolsMenu.add(howtoMenu);

        createApkMenuItem.setText(JCmdLineApp.getStringsClass().get_mfpapp_prog_name());
        createApkMenuItem.setName("createApkMenuItem"); // NOI18N
        createApkMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createApkMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(createApkMenuItem);

        settingsMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_settings());
        settingsMenuItem.setName("settingsMenuItem"); // NOI18N
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(settingsMenuItem);

        menuBar.add(toolsMenu);

        helpMenu.setText(JCmdLineApp.getStringsClass().get_menu_help());
        helpMenu.setName("helpMenu"); // NOI18N

        contentMenu.setText(JCmdLineApp.getStringsClass().get_menu_content());
        contentMenu.setName("contentMenu"); // NOI18N

        contentEnglishMenuItem.setText(resourceMap.getString("contentEnglishMenuItem.text")); // NOI18N
        contentEnglishMenuItem.setName("contentEnglishMenuItem"); // NOI18N
        contentEnglishMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentEnglishMenuItemActionPerformed(evt);
            }
        });
        contentMenu.add(contentEnglishMenuItem);

        contentSChineseMenuItem.setText(resourceMap.getString("contentSChineseMenuItem.text")); // NOI18N
        contentSChineseMenuItem.setName("contentSChineseMenuItem"); // NOI18N
        contentSChineseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentSChineseMenuItemActionPerformed(evt);
            }
        });
        contentMenu.add(contentSChineseMenuItem);

        contentTChineseMenuItem.setText(resourceMap.getString("contentTChineseMenuItem.text")); // NOI18N
        contentTChineseMenuItem.setName("contentTChineseMenuItem"); // NOI18N
        contentTChineseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentTChineseMenuItemActionPerformed(evt);
            }
        });
        contentMenu.add(contentTChineseMenuItem);

        helpMenu.add(contentMenu);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(JCmdLineApp.getStringsClass().get_menu_about());
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaOutputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaOutputKeyTyped
            char c = evt.getKeyChar();
            int nSelectionStart = txtAreaOutput.getSelectionStart();
            int nSelectionEnd = txtAreaOutput.getSelectionEnd();
            int nSelectedEdtableStart = (nSelectionStart > mnEditableStart) ? nSelectionStart : mnEditableStart;
            int nSelectedEdtableEnd = (nSelectionEnd < mnEditableStart) ? -1 : nSelectionEnd;
            switch (c) {
                case (KeyEvent.CHAR_UNDEFINED):
                case (KeyEvent.VK_ESCAPE):
                    // do nothing for the above keys
                    return;
                case (KeyEvent.VK_BACK_SPACE):
                    // if just delete text, need not to worry the buffer size limit.
                    if (nSelectedEdtableStart < nSelectedEdtableEnd) {
                        // delete selected editable
                        txtAreaOutput.setText(txtAreaOutput.getText().substring(0, nSelectedEdtableStart)
                                + txtAreaOutput.getText().substring(nSelectedEdtableEnd));
                        txtAreaOutput.setCaretPosition(nSelectionStart);
                    } else if (nSelectedEdtableStart == nSelectedEdtableEnd) {
                        if (nSelectedEdtableEnd > mnEditableStart) {
                            // delete a character.
                            txtAreaOutput.setText(txtAreaOutput.getText().substring(0, nSelectedEdtableStart - 1)
                                    + txtAreaOutput.getText().substring(nSelectedEdtableStart));
                        } // cannot delete anything if nSelectedEdtableStart == nSelectedEdtableEnd == mnEditableStart
                        if (nSelectionStart == 0) {
                            txtAreaOutput.setCaretPosition(0);
                        } else if (nSelectionStart < nSelectionEnd) {
                            txtAreaOutput.setCaretPosition(nSelectionStart);
                        } else {   //if (nSelectionStart == nSelectionEnd)
                            txtAreaOutput.setCaretPosition(nSelectionStart - 1);
                        }
                    } else {
                        // nSelectedEdtableEnd == -1
                        // do not delete anything but move caret forward.
                        if (nSelectionStart == 0) {
                            txtAreaOutput.setCaretPosition(0);
                        } else {
                            txtAreaOutput.setCaretPosition(nSelectionStart - 1);
                        }
                    }
                    return;
                case (KeyEvent.VK_DELETE):
                    // if just delete text, need not to worry the buffer size limit.
                    if (nSelectedEdtableStart < nSelectedEdtableEnd) {
                        // delete selected editable
                        txtAreaOutput.setText(txtAreaOutput.getText().substring(0, nSelectedEdtableStart)
                                + txtAreaOutput.getText().substring(nSelectedEdtableEnd));
                        txtAreaOutput.setCaretPosition(nSelectionStart);

                    } else if (nSelectedEdtableStart == nSelectedEdtableEnd) {
                        if (nSelectedEdtableEnd < txtAreaOutput.getText().length()) {
                            // delete a character.
                            txtAreaOutput.setText(txtAreaOutput.getText().substring(0, nSelectedEdtableStart)
                                    + txtAreaOutput.getText().substring(nSelectedEdtableStart + 1));
                        }
                        txtAreaOutput.setCaretPosition(nSelectionStart);
                    } else {
                        // nSelectedEdtableEnd == -1
                        txtAreaOutput.setCaretPosition(nSelectionStart);
                    }
                    return;
                case (KeyEvent.VK_ENTER):
                    if (nSelectedEdtableStart > nSelectedEdtableEnd) {
                        // nSelectedEdtableEnd == -1
                        // do not delete anything or move caret.
                        return;
                    } else if (JCmdLineApp.getApplication().isIdle() //if waiting for a cmd input and shift is not pressed
                            && KeyEvent.getKeyModifiersText(evt.getModifiers()).equals("Shift") == false) {
                        // add a '\n' at the end of the text and will start a new cmd anyway
                        String strText = txtAreaOutput.getText();
                        strText += '\n';
                        txtAreaOutput.setText(strText);
                        break;
                    } else {
                        // replace selected editable
                        String strText = txtAreaOutput.getText();
                        strText = strText.substring(0, nSelectedEdtableStart)
                                + '\n' + strText.substring(nSelectedEdtableEnd);
                        int nCaretPosition = nSelectedEdtableStart + 1;
                        if (strText.length() > mnCmdLineBufSize) {
                            int nShiftedChars = strText.length() - mnCmdLineBufSize;
                            nCaretPosition = ((nCaretPosition - nShiftedChars) >= 0) ? (nCaretPosition - nShiftedChars) : 0;
                            mnEditableStart = ((mnEditableStart - nShiftedChars) >= 0) ? (mnEditableStart - nShiftedChars) : 0;
                            strText = strText.substring(nShiftedChars);
                        }
                        txtAreaOutput.setText(strText);
                        txtAreaOutput.setCaretPosition(nCaretPosition);
                        break;
                    }

                default:
                    if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
                            || c == '`' || c == '~' || c == '!' || c == '@' || c == '#' || c == '$'
                            || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')'
                            || c == '_' || c == '-' || c == '+' || c == '=' || c == '{' || c == '['
                            || c == '}' || c == ']' || c == '|' || c == '\\' || c == ':' || c == ';'
                            || c == '"' || c == '\'' || c == '<' || c == ',' || c == '>' || c == '.'
                            || c == '?' || c == '/' || c == ' ') {
                        int nEditStart = nSelectedEdtableStart, nEditEnd = nSelectedEdtableEnd;
                        String strText = txtAreaOutput.getText();
                        if (nEditStart > nEditEnd) {
                            nEditStart = nEditEnd = strText.length();
                        }
                        // replace selected editable
                        strText = strText.substring(0, nEditStart)
                                + c + strText.substring(nEditEnd);
                        int nCaretPosition = nEditStart + 1;
                        if (strText.length() > mnCmdLineBufSize) {
                            int nShiftedChars = strText.length() - mnCmdLineBufSize;
                            nCaretPosition = ((nCaretPosition - nShiftedChars) >= 0) ? (nCaretPosition - nShiftedChars) : 0;
                            mnEditableStart = ((mnEditableStart - nShiftedChars) >= 0) ? (mnEditableStart - nShiftedChars) : 0;
                            strText = strText.substring(nShiftedChars);
                        }
                        txtAreaOutput.setText(strText);
                        txtAreaOutput.setCaretPosition(nCaretPosition);
                    } else {
                        // nSelectedEdtableEnd == -1
                        // do not delete anything or move caret.
                        return;
                    }
            }
            String strText = txtAreaOutput.getText();
            // if last key hit was a non-shift enter, a new command will start 
            if (strText != null && strText.length() > 0 && evt.getKeyChar() == KeyEvent.VK_ENTER
                    && KeyEvent.getKeyModifiersText(evt.getModifiers()).equals("Shift") == false) {
                if (JCmdLineApp.getApplication().isIdle()) {
                    // no command is running.
                    if (strText.length() <= mnEditableStart + 1) {
                        // just press an enter
                        strText = txtAreaOutput.getText() + '\n' + msstrPrompt;
                        if (strText.length() > mnCmdLineBufSize) {
                            int nShiftedChars = strText.length() - mnCmdLineBufSize;
                            mnEditableStart = ((mnEditableStart - nShiftedChars) >= 0) ? (mnEditableStart - nShiftedChars) : 0;
                            strText = strText.substring(nShiftedChars);
                        }
                        txtAreaOutput.setText(strText);
                        txtAreaOutput.setCaretPosition(txtAreaOutput.getText().length());
                        txtAreaOutput.setSelectionEnd(txtAreaOutput.getText().length());
                        txtAreaOutput.setSelectionStart(txtAreaOutput.getText().length());
                        mnEditableStart = txtAreaOutput.getText().length(); // editable start is calculated after the empty cmd is processed.
                    } else {
                        // start a new command or a new command batch
                        String strCommands = strText.substring(mnEditableStart);
                        mnEditableStart = txtAreaOutput.getText().length(); // editable start is calculated before commands processed.
                        JCmdLineApp.getApplication().processInput(strCommands);
                    }
                }
            }
    }//GEN-LAST:event_txtAreaOutputKeyTyped

    private void txtAreaOutputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaOutputFocusGained
        txtAreaOutput.getCaret().setVisible(true);
    }//GEN-LAST:event_txtAreaOutputFocusGained

    private void selectAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllMenuItemActionPerformed
            txtAreaOutput.selectAll();
    }//GEN-LAST:event_selectAllMenuItemActionPerformed

    /*public void copyText()  {
        String str = txtAreaOutput.getSelectedText();
        StringSelection strsel = new StringSelection(str);
        Clipboard clbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clbrd.setContents(strsel, strsel);
    }*/
    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
        txtAreaOutput.copy();
    }//GEN-LAST:event_copyMenuItemActionPerformed

    public void pasteText() {
        if ((JCmdLineApp.getApplication().isIdle() || JCmdLineApp.getApplication().isWaiting())
                && txtAreaOutput.getSelectionEnd() >= mnEditableStart) {

            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            try {
                if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String strToPasteText = (String) t.getTransferData(DataFlavor.stringFlavor);
                    if (strToPasteText.equals("") == false) {
                        int nReplaceStart = Math.max(txtAreaOutput.getSelectionStart(),
                                mnEditableStart);
                        int nReplaceEnd = txtAreaOutput.getSelectionEnd();
                        String strText = txtAreaOutput.getText();
                        txtAreaOutput.setText(strText.substring(0, nReplaceStart)
                                + strToPasteText + strText.substring(nReplaceEnd));
                        txtAreaOutput.setSelectionStart(nReplaceStart + strToPasteText.length());
                        txtAreaOutput.setSelectionEnd(nReplaceStart + strToPasteText.length());
                        txtAreaOutput.setCaretPosition(nReplaceStart + strToPasteText.length());
                    }
                }
            } catch (UnsupportedFlavorException e) {
            } catch (IOException e) {
            }
        }
    }

    private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
            pasteText();
    }//GEN-LAST:event_pasteMenuItemActionPerformed

    private void clearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuItemActionPerformed
            if (JCmdLineApp.getApplication().isIdle()) {
                txtAreaOutput.setText(msstrPrompt);
                mnEditableStart = msstrPrompt.length();
                txtAreaOutput.setSelectionStart(msstrPrompt.length());
                txtAreaOutput.setSelectionEnd(msstrPrompt.length());
                txtAreaOutput.setCaretPosition(msstrPrompt.length());
            }
    }//GEN-LAST:event_clearMenuItemActionPerformed

    private void integrateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_integrateMenuItemActionPerformed
        JCmdLineApp.getApplication().showIntegrationInput();
    }//GEN-LAST:event_integrateMenuItemActionPerformed

    private void viewChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewChartMenuItemActionPerformed
        String strStartPath = JCmdLineApp.mMFP4JavaFileMan.getChartFolderFullPath();
        JFileChooser fc = new JFileChooser(new File(strStartPath));
        FileFilter ff = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                } else if (pathname.getAbsolutePath().length() > LangFileManager.STRING_CHART_EXTENSION.length()
                        && pathname.getAbsolutePath().substring(pathname.getAbsolutePath().length()
                                - LangFileManager.STRING_CHART_EXTENSION.length())
                                .equalsIgnoreCase(LangFileManager.STRING_CHART_EXTENSION)) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return /*MFPAdapter.STRING_SCRIPT_EXTENSION + ";" + */ LangFileManager.STRING_CHART_EXTENSION;
            }

        };
        fc.setFileFilter(ff);
        fc.setMultiSelectionEnabled(false);
        // Show open dialog; this method does not return until the dialog is closed
        fc.showOpenDialog(null);
        File fSelected = fc.getSelectedFile();
        if (fSelected != null) {
            String strFilePath = fSelected.getAbsolutePath();
            if (strFilePath.length() > LangFileManager.STRING_CHART_EXTENSION.length()
                    && strFilePath.substring(strFilePath.length() - LangFileManager.STRING_CHART_EXTENSION.length())
                            .equalsIgnoreCase(LangFileManager.STRING_CHART_EXTENSION)) {
                int nRecommendedNumOfMarksPerAxis = 4;
                XYChartOperator xyChartOperator = new XYChartOperator();
                XYExprChartOperator xyExprChartOperator = new XYExprChartOperator();
                PolarChartOperator polarChartOperator = new PolarChartOperator();
                PolarExprChartOperator polarExprChartOperator = new PolarExprChartOperator();
                OGLChartOperator oglChartOperator = new OGLChartOperator();
                OGLExprChartOperator oglExprChartOperator = new OGLExprChartOperator();
                ChartCreationParam ccp = new ChartCreationParam();
                ccp.mnRecommendedNumOfMarksPerAxis = nRecommendedNumOfMarksPerAxis;
                String strChartType = "Invalid";
                strChartType = ChartOperator.getChartTypeFromFile(strFilePath);
                MFPChart mfpChart = null;
                if (strChartType.compareToIgnoreCase("multiXY") == 0) {
                    if (xyChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        mfpChart = (MFPChart) xyChartOperator.createChart(ccp);
                        FlatChartView.launchChart((XYChart) mfpChart);
                    }
                } else if (strChartType.compareToIgnoreCase("2DExpr") == 0) {
                    if (xyExprChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        mfpChart = (MFPChart) xyExprChartOperator.createChart(ccp);
                        FlatChartView.launchChart((XYExprChart) mfpChart);
                    }
                } else if (strChartType.compareToIgnoreCase("multiRangle") == 0) {
                    if (polarChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        mfpChart = (MFPChart) polarChartOperator.createChart(ccp);
                        FlatChartView.launchChart((PolarChart) mfpChart);
                    }
                } else if (strChartType.compareToIgnoreCase("PolarExpr") == 0) {
                    if (polarExprChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        mfpChart = (MFPChart) polarExprChartOperator.createChart(ccp);
                        FlatChartView.launchChart((PolarExprChart) mfpChart);
                    }
                } else if (strChartType.compareToIgnoreCase("multiXYZ") == 0) {
                    if (oglChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            mfpChart = (MFPChart) oglChartOperator.createChart(ccp);
                            OGLChartView.launchChart((OGLChart) mfpChart);
                        } catch (UnsatisfiedLinkError e) {
                            JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_ogl_chart_cannot_plot_lack_system_libs(),
                                    JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (strChartType.compareToIgnoreCase("3DExpr") == 0) {
                    if (oglExprChartOperator.loadFromFile(fSelected.getAbsolutePath()) == false) {
                        JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_graph_file_cannot_be_read(),
                                JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            mfpChart = (MFPChart) oglExprChartOperator.createChart(ccp);
                            OGLChartView.launchChart((OGLExprChart) mfpChart);
                        } catch (UnsatisfiedLinkError e) {
                            JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_ogl_chart_cannot_plot_lack_system_libs(),
                                    JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {   // invalid chart type.
                    JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_wrong_file_type(),
                            JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_wrong_file_type(),
                        JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_viewChartMenuItemActionPerformed

    private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
            JCmdLineApp.getApplication().showSettingsDlg();
    }//GEN-LAST:event_settingsMenuItemActionPerformed

    private void contentEnglishMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentEnglishMenuItemActionPerformed
            File fLanguageQuickStart = new File(JCmdLineApp.mMFP4JavaFileMan.getAssetFolderFullPath()
                    + LangFileManager.STRING_PATH_DIVISOR
                    + "en" + LangFileManager.STRING_PATH_DIVISOR
                    + "language_quick_start.html");
            if (fLanguageQuickStart.isFile()) {
                try {
                    Desktop.getDesktop().open(fLanguageQuickStart);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_open_file(),
                            JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_find_file(),
                        JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_contentEnglishMenuItemActionPerformed

    private void contentSChineseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentSChineseMenuItemActionPerformed
            File fLanguageQuickStart = new File(JCmdLineApp.mMFP4JavaFileMan.getAssetFolderFullPath()
                    + LangFileManager.STRING_PATH_DIVISOR
                    + "zh-CN" + LangFileManager.STRING_PATH_DIVISOR
                    + "language_quick_start.html");
            if (fLanguageQuickStart.isFile()) {
                try {
                    Desktop.getDesktop().open(fLanguageQuickStart);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_open_file(),
                            JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_find_file(),
                        JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_contentSChineseMenuItemActionPerformed

    private void contentTChineseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentTChineseMenuItemActionPerformed
            File fLanguageQuickStart = new File(JCmdLineApp.mMFP4JavaFileMan.getAssetFolderFullPath()
                    + LangFileManager.STRING_PATH_DIVISOR
                    + "zh-TW" + LangFileManager.STRING_PATH_DIVISOR
                    + "language_quick_start.html");
            if (fLanguageQuickStart.isFile()) {
                try {
                    Desktop.getDesktop().open(fLanguageQuickStart);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_open_file(),
                            JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_cannot_find_file(),
                        JCmdLineApp.getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_contentTChineseMenuItemActionPerformed

    private void getConstantMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getConstantMenuItemActionPerformed
        String strCmd = "help get_constant";
        if (JCmdLineApp.getApplication().isIdle()) {
            pasteNewCmd(strCmd + "\n");
            String strText = txtAreaOutput.getText();
            String strCommands = strText.substring(mnEditableStart);
            mnEditableStart = txtAreaOutput.getText().length(); // editable start is calculated before commands processed.
            JCmdLineApp.getApplication().processInput(strCommands);
        } else {
            JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_wait_for_calculation()
                    + JCmdLineApp.getStringsClass().get_help_get_constant_info(),
                    JCmdLineApp.getStringsClass().get_menu_get_constant(), JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_getConstantMenuItemActionPerformed

    private void cvtUnitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cvtUnitMenuItemActionPerformed
        String strCmd = "help convert_unit";
        if (JCmdLineApp.getApplication().isIdle()) {
            pasteNewCmd(strCmd + "\n");
            String strText = txtAreaOutput.getText();
            String strCommands = strText.substring(mnEditableStart);
            mnEditableStart = txtAreaOutput.getText().length(); // editable start is calculated before commands processed.
            JCmdLineApp.getApplication().processInput(strCommands);
        } else {
            JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_wait_for_calculation()
                    + JCmdLineApp.getStringsClass().get_help_convert_unit_info(),
                    JCmdLineApp.getStringsClass().get_menu_convert_unit(), JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_cvtUnitMenuItemActionPerformed

    private void rootsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootsMenuItemActionPerformed
            String strCmd = "help roots";
            if (JCmdLineApp.getApplication().isIdle()) {
                pasteNewCmd(strCmd + "\n");
                String strText = txtAreaOutput.getText();
                String strCommands = strText.substring(mnEditableStart);
                mnEditableStart = txtAreaOutput.getText().length(); // editable start is calculated before commands processed.
                JCmdLineApp.getApplication().processInput(strCommands);
            } else {
                JOptionPane.showMessageDialog(null, JCmdLineApp.getStringsClass().get_wait_for_calculation()
                        + JCmdLineApp.getStringsClass().get_help_calc_polynomial_roots_info(),
                        JCmdLineApp.getStringsClass().get_menu_calc_polynomial_roots(), JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_rootsMenuItemActionPerformed

    private void wrapLineMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wrapLineMenuItemActionPerformed
        txtAreaOutput.setLineWrap(wrapLineMenuItem.isSelected());
    }//GEN-LAST:event_wrapLineMenuItemActionPerformed

    private void interruptCmdMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interruptCmdMenuItemActionPerformed
            JCmdLineApp.getApplication().interruptCmd();
    }//GEN-LAST:event_interruptCmdMenuItemActionPerformed

    private void plotMultiXYChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotMultiXYChartMenuItemActionPerformed
        JCmdLineApp.getApplication().showChartDef("multixy");
    }//GEN-LAST:event_plotMultiXYChartMenuItemActionPerformed

    private void plotMultiXYZChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotMultiXYZChartMenuItemActionPerformed
        JCmdLineApp.getApplication().showChartDef("multixyz");
    }//GEN-LAST:event_plotMultiXYZChartMenuItemActionPerformed

    private void plotPolarChartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotPolarChartMenuItemActionPerformed
            JCmdLineApp.getApplication().showChartDef("multiRangle");
    }//GEN-LAST:event_plotPolarChartMenuItemActionPerformed

    private void createApkMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createApkMenuItemActionPerformed
        JCmdLineApp.getApplication().showApkGenInput();
    }//GEN-LAST:event_createApkMenuItemActionPerformed

    private void derivativeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derivativeMenuItemActionPerformed
        JCmdLineApp.getApplication().showDerivativeInput();
    }//GEN-LAST:event_derivativeMenuItemActionPerformed

    private void reloadLibMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadLibMenuItemActionPerformed
        // instead of calling
        // MFPAdapter.reloadUsrLib(JCmdLineApp.mMFP4JavaFileMan);    // reload user lib.
        // we call
        // JCmdLineApp.mMFP4JavaFileMan.reloadAllUserLibs(null);
        // because it cannot handle cached function call
        // in AEFunction, also it cannot handle parent class change (while child
        // class doesn't change).
        JCmdLineApp.mMFP4JavaFileMan.reloadAllUserLibs(null);
        
        // hide this function because we want to analyse statements on the fly
        //MFPAdapter.analyseStatements(); // reanalyse the statements
    }//GEN-LAST:event_reloadLibMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu calculusMenu;
    private javax.swing.JMenuItem clearMenuItem;
    private javax.swing.JMenuItem contentEnglishMenuItem;
    private javax.swing.JMenu contentMenu;
    private javax.swing.JMenuItem contentSChineseMenuItem;
    private javax.swing.JMenuItem contentTChineseMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem createApkMenuItem;
    private javax.swing.JMenuItem cvtUnitMenuItem;
    private javax.swing.JMenuItem derivativeMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem getConstantMenuItem;
    private javax.swing.JMenu howtoMenu;
    private javax.swing.JMenuItem integrateMenuItem;
    private javax.swing.JMenuItem interruptCmdMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem plotMultiXYChartMenuItem;
    private javax.swing.JMenuItem plotMultiXYZChartMenuItem;
    private javax.swing.JMenuItem plotPolarChartMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem reloadLibMenuItem;
    private javax.swing.JMenuItem rootsMenuItem;
    private javax.swing.JMenuItem selectAllMenuItem;
    private javax.swing.JMenuItem settingsMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JTextArea txtAreaOutput;
    private javax.swing.JMenuItem viewChartMenuItem;
    private javax.swing.JCheckBoxMenuItem wrapLineMenuItem;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    protected int busyIconIndex = 0;

    private JDialog aboutBox;

    protected int mnCmdLineBufSize = 8388608;    // characters. tested 8848.
    protected int mnEditableStart = 0;
    protected static String msstrPrompt = "$>";

    public void outputString2TxtArea(String str) {
        // always output to the tail.
        if (str == null) {
            try {
                LinkedList<String> listNewOutputStrings = new LinkedList<String>();
                JCmdLineApp.getApplication().writeLock.lockInterruptibly();
                if (JCmdLineApp.getApplication().mlistOutputStrings.size() > 0) {
                    listNewOutputStrings.addAll(JCmdLineApp.getApplication().mlistOutputStrings);
                    JCmdLineApp.getApplication().mlistOutputStrings.clear();
                }
                JCmdLineApp.getApplication().writeLock.unlock();
                // do heavy jobs out of locking scope.
                String strNewOutput = "";
                boolean bUpdated = false;
                while (listNewOutputStrings.size() > 0) {
                    strNewOutput += listNewOutputStrings.poll();
                    bUpdated = true;
                }
                if (bUpdated) { // do not use strNewOutput == "" to identify updated or not coz output could be "".
                    txtAreaOutput.setText(txtAreaOutput.getText() + strNewOutput);
                    mnEditableStart = txtAreaOutput.getText().length();
                }
            } catch (InterruptedException e) {
                //do nothing coz will exit.    
            }
        } else {
            txtAreaOutput.setText(txtAreaOutput.getText() + str);
            mnEditableStart = txtAreaOutput.getText().length();
        }
    }

    public void outputPrompt2TxtArea() {
        outputString2TxtArea(msstrPrompt);
    }

    public void pasteNewCmd(String strCmd) {
        if (JCmdLineApp.getApplication().isIdle() && strCmd != null) {
            txtAreaOutput.setText(txtAreaOutput.getText().substring(0, mnEditableStart) + strCmd);
        }
    }

    public void onIdleStateChange(boolean isIdle) {
        interruptCmdMenuItem.setEnabled(!isIdle);
        clearMenuItem.setEnabled(isIdle);
    }

    public void onTextChange() {
        synchronized (JCmdLineApp.getApplication().mobjInputSync) {
            JCmdLineApp.getApplication().mobjInputSync.notifyAll();
        }
    }

    public String getInput() {
        String strAllTxt = txtAreaOutput.getText();
        int nEditableTextStart = mnEditableStart;
        String strInput = "";
        if (nEditableTextStart <= strAllTxt.length()) {
            strInput = strAllTxt.substring(nEditableTextStart);
        }
        return strInput;
    }

    public void moveEditableStart2End() {
        mnEditableStart = txtAreaOutput.getText().length();
    }
}
