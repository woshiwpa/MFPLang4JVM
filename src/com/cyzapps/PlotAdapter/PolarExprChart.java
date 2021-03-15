// MFP project, PolarExprChart.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.PlotAdapter;

import com.cyzapps.Jfcalc.PlotLib;
import com.cyzapps.Jfcalc.PlotLib.TwoDExprCurve;
import com.cyzapps.Jfcalc.TwoDExprDataCache;
import com.cyzapps.VisualMFP.Position3D;
import com.cyzapps.adapter.MFPAdapter;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import jcmdline.JPolarExprChartAdj;
import jcmdline.JXYExprChartAdj.AdjXYExprChartParams;

public class PolarExprChart extends PolarChart {

    public TwoDExprCurve[] m2DExprCurves = new TwoDExprCurve[0];

    protected double mdYFrom = 0, mdXTo = 0, mdYTo = 0; // mdXFrom is always 0.
    public double mdSavedYFrom = 0, mdSavedYTo = 0;

    public boolean mbCfgChangeApplied = false;
    protected boolean mbIsBusy = false;
    protected TwoDExprDataCache[] m2DExprDataCaches = new TwoDExprDataCache[0];

    public JPolarExprChartAdj mpolarExprChartAdj = null;

    public PolarExprChart() {
        super();
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        mdSavedYFrom = mcaYAxis.mdValueFrom;
        mdSavedYTo = mcaYAxis.mdValueTo;
    }

    @Override
    public void restoreSettings() {
        super.restoreSettings();
        mcaYAxis.mdValueFrom = mdSavedYFrom;
        mcaYAxis.mdValueTo = mdSavedYTo;
    }

    public AdjXYExprChartParams getChartDataParams() {
        AdjXYExprChartParams adjParam = new AdjXYExprChartParams();
        adjParam.mdXFrom = mcaXAxis.mdValueFrom;    // do not need PlotLib.TWOD_EXPR_CHART_AXIS_FILL_RATIO;
        adjParam.mdXTo = (mdCoordWidth - mp3CoordOriginInTO.getX()) / getScalingRatio();    // use the horizontal len. do not need PlotLib.TWOD_EXPR_CHART_AXIS_FILL_RATIO;
        adjParam.mdYFrom = mcaYAxis.mdValueFrom * 180 / Math.PI;    // do not need PlotLib.TWOD_EXPR_CHART_AXIS_FILL_RATIO here
        adjParam.mdYTo = mcaYAxis.mdValueTo * 180 / Math.PI;    // do not need PlotLib.TWOD_EXPR_CHART_AXIS_FILL_RATIO here
        for (int idx = 0; idx < m2DExprCurves.length; idx++) {
            if (idx == 0) {
                adjParam.mnNumOfSteps = m2DExprCurves[idx].mnNumOfSteps;
                adjParam.mbAutoStep = m2DExprCurves[idx].mbAutoStep;
            } else {
                if (adjParam.mnNumOfSteps > m2DExprCurves[idx].mnNumOfSteps) {
                    adjParam.mnNumOfSteps = m2DExprCurves[idx].mnNumOfSteps;
                    if (adjParam.mbAutoStep == false) {
                        adjParam.mbAutoStep = m2DExprCurves[idx].mbAutoStep;
                    }
                }
            }
        }
        if (adjParam.mnNumOfSteps <= 0) {
            adjParam.mnNumOfSteps = OGLExprChartOperator.DEFAULT_NUM_OF_STEPS;
        }
        return adjParam;
    }

    public void updateUponDataRange(double dXMin, double dXMax, double dYMin, double dYMax) {
        // here, assume dYMax - dYMin always no less than 2*PI.
        // here, also assume that dXMin == 0 and dXMax are positive.
        // also , here need not to worry about canUpdate or not coz coord origin is in the middle.
        mp3CoordOriginInTO = new Position3D(mdCoordWidth / 2.0, mdCoordHeight / 2.0);
        mcaXAxis.mdValueFrom = dXMin;    // make sure X axis's value from is always 0, i.e. dXMin must be 0.
        mcaXAxis.mdValueTo = mdXAxisLenInFROM
                = dXMax * Math.sqrt(mdCoordWidth * mdCoordWidth + mdCoordHeight * mdCoordHeight) / mdCoordWidth;    // make sure it is positive, i.e. dXMax > 0
        mdXAxisLenInTO = Math.sqrt((mdCoordWidth / 2.0) * (mdCoordWidth / 2.0) + (mdCoordHeight / 2.0) * (mdCoordHeight / 2.0));
        double dOverAllZoomRatio = getScalingRatio() / getSavedScalingRatio();
        mdXMark1 = mdSavedXMark1;
        mdXMark2 = (mdSavedXMark2 - mdSavedXMark1) / dOverAllZoomRatio + mdSavedXMark1;

        mcaYAxis.mdValueFrom = dYMin;
        mcaYAxis.mdValueTo = dYMax;

        update();
    }

    public void applyCfgChart(AdjXYExprChartParams adjParams) {
        // needs to update the chart anyway because even the xMin, xMax, yMin, yMax do not change, coordOrigin may change.
        // moreover, x axis length in adjParams is the horizontal line length from 0 to left edge of chart, it can be negative,
        // while dXMax is the maximum r length in the chart. As such adjParams.isNoAdj(...) always return false.
        // also notice that in adjParams y value is based on angle, while dYMin and dYMax is based on arc.
        // Thus, mbCfgChangeApplied is always true.
        if (!mbIsBusy) {
            mbIsBusy = true;
            getGraphContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            for (int idx = 0; idx < m2DExprCurves.length; idx++) {
                m2DExprCurves[idx].mnNumOfSteps = adjParams.mnNumOfSteps;
                m2DExprCurves[idx].mbAutoStep = adjParams.mbAutoStep;
            }
            updateUponDataRange(adjParams.mdXFrom, adjParams.mdXTo,
                    adjParams.mdYFrom * Math.PI / 180, adjParams.mdYTo * Math.PI / 180);
            mbCfgChangeApplied = true;
        }

    }

    @Override
    public void config() {
        if (mpolarExprChartAdj == null) {
            mpolarExprChartAdj = new JPolarExprChartAdj((Frame) getGraphContainer(), true, this);
        }
        mpolarExprChartAdj.resetParams(getChartDataParams());
        mpolarExprChartAdj.setVisible(true);
    }

    @Override
    public void zoom(double dXRatio, double dYRatio) {
        if (dXRatio > 0 && dYRatio > 0 && (dXRatio * dYRatio) != 1) {
            if (!mbIsBusy) {
                mbIsBusy = true;
                getGraphContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                super.zoom(dXRatio, dYRatio);
            }
        }
    }

    @Override
    public void zoomReset() {
        if (!mbIsBusy) {
            mbIsBusy = true;
            getGraphContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            super.zoomReset();
        }
    }

    @Override
    public void slide(double dOldX, double dOldY, double dNewX, double dNewY) {
        if (dOldX != dNewX && dOldY != dNewY) {
            if (!mbIsBusy) {
                mbIsBusy = true;
                getGraphContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                super.slide(dOldX, dOldY, dNewX, dNewY);
            }
        }
    }

    @Override
    public void initialize() {
        // resize must be down whether there is progressing dialog or not
        super.initialize();
        if (!mbIsBusy) {
            mbIsBusy = true;
            // getGraphContainer() is null.
            //getGraphContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }

    @Override
    public void draw(Graphics2D graphics, double x, double y, double width, double height) {
        int nFunctionVar = 0;   // nFunctionVar is 0 means x = f(theta), x is function variable.
        for (int idx = 0; idx < m2DExprCurves.length; idx++) {
            if (idx == 0) {
                nFunctionVar = m2DExprCurves[idx].mnFunctionVar;
            } else if (nFunctionVar != m2DExprCurves[idx].mnFunctionVar) {
                nFunctionVar = -1;
                break;
            }
        }
        if (((mdXTo != mcaXAxis.mdValueTo || mdYFrom != mcaYAxis.mdValueFrom || mdYTo != mcaYAxis.mdValueTo) //x from is always 0 in polar chart.
                && nFunctionVar == -1) // nFunctionVar == -1 means the curves do not have same nFunctionVar value, as such change x or y range matters.
                || (mdXTo != mcaXAxis.mdValueTo && nFunctionVar == 1) // input variable is r (i.e. x) and x range changes
                || ((mdYFrom != mcaYAxis.mdValueFrom || mdYTo != mcaYAxis.mdValueTo) && nFunctionVar == 0) // input variable is angle (i.e. y) and y range changes
                || mbCfgChangeApplied) {    // cfg change applied may means number of steps change.
            // update dataset.
            mDataSet = PlotLib.recalc2DExprDataSet(mcaXAxis.mdValueFrom, mcaXAxis.mdValueTo, mcaYAxis.mdValueFrom, mcaYAxis.mdValueTo, 1.0, m2DExprCurves, m2DExprDataCaches);
        }
        mdXTo = mcaXAxis.mdValueTo;
        mdYFrom = mcaYAxis.mdValueFrom;
        mdYTo = mcaYAxis.mdValueTo;
        mbCfgChangeApplied = false;

        // everything is done successfully.
        mbIsBusy = false;
        getGraphContainer().setCursor(Cursor.getDefaultCursor());
        super.draw(graphics, x, y, width, height);
    }

}
