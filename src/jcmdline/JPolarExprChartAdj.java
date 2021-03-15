/*
 * MFP project, JPolarExprChartAdj.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcmdline;

import com.cyzapps.PlotAdapter.FlatChartView;
import com.cyzapps.PlotAdapter.PolarExprChart;

/**
 *
 * @author tonyc
 */
public class JPolarExprChartAdj extends JXYExprChartAdj {

    public PolarExprChart mpolarChart = new PolarExprChart();

    /**
     * Creates new form JXYExprChartAdj
     */
    public JPolarExprChartAdj(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle(JCmdLineApp.getStringsClass().get_PolarExpr_config());
        lblXFrom.setText(mpolarChart.mstrXAxisName + " " + lblXFrom.getText());
        lblYFrom.setText(mpolarChart.mstrYAxisName + " " + lblYFrom.getText());
        txtXFrom.setText("0");
        txtXFrom.setEditable(false);
        lblXInputNote.setText(JCmdLineApp.getStringsClass().get_Polar_chart_r_range_note());
        lblYInputNote.setText(JCmdLineApp.getStringsClass().get_Polar_chart_angle_range_note());
    }

    public JPolarExprChartAdj(java.awt.Frame parent, boolean modal, PolarExprChart polarChart) {
        super(parent, modal);
        if (polarChart != null) {
            mpolarChart = polarChart;
        }
        setTitle(JCmdLineApp.getStringsClass().get_PolarExpr_config() + " : " + mpolarChart.mstrChartTitle);
        lblXFrom.setText(mpolarChart.mstrXAxisName + " " + lblXFrom.getText());
        lblYFrom.setText(mpolarChart.mstrYAxisName + " " + lblYFrom.getText());
        txtXFrom.setText("0");
        txtXFrom.setEditable(false);
        lblXInputNote.setText(JCmdLineApp.getStringsClass().get_Polar_chart_r_range_note());
        lblYInputNote.setText(JCmdLineApp.getStringsClass().get_Polar_chart_angle_range_note() + " "
                + JCmdLineApp.getStringsClass().get_degree());
    }

    @Override
    public void applyParams() {
        mpolarChart.applyCfgChart(getAdjParams());
        ((FlatChartView) (mpolarChart.getGraphContainer())).getCanvas().repaint();
    }

    public static final double XFROM_INITIAL_VALUE = 0;    // variable cannot be overridden, has to use function.
    public static final double XTO_INITIAL_VALUE = 10.0;
    public static final double YFROM_INITIAL_VALUE = -180;
    public static final double YTO_INITIAL_VALUE = 180;

    public static final String XAXIS_DEFAULT_NAME = "r";
    public static final String YAXIS_DEFAULT_NAME = "\u03b8";    // theta

    @Override
    public double getXFromInitialValue() {
        return XFROM_INITIAL_VALUE;
    }

    @Override
    public double getXToInitialValue() {
        return XTO_INITIAL_VALUE;
    }

    @Override
    public double getYFromInitialValue() {
        return YFROM_INITIAL_VALUE;
    }

    @Override
    public double getYToInitialValue() {
        return YTO_INITIAL_VALUE;
    }

}
