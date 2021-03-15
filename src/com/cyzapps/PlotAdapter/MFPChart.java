/*
 * MFP project, MFPChart.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.PlotAdapter;

import java.awt.Component;

/**
 *
 * @author tonyc
 */
public class MFPChart {

    protected Component mgraphContainer = null;

    public void setGraphContainer(Component c) {
        mgraphContainer = c;
    }

    public Component getGraphContainer() {
        return mgraphContainer;
    }

    public void saveSettings() {

    }

    public void restoreSettings() {

    }

}
