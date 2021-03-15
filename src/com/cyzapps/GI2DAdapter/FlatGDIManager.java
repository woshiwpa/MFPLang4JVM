/*
 * MFP project, FlatGDIManager.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.GI2DAdapter;

import com.cyzapps.Jfcalc.FuncEvaluator.GraphicDisplayInterfaceManager;
import com.cyzapps.JGI2D.DisplayLib.IGraphicDisplay;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author tony
 */
public class FlatGDIManager extends GraphicDisplayInterfaceManager {

    // never remove flatGDI from this list.
    public static List<FlatGDI> mslistFlatGDI = Collections.synchronizedList(new ArrayList<FlatGDI>());
    
    @Override
    public IGraphicDisplay openScreenDisplay(String strTitle, com.cyzapps.VisualMFP.Color clr, boolean bConfirmClose,
            double[] size, boolean bResizable, int orientation) {
        FlatGDI flatGDI = new FlatGDI();
        flatGDI.mstrTitle = strTitle;
        flatGDI.mcolorBkGrnd = clr;
        flatGDI.mbConfirmClose = bConfirmClose;
        flatGDI.mbResizable = bResizable;
        // assume size has been verified to be a two element array.
        flatGDI.mnBufferedWidth = (int)size[0];
        flatGDI.mnBufferedHeight = (int)size[1];
        mslistFlatGDI.add(flatGDI);
        FlatGDIView.launchFlatGDI(flatGDI);
        if (!SwingUtilities.isEventDispatchThread()) {
            // we are not in the ui thread.
            int waitSec = 10;
            for (int cnt = 0; cnt < waitSec; cnt++) {
                if (!flatGDI.isDisplayOnLive()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        break;
                    }
                } else {
                    break;
                }
            }
            // If flatGDI is on live, it is good. However, if we have waited for a few seconds, but
            // flat GDI is still not on live. we do not return null. This is to cater some extreme case
            // where phone is so slow that it takes ages to start flatGDI.
            // return null;
            return flatGDI;
        } else { // we are in the ui thread. This mode should not be used except in some very special mode.
            return flatGDI;
        }
    }

    @Override
    public void shutdownScreenDisplay(IGraphicDisplay gdi, boolean bByForce) {
        FlatGDI[] allDisplays = mslistFlatGDI.toArray(new FlatGDI[0]);  // convert to an array, this is an atomic operation.
        for (int idx = 0; idx < allDisplays.length; idx ++) {
            if (allDisplays[idx] == gdi) {
                // send a close event.
                if (bByForce) {
                    ((FlatGDI)gdi).setDisplayConfirmClose(false);
                }
                if (((FlatGDI)gdi).getGDIView() != null) {
                    // flat GDI view has been initialized.
                    if (!((FlatGDI)gdi).getDisplayConfirmClose()) {
                        // the status should be shutdown if we do not need to confirm close.
                        ((FlatGDI)gdi).mbHasBeenShutdown = true;
                    }
                    ((FlatGDI)gdi).getGDIView().dispatchEvent(new WindowEvent(((FlatGDI)gdi).getGDIView(), WindowEvent.WINDOW_CLOSING));
                }
            }
        }
    }
    
}
