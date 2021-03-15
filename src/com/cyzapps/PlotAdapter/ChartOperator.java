// MFP project, ChartOperator.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.PlotAdapter;

import com.cyzapps.Jfcalc.FuncEvaluator.GraphPlotter;

public class ChartOperator extends com.cyzapps.adapter.ChartOperator implements ICreateChart {

    static public class ChartOperatorPlotter extends GraphPlotter {

        public ChartOperatorPlotter() {
        }

        @Override
        public boolean plotGraph(String strChart) {
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
            strChartType = ChartOperator.getChartTypeFromString(strChart);
            MFPChart mfpChart = null;
            boolean bOK = true;
            if (strChartType.compareToIgnoreCase("multiXY") == 0) {
                if (xyChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    mfpChart = (MFPChart) xyChartOperator.createChart(ccp);
                    FlatChartView.launchChart((XYChart) mfpChart);
                }
            } else if (strChartType.compareToIgnoreCase("2DExpr") == 0) {
                if (xyExprChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    mfpChart = (MFPChart) xyExprChartOperator.createChart(ccp);
                    FlatChartView.launchChart((XYExprChart) mfpChart);
                }
            } else if (strChartType.compareToIgnoreCase("multiRangle") == 0) {
                if (polarChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    mfpChart = (MFPChart) polarChartOperator.createChart(ccp);
                    FlatChartView.launchChart((PolarChart) mfpChart);
                }
            } else if (strChartType.compareToIgnoreCase("polarExpr") == 0) {
                if (polarExprChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    // load function libs should be when initialize the chart.
                    mfpChart = (MFPChart) polarExprChartOperator.createChart(ccp);
                    FlatChartView.launchChart((PolarExprChart) mfpChart);
                }
            } else if (strChartType.compareToIgnoreCase("multiXYZ") == 0) {
                if (oglChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    mfpChart = (MFPChart) oglChartOperator.createChart(ccp);
                    OGLChartView.launchChart((OGLChart) mfpChart);
                }
            } else if (strChartType.compareToIgnoreCase("3DExpr") == 0) {
                if (oglExprChartOperator.loadFromString(strChart) == false) {
                    bOK = false;
                } else {
                    mfpChart = (MFPChart) oglExprChartOperator.createChart(ccp);
                    OGLChartView.launchChart((OGLExprChart) mfpChart);
                }
            } else {
                bOK = false;
            }

            return bOK;
        }
    }

    @Override
    public MFPChart createChart(ChartCreationParam ccpParam1) {
        return new MFPChart();
    }
}
