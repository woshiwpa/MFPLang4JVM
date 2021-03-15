/*
 * MFP project, JMultiRangleChartDef.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JMultiRangleChartDef extends JMultiXYChartDef {

    /**
     * Creates new form JMultiXYChartDef
     */
    public JMultiRangleChartDef(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle(JCmdLineApp.getStringsClass().get_Polar_chart_def_title());
        lblXTitle.setText(JCmdLineApp.getStringsClass().get_graph_Rtitle_prompt());
        lblYTitle.setVisible(false);
        txtFieldYTitle.setText("\u03B8");
        txtFieldYTitle.setVisible(false);
    }

    public static final String PLOT_FUNCTION_NAME = "plot_polar_curves";    // variable cannot be overridden, has to use function.

    @Override
    public String getPlotFunctionName() {
        return PLOT_FUNCTION_NAME;
    }

    @Override
    public JMultiXYCurveDef initNewCurveDef() {
        return new JMultiRangleCurveDef(this);
    }

}
