// MFP project, FlatGDIView.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.GI2DAdapter;

import com.cyzapps.JGI2D.GIEvent;
import com.cyzapps.JGI2D.PaintingCallBacks;
import com.cyzapps.Jfcalc.DCHelper.DATATYPES;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassSingleNum;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.MFPNumeric;
import com.cyzapps.MultimediaAdapter.ImageDisplay;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jcmdline.JCmdLineApp;

public class FlatGDIView extends JFrame {

    /**
     * The view bounds.
     */
    protected Canvas mcanvas = null;

    final protected FlatGDI mflatGDI;
    
    public FlatGDIView(FlatGDI flatGDI) {
        super(flatGDI.mstrTitle);
        mflatGDI = flatGDI;
    }

    public Canvas getCanvas() {
        return mcanvas;
    }
    
    public Image getImageBuffer() {
        if (!(mcanvas instanceof FlatGDICanvas)) {
            // including mcanvas is null.
            return null;
        } else {
            return ((FlatGDICanvas)mcanvas).mimageBuffer;
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (mflatGDI != null) {
            mflatGDI.close();
        }
    }
    
    public static void launchFlatGDI(FlatGDI flatGDI) {

        final FlatGDIView flatGDIView = new FlatGDIView(flatGDI);
        flatGDI.setGDIView(flatGDIView);
        FlatGDIEventHandler flatGDIEventHandler = new FlatGDIEventHandler(flatGDI);
        flatGDIView.mcanvas = new FlatGDICanvas(flatGDI);
        flatGDIView.mcanvas.addMouseListener(flatGDIEventHandler);
        flatGDIView.mcanvas.addMouseMotionListener(flatGDIEventHandler);
        flatGDIView.mcanvas.addComponentListener(flatGDIEventHandler);
        flatGDIView.mcanvas.setBackground(new java.awt.Color(
                flatGDI.getBackgroundColor().mnR,
                flatGDI.getBackgroundColor().mnG,
                flatGDI.getBackgroundColor().mnB,
                flatGDI.getBackgroundColor().mnAlpha)
        );
        flatGDIView.add(flatGDIView.mcanvas);
        flatGDIView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        flatGDIView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                boolean bShouldClose = true;
                if (flatGDI.getDisplayConfirmClose()) {
                    String ObjButtons[] = { JCmdLineApp.getStringsClass().get_yes(), JCmdLineApp.getStringsClass().get_no()};
                    int PromptResult = JOptionPane.showOptionDialog(
                            null,
                            JCmdLineApp.getStringsClass().get_do_you_want_to_exit(),
                            flatGDI.getDisplayCaption(),
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,ObjButtons,ObjButtons[1]);
                    if(PromptResult==JOptionPane.YES_OPTION) {
                        bShouldClose = true;
                    } else {
                        bShouldClose = false;
                    }
                }
                if (bShouldClose) {
                    flatGDI.mbHasBeenShutdown = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //System.exit(0);
                            flatGDIView.dispose();
                        }
                    }).start();
                }
            }
        });
        flatGDIView.setResizable(flatGDI.mbResizable);
        // Center frame
        flatGDIView.setLocationRelativeTo(null);
        flatGDIView.getContentPane().setPreferredSize(
                new Dimension(flatGDI.mnBufferedWidth, flatGDI.mnBufferedHeight)
        );
        flatGDIView.pack();
        flatGDIView.setVisible(true);
    }

    static public class FlatGDICanvas extends Canvas {

        protected Dimension mdimSize = new Dimension();
        protected Image mimageBuffer;
        protected Graphics mgraphBuffer;
        final protected FlatGDI mflatGDI;

        public FlatGDICanvas(FlatGDI flatGDI) {
            mflatGDI = flatGDI;
        }

        protected void resetBuffer() {
            // always keep track of the image size
            mdimSize.width = getWidth();
            mdimSize.height = getHeight();

            //    clean up the previous image
            if (mgraphBuffer != null) {
                mgraphBuffer.dispose();
                mgraphBuffer = null;
            }
            if (mimageBuffer != null) {
                mimageBuffer.flush();
                mimageBuffer = null;
            }
            System.gc();    // collect gabarage

            //    create the new image with the size of the panel
            mimageBuffer = createImage(mdimSize.width, mdimSize.height);
            mgraphBuffer = mimageBuffer.getGraphics();
        }

        @Override
        public void paint(Graphics g) {
            synchronized(mflatGDI) {
                //    checks the buffersize with the current panelsize
                //    or initialises the image with the first paint
                if (mdimSize.width != getSize().width
                        || mdimSize.height != getSize().height
                        || mimageBuffer == null || mgraphBuffer == null
                        || mflatGDI.mbPCBQueueChangedAfterDraw.get()) {
                    resetBuffer();
                    if (mgraphBuffer != null) {
                        //this clears the offscreen image, not the onscreen one
                        mgraphBuffer.clearRect(0, 0, mdimSize.width, mdimSize.height);
                        //calls the paintbuffer method with the offscreen graphics as a param
                        paintBuffer(mgraphBuffer);
                    }
                }
            }

            if (mgraphBuffer != null) {
                //we finaly paint the offscreen image onto the onscreen image
                // it was getGraphics().drawImage, but I changed to g.drawImage.
                g.drawImage(mimageBuffer, 0, 0, this);
            }
        }

        public void paintBuffer(Graphics graphics) {
            
            if (graphics instanceof Graphics2D) {
                // ensure it is Graphics 2D.
                mflatGDI.setCurrentGraphics((Graphics2D)graphics); // now the draw events should paint on the offscreen image.
                mflatGDI.draw(0, 0, mdimSize.width, mdimSize.height);
            }
        }

        @Override
        public void update(Graphics g) {
            paint(g);
        }

    }

    static public class FlatGDIEventHandler implements MouseListener, MouseMotionListener, ComponentListener {

        private FlatGDI mflatGDI = null;
        // Assume a mouse can have at most 5 buttons.
        /**
         * The old x coordinates. Use float here coz they definitely are
         * Android's coordinate
         */
        private final float[] mfOldX = new float[] {
            -1, -1, -1, -1, -1, -1
        };
        /**
         * The old y coordinate.
         */
        private final float[] mfOldY = new float[] {
            -1, -1, -1, -1, -1, -1
        };
        /**
         * The x coordinate when mouse is pressed.
         */
        private final float[] mfMousePressedX = new float[] {
            -1, -1, -1, -1, -1, -1
        };
        /**
         * The y coordinate when mouse is pressed.
         */
        private final float[] mfMousePressedY = new float[] {
            -1, -1, -1, -1, -1, -1
        };

        FlatGDIEventHandler(FlatGDI flatGDI) {
            mflatGDI = flatGDI;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int btnId = e.getButton();
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            mfOldX[btnId] = (float) x;
            mfOldY[btnId] = (float) y;
            GIEvent gIEvent = new GIEvent();
            gIEvent.menumEventType = GIEvent.EVENTTYPE.POINTER_CLICKED;
            try {
                gIEvent.setInfo("button", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(btnId)));
                gIEvent.setInfo("x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(x, true)));
                gIEvent.setInfo("y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(y, true)));
                // e is required to run e.getComponent().repaint() in the event handler.
                gIEvent.setInfo("event", new DataClassExtObjRef(e));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here. If here, something must be very wrong.
                Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
            }
            mflatGDI.mqueueGIEvents.add(gIEvent);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int btnId = e.getButton();
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            mfMousePressedX[btnId] = mfOldX[btnId] = (float) x;
            mfMousePressedY[btnId] = mfOldY[btnId] = (float) y;
            GIEvent gIEvent = new GIEvent();
            gIEvent.menumEventType = GIEvent.EVENTTYPE.POINTER_DOWN;
            try {
                gIEvent.setInfo("button", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(btnId)));
                gIEvent.setInfo("x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(x, true)));
                gIEvent.setInfo("y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(y, true)));
                // e is required to run e.getComponent().repaint() in the event handler.
                gIEvent.setInfo("event", new DataClassExtObjRef(e));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here. If here, something must be very wrong.
                Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
            }
            mflatGDI.mqueueGIEvents.add(gIEvent);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int btnId = e.getButton();
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            mfOldX[btnId] = (float) x;
            mfOldY[btnId] = (float) y;
            GIEvent gIEvent = new GIEvent();
            gIEvent.menumEventType = GIEvent.EVENTTYPE.POINTER_UP;
            try {
                gIEvent.setInfo("button", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(btnId)));
                gIEvent.setInfo("x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(x, true)));
                gIEvent.setInfo("y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(y, true)));
                // e is required to run e.getComponent().repaint() in the event handler.
                gIEvent.setInfo("event", new DataClassExtObjRef(e));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here. If here, something must be very wrong.
                Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
            }
            mflatGDI.mqueueGIEvents.add(gIEvent);
            if (mfMousePressedX[btnId] >= 0 && mfMousePressedY[btnId] >= 0
                    && (mfMousePressedX[btnId] != x || mfMousePressedY[btnId] != y)) {
                // OK, this is a POINTER_SLIDED event.
                GIEvent gIEvent1 = new GIEvent();
                gIEvent1.menumEventType = GIEvent.EVENTTYPE.POINTER_SLIDED;
                try {
                    gIEvent1.setInfo("button", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(btnId)));
                    gIEvent1.setInfo("x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(x, true)));
                    gIEvent1.setInfo("y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(y, true)));
                    gIEvent1.setInfo("last_x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(mfMousePressedX[btnId], true)));
                    gIEvent1.setInfo("last_y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(mfMousePressedY[btnId], true)));
                    // e is required to run e.getComponent().repaint() in the event handler.
                    gIEvent1.setInfo("event", new DataClassExtObjRef(e));
                } catch (ErrProcessor.JFCALCExpErrException ex) {
                    // will not be here. If here, something must be very wrong.
                    Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
                }
                mflatGDI.mqueueGIEvents.add(gIEvent1);
                mfMousePressedX[btnId] = mfMousePressedY[btnId] = -1;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int btnId = e.getButton();
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();
            GIEvent gIEvent = new GIEvent();
            gIEvent.menumEventType = GIEvent.EVENTTYPE.POINTER_DRAGGED;
            try {
                gIEvent.setInfo("button", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(btnId)));
                gIEvent.setInfo("x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(x, true)));
                gIEvent.setInfo("y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(y, true)));
                gIEvent.setInfo("last_x", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(mfOldX[btnId], true)));
                gIEvent.setInfo("last_y", new DataClassSingleNum(DATATYPES.DATUM_MFPDEC, new MFPNumeric(mfOldY[btnId], true)));
                // e is required to run e.getComponent().repaint() in the event handler.
                gIEvent.setInfo("event", new DataClassExtObjRef(e));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here. If here, something must be very wrong.
                Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
            }
            mflatGDI.mqueueGIEvents.add(gIEvent);
            mfOldX[btnId] = (float) x;
            mfOldY[btnId] = (float) y;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentResized(ComponentEvent e) {
            int width = e.getComponent().getWidth();
            int height = e.getComponent().getHeight();
            GIEvent gIEvent = new GIEvent();
            gIEvent.menumEventType = GIEvent.EVENTTYPE.WINDOW_RESIZED;
            try {
                gIEvent.setInfo("width", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(width)));
                gIEvent.setInfo("height", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(height)));
                gIEvent.setInfo("last_width", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(mflatGDI.mnBufferedWidth)));
                gIEvent.setInfo("last_height", new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(mflatGDI.mnBufferedHeight)));
                // e is required to run e.getComponent().repaint() in the event handler.
                gIEvent.setInfo("event", new DataClassExtObjRef(e));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here. If here, something must be very wrong.
                Logger.getLogger(FlatGDIView.class.getName()).log(Level.SEVERE, null, ex);
            }
            mflatGDI.resize(e.getComponent().getWidth(), e.getComponent().getHeight(), mflatGDI.mnBufferedWidth, mflatGDI.mnBufferedHeight);
            mflatGDI.mqueueGIEvents.add(gIEvent);
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentShown(ComponentEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // Do nothing. Not supported yet.
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
