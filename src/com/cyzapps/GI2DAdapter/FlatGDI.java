// MFP project, FlatGDI.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.GI2DAdapter;


import com.cyzapps.JGI2D.GIEvent;
import com.cyzapps.JGI2D.Display2D;
import com.cyzapps.JGI2D.DisplayLib;
import com.cyzapps.JGI2D.DisplayLib.GraphicDisplayType;
import com.cyzapps.JGI2D.DrawLib.PaintingExtraInfo;
import com.cyzapps.JGI2D.PaintingCallBacks.PaintingCallBack;
import com.cyzapps.JGI2D.PaintingCallBacks.UpdatePaintingCallBack;
import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.MultimediaAdapter.ImageDisplay;
import com.cyzapps.MultimediaAdapter.ImageDisplay.SizeChoices;
import com.cyzapps.MultimediaAdapter.ImageMgrJava.ImageWrapper;
import com.cyzapps.VisualMFP.LineStyle;
import com.cyzapps.VisualMFP.PointStyle;
import com.cyzapps.VisualMFP.TextStyle;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * This class is not thread safe. Ideally, all get and set display attribute functions
 * should be called in UI thread. However, to make things easier, I only call set in UI
 * threads but get is called in local thread. This will avoid any exception during setting
 * but get may not return right value. But considering graphics2D is shared across threads
 * , the class is thread unsafe anyway. And because it is mainly to draw game display,
 * animation will run quickly, user may not detect it.
 * Use invokeAndWait instead of invokeLater because invokeLater may not properly set like
 * resizable especially many properties are set in a batch.
 * @author tony
 */
public class FlatGDI extends Display2D {

    public static final String LOG_TAG = "GI2DAdapter.FlatGDI";

    private static AtomicInteger sid = new AtomicInteger();
    
    private int mnId = -1;
    public int getId() {
        return mnId;
    }
    
    public boolean mbHasBeenShutdown = false;
    
    protected SizeChoices msizeChoices = new SizeChoices();
    
    protected ConcurrentLinkedQueue<GIEvent> mqueueGIEvents = new ConcurrentLinkedQueue<>();
    
    protected ConcurrentLinkedQueue<PaintingCallBack> mqueuePaintingCallBacks = new ConcurrentLinkedQueue<>();
    
    protected ImageWrapper mimageBkGrnd = null;    // background image.
    protected int mBkgrndImgMode = 0;   // 0 means actually, 1 means scaled, 2 means tiled
    
    protected int mnBufferedWidth = 0;
    protected int mnBufferedHeight = 0;
    protected String mstrTitle = "";
    protected boolean mbResizable = false;    

    @Override
    public DisplayLib.GraphicDisplayType getDisplayType() {
        if (isDisplayOnLive()) {
            return GraphicDisplayType.SCREEN_2D_DISPLAY;
        } else {
            return GraphicDisplayType.INVALID_DISPLAY;
        }
    }
    
    @Override
    public GIEvent pullGIEvent() {
        GIEvent event = mqueueGIEvents.poll();
        return event;
    }
    
    @Override
    public void addPaintingCallBack(PaintingCallBack paintingCallBack) {
        if (paintingCallBack instanceof UpdatePaintingCallBack) {
            // remove obsolete call backs first.
            int idx = mqueuePaintingCallBacks.size() - 1;
            for (PaintingCallBack callBack : mqueuePaintingCallBacks) {
                if (callBack.isCallBackOutDated(paintingCallBack)) {
                    mqueuePaintingCallBacks.remove(callBack);
                }
            }
            // seems it is much slower.
            //mqueuePaintingCallBacks.removeIf(elem -> elem.isCallBackOutDated(paintingCallBack));
        }
        mqueuePaintingCallBacks.add(paintingCallBack);
        mbPCBQueueChangedAfterDraw.set(true);  //need to reset buffer because painting call back queue is changed.
    }

    @Override
    public void clearPaintingCallBacks() {
        mqueuePaintingCallBacks.clear();
    }
    
    // background color
    public com.cyzapps.VisualMFP.Color mcolorBkGrnd = new com.cyzapps.VisualMFP.Color();
    
    @Override
    public void setBackgroundColor(com.cyzapps.VisualMFP.Color newColor) {
        if (getGDIView() != null && !mcolorBkGrnd.isEqual(newColor)) {
            mcolorBkGrnd = newColor;
            try {
                SwingUtilities.invokeAndWait(() -> {
                    getGDIView().getCanvas().setBackground(new java.awt.Color(
                            mcolorBkGrnd.mnR,
                            mcolorBkGrnd.mnG,
                            mcolorBkGrnd.mnB,
                            mcolorBkGrnd.mnAlpha)
                    );
                });
                // do not call repaint()?
            } catch (InterruptedException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public com.cyzapps.VisualMFP.Color getBackgroundColor() {
        return mcolorBkGrnd;
    }

    @Override
    synchronized public void setBackgroundImage(DataClassExtObjRef imgHandler, int mode) {
        if (getGDIView() != null) {
            try {
                if (imgHandler == null) {
                    mimageBkGrnd = null;
                    mBkgrndImgMode = mode;
                    // do not force to repaint.
                    mbPCBQueueChangedAfterDraw.set(true);
                    //repaint();
                } else if (imgHandler.getExternalObject() instanceof ImageWrapper) {
                    // only if it is a ImageWrapper.
                    if ((ImageWrapper)imgHandler.getExternalObject() != mimageBkGrnd
                            || mode != mBkgrndImgMode) {
                        mimageBkGrnd = (ImageWrapper)imgHandler.getExternalObject();
                        mBkgrndImgMode = mode;
                        // do not force to repaint.
                        mbPCBQueueChangedAfterDraw.set(true);
                        //repaint();
                    }
                }
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here.
                Logger.getLogger(ImageDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    synchronized public DataClass getBackgroundImage() {
        if (mimageBkGrnd == null) {
            return new DataClassNull();
        } else {
            try {
                return new DataClassExtObjRef(mimageBkGrnd);
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                return new DataClassNull(); // will not be here.
            }
        }
    }

    @Override
    public int getBackgroundImageMode() {
        return mBkgrndImgMode;
    }

    // default foreground color
    public com.cyzapps.VisualMFP.Color mcolorForeGrnd = new com.cyzapps.VisualMFP.Color(255, 255, 255);

    public boolean mbConfirmClose = false;
    
    @Override
    synchronized public void setSnapshotAsBackground(boolean bUpdateScreen, boolean bClearPaintingCallbacks) {
        if (mflatGDIView != null) {
            // sync mode
            DataClass datumImg = getSnapshotImage(bUpdateScreen, 1.0, 1.0);
            if (!datumImg.isNull()) {
                try {
                    mimageBkGrnd = (ImageWrapper)(DCHelper.lightCvtOrRetDCExtObjRef(datumImg).getExternalObject());
                } catch (ErrProcessor.JFCALCExpErrException ex) {
                    mimageBkGrnd = null;
                    Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);  // will not be here.
                }
            } else {
                mimageBkGrnd = null;
            }
            if (bClearPaintingCallbacks) {
                clearPaintingCallBacks();
            }
            mbPCBQueueChangedAfterDraw.set(true); // even if we do not clear PCB, still set it to true coz final painting result is different.
            // repaint(); // do not force to repaint.
        }
    }
    
    @Override
    public DataClass getSnapshotImage(boolean bUpdateScreen, double wRatio, double hRatio) {
        if (mflatGDIView != null) {
            try {
                int destW = (int) (mflatGDIView.getContentPane().getWidth() * wRatio);
                int destH = (int) (mflatGDIView.getContentPane().getHeight() * hRatio);
                if (destW < 1) {
                    destW = 1;
                }
                if (destH < 1) {
                    destH = 1;
                }
                BufferedImage image = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                // draw background color.
                g.setBackground(new java.awt.Color(
                                    mcolorBkGrnd.mnR,
                                    mcolorBkGrnd.mnG,
                                    mcolorBkGrnd.mnB,
                                    mcolorBkGrnd.mnAlpha));
                g.clearRect(0, 0, image.getWidth(), image.getHeight());
                if (bUpdateScreen && mbPCBQueueChangedAfterDraw.get()) {
                    // we need to update screen
                    update();
                    repaint();
                    int loopCnt = 0;
                    do {
                        try {
                            Thread.sleep(20);// wait for UI thread until it is updated.
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        loopCnt ++;
                    } while(mbPCBQueueChangedAfterDraw.get() && loopCnt < 8);
                }
                
                synchronized(this) {
                    Image imgView = mflatGDIView.getImageBuffer();
                    if (imgView != null) {
                        g.drawImage(imgView, 0, 0, destW, destH, 0, 0,
                                mflatGDIView.getContentPane().getWidth(),
                                mflatGDIView.getContentPane().getHeight(),
                                null);
                    }
                }
                g.dispose();
                
                return new DataClassExtObjRef(new ImageWrapper(image));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
                return new DataClassNull();
            }
        } else {
            return new DataClassNull();
        }
    }
    
    @Override
    public void setDisplayConfirmClose(boolean bConfirmClose) {
        mbConfirmClose = bConfirmClose;
    }
    
    @Override
    public boolean getDisplayConfirmClose() {
        return mbConfirmClose;
    }
    
    public AtomicBoolean mbPCBQueueChangedAfterDraw = new AtomicBoolean(false);  // after last drawing painting call back queue is changed.

    protected FlatGDIView mflatGDIView = null;
    public void setGDIView(FlatGDIView flatGDIView) {
        mflatGDIView = flatGDIView;
    }
    public FlatGDIView getGDIView() {
        return mflatGDIView;
    }
    
    protected Graphics2D mcurrentGraphics = null;
    /**
     * This function can only be called in FlatGDI's UI thread, i.e. in Flat GDI View's
     * Canvas class. Because in that class paint function which call setCurrentGraphics
     * has been synchronized by flatGDI, no need to synchronize it again.
     * @param graphics 
     */
    public void setCurrentGraphics(Graphics2D graphics) {
        mcurrentGraphics = graphics;
    }

    /**
     * this function prevent a situation where several statements
     * continously run after open_display, where display hasn't been
     * initialized.
     * @return true or false
     */
    @Override
    public boolean isDisplayOnLive() {
        return mflatGDIView != null    // mcurrentGraphics might be null even when display is on live
                && mbHasBeenShutdown == false   // hasn't been shutdown.
                && (mnBufferedWidth != 0 || mnBufferedHeight != 0); // a on-live display in JAVA should not be 0*0.
    }
    
    @Override
    public void setDisplayOrientation(int orientation) {
        // do nothing.
    }
    @Override
    public int getDisplayOrientation() {
        return -1000;  // always return -1000 if in a PC.
    }
    
    protected void deprecateFlatGDI() {
        mflatGDIView = null;
        mcurrentGraphics = null;
        clearPaintingCallBacks();
        FlatGDIManager.mslistFlatGDI.remove(this);  // remove from list to save memory
    }
    
    public FlatGDI() {
        mnId = sid.getAndIncrement() + 1;
    }

    /**
     * Calculates the best text to fit into the available space.
     * @param text
     * @param width
     * @param graphics
     * @return 
     */
    public static String getFitText(String text, double width, Graphics2D graphics) {
        String newText = text;
        int length = text.length();
        int diff = 0;
        FontMetrics metrics = graphics.getFontMetrics();
        while (metrics.stringWidth(newText) > width && diff < length) {
            diff++;
            newText = text.substring(0, length - diff) + "...";
        }
        if (diff == length) {
            newText = "...";
        }
        return newText;
    }

    /**
     * Calculates How many characters can be placed in the width
     * @param width
     * @param graphics
     * @return 
     */
    public static int getNumOfCharsInWidth(double width, Graphics2D graphics) {
        if (width <= 0) {
            return 0;    // width cannot be negative.
        }
        FontMetrics metrics = graphics.getFontMetrics();
        double dWWidth = metrics.stringWidth("W");
        return (int) (width / dWWidth);
    }

    @Override
    public void setDisplaySize(int width, int height) {
        if (getGDIView() != null) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    
                    @Override
                    public void run() {
                        getGDIView().getContentPane().setPreferredSize(new Dimension(width, height));
                        getGDIView().pack();
                    }
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int[] getDisplaySize() {
        if (getGDIView() != null) {
            return new int[] {
                mnBufferedWidth = getGDIView().getContentPane().getSize().width,
                mnBufferedHeight = getGDIView().getContentPane().getSize().height
            };
        } else {
            return new int[] {mnBufferedWidth, mnBufferedHeight};
        }
    }

    @Override
    public void setDisplayCaption(String strCaption) {
        mstrTitle = strCaption;
        if (getGDIView() != null) {
            try {
                SwingUtilities.invokeAndWait(()-> getGDIView().setTitle(mstrTitle));
            } catch (InterruptedException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getDisplayCaption() {
        if (getGDIView() == null) {
            return mstrTitle;  // havn't been initialized.
        } else {
            return (mstrTitle = getGDIView().getTitle());
        }
    }
    
    /**
     * calculate rectangular boundary of text. The text shouldn't be rotated. 
     * @param text : multi-line text
     * @param x : x of the starting point of text
     * @param y : y of the starting point of text
     * @param txtStyle : text font and size
     * @return boundary rectangle [x, y, w, h]
     */
    @Override
    public int[] calcTextBoundary(String text, int x, int y, TextStyle txtStyle) {
        // Although this is not a drawing function, still has to use mcurrentGraphics.
        // Otherwise, the result may not be accurate. However, a problem is
        // mcurrentGraphics may not be initialized (i.e. is null) when display starts,
        // so may use a newly created graphics here.
        if (mcurrentGraphics == null) {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            int[] rect = ImageDisplay.calcTextBoundary(g, text, x, y, txtStyle);
            g.dispose();
            return rect;
        } else {
            Graphics2D g = mcurrentGraphics;
            int[] rect = ImageDisplay.calcTextBoundary(g, text, x, y, txtStyle);
            return rect;
        }
    }
    
    /**
     * calculate origin of text. The text shouldn't be rotated. 
     * @param text : multi-line text
     * @param x : left of boundary rectangle
     * @param y : top of boundary rectangle
     * @param w : width of boundary rectangle
     * @param h : height of boundary rectangle
     * @param horAlign : horizontal alignment, -1 means left, 0 means center, 1 means right aligned.
     * @param verAlign : vertical alignment, -1 means left, 0 means center, 1 means right aligned.
     * @param txtStyle : text font and size
     * @return boundary rectangle [x, y, w, h]
     */
    @Override
    public int[] calcTextOrigin(String text, int x, int y, int w, int h, int horAlign, int verAlign, TextStyle txtStyle) {
        // Although this is not a drawing function, still has to use mcurrentGraphics.
        // Otherwise, the result may not be accurate. However, a problem is
        // mcurrentGraphics may not be initialized (i.e. is null) when display starts,
        // so may use a newly created graphics here.
        if (mcurrentGraphics == null) {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            int[] origin = ImageDisplay.calcTextOrigin(g, text, x, y, w, h, horAlign, verAlign, txtStyle);
            g.dispose();
            return origin;
        } else {
            Graphics2D g = mcurrentGraphics;
            int[] origin = ImageDisplay.calcTextOrigin(g, text, x, y, w, h, horAlign, verAlign, txtStyle);
            return origin;
        }
    }
        
    /**
     * Draw a multiple lines text. x is the starting point of first character
     * and y is the pivot point
     * @param text
     * @param x
     * @param y
     * @param txtStyle
     * @param dRotateRadian 
     * @param pei
     */
    @Override
    public void drawText(String text, double x, double y, TextStyle txtStyle, double dRotateRadian, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawText(mcurrentGraphics, text, x, y, txtStyle, dRotateRadian, pei);
    }

    @Override
    public void drawPoint(double x, double y, PointStyle pointStyle, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawPoint(mcurrentGraphics, msizeChoices, x, y, pointStyle, pei);
    }

    @Override
    public void drawLine(double x0, double y0, double x1, double y1, LineStyle lineStyle, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawLine(mcurrentGraphics, msizeChoices, x0, y0, x1, y1, lineStyle, pei);
    }

    /**
     * draw a polygon.
     * @param points : vetex of the points.
     * @param color : color to fill or frame.
     * @param drawMode : this is an integer parameter, if it is zero or negative, the polygon is filled,
     * otherwise, the polygon is framed and the drawMode value is the border's with ( the border is always
     * solid line).
     * @param pei
     */
    @Override
    public void drawPolygon(LinkedList<double[]> points,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawPolygon(mcurrentGraphics, points, color, drawMode, pei);
    }

    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     * @param drawMode : this is an integer parameter, if it is zero or negative, the rectangle is filled,
     * otherwise, the rectangle is framed and the drawMode value is the border's with ( the border is always
     * solid line).
     * @param pei
     */
    @Override
    public void drawRect(double x, double y, double width, double height,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawRect(mcurrentGraphics, x, y, width, height, color, drawMode, pei);
    }

    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void clearRect(double x, double y, double width, double height) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.clearRect(mcurrentGraphics, x, y, width, height);
    }

    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     * @param drawMode : this is an integer parameter, if it is zero or negative, the oval is filled,
     * otherwise, the oval is framed and the drawMode value is the border's with ( the border is always
     * solid line).
     * @param pei
     */
    @Override
    public void drawOval(double x, double y, double width, double height,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.drawOval(mcurrentGraphics, x, y, width, height, color, drawMode, pei);
    }

    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void clearOval(double x, double y, double width, double height) {
        if (mcurrentGraphics == null) {
            return; // if flat GDI hasn't been fully initialized, return.
        }
        ImageDisplay.clearOval(mcurrentGraphics, x, y, width, height);
    }
    
    /**
     * return false if loading image fails.
     * @param imgHandle
     * @param left
     * @param top
     * @param widthRatio
     * @param heightRatio
     * @param pei
     * @return 
     */
    @Override
    public boolean drawImage(DataClass imgHandle, double left, double top, double widthRatio, double heightRatio, PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return false; // if flat GDI hasn't been fully initialized, return.
        }
        return ImageDisplay.drawImage(mcurrentGraphics, imgHandle, left, top, widthRatio, heightRatio, pei);
    }
    
    /**
     * return false if loading image fails.
     * @param imgHandle
     * @param srcX1
     * @param srcY1
     * @param srcX2
     * @param srcY2
     * @param dstX1
     * @param dstY1
     * @param dstX2
     * @param dstY2
     * @param pei
     * @return 
     */
    @Override
    public boolean drawImage(DataClass imgHandle,
            double srcX1, double srcY1, double srcX2, double srcY2,
            double dstX1, double dstY1, double dstX2, double dstY2,
            PaintingExtraInfo pei) {
        if (mcurrentGraphics == null) {
            return false; // if flat GDI hasn't been fully initialized, return.
        }
        return ImageDisplay.drawImage(mcurrentGraphics, imgHandle, srcX1, srcY1, srcX2, srcY2,
                dstX1, dstY1, dstX2, dstY2, pei);
    }
    
    @Override
    public void repaint() {
        if (getGDIView() != null && mbPCBQueueChangedAfterDraw.get()) {
            getGDIView().getCanvas().validate();
            getGDIView().getCanvas().repaint();            
        }
    }
    
    @Override
    public void setDisplayResizable(boolean bResizable) {
        mbResizable = bResizable;
        if (getGDIView() != null) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    getGDIView().setResizable(mbResizable);
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    @Override
    public boolean getDisplayResizable() {
        if (getGDIView() != null) {
            return (mbResizable = getGDIView().isResizable());
        } else {
            return mbResizable;
        }
    }
    
    /**
     * This function can only be called in FlatGDI's UI thread (i.e. in Flat GDI View's
     * Canvas class). Because in Flat GDI View's canvas class, paint function has been
     * synchronized by flatGDI, no need to make it synchronized again.
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public void draw(double x, double y, double width, double height) {
        if (getGDIView() != null) {
            if (mimageBkGrnd != null) {
                // draw background image
                ImageDisplay.drawBackgroundImage(mcurrentGraphics,
                        getGDIView().getContentPane().getWidth(), getGDIView().getContentPane().getHeight(),
                        mimageBkGrnd, mBkgrndImgMode);
            }
            // no need to remove all obsolete painting call backs because these call backs
            // have been removed when updatepainting call back is added.
            double right = x + width;
            double bottom = y + height;
            boolean bWholeDisplayInRange = (x == 0) && (y == 0)
                    && (width == getGDIView().getContentPane().getWidth()) // no need to worry about getGDIView() is null.
                    && (height == getGDIView().getContentPane().getHeight());
            mqueuePaintingCallBacks.stream().filter((pcb) -> (this.equals(pcb.getDisplay2D())))
                    .filter((pcb) -> (bWholeDisplayInRange || pcb.isInPaintingRange(x, y, right, bottom)))
                    .forEachOrdered((pcb) -> {
                pcb.call(); // draw all the painting call backs.
            }); // the same Display2D
            mbPCBQueueChangedAfterDraw.set(false);  //no need to reset buffer until painting call back queue is changed.
        }
    }
    
    @Override
    public void update() {
        // TODO: this function updates some underlying logic (for example, if FLatGDI
        // is for a game, this function updates some game logic), it has nothing to do
        // with graphics (UI).
    }

    @Override
    public void initialize() {
        GIEvent gIEvent = new GIEvent();
        gIEvent.menumEventType = GIEvent.EVENTTYPE.GDI_INITIALIZE;
        mqueueGIEvents.add(gIEvent);
    }
    
    @Override
    public void close() {
        deprecateFlatGDI();
        
        GIEvent gIEvent = new GIEvent();
        gIEvent.menumEventType = GIEvent.EVENTTYPE.GDI_CLOSE;
        mqueueGIEvents.add(gIEvent);
    }

    public void resize(double dWidth, double dHeight, double dOldWidth, double dOldHeight) {
        mnBufferedWidth = (int)dWidth;
        mnBufferedHeight = (int)dHeight;
        if (dWidth != dOldWidth || dHeight != dOldHeight) {
            update();
        }
    }

    @Override
    public int addRtcVideoOutput(int left, int top, int width, int height, boolean enableSlide) {
        return -1;   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public boolean startLocalStream(int videoOutputId) {
        return false;   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public void stopLocalStream() {
        
    }

    @Override
    public boolean startVideoCapturer() {
        return false;
    }

    @Override
    public void stopVideoCapturer() {
    }

    @Override
    public boolean setVideoTrackEnable(int idx, boolean enable) {
        return false;
    }

    @Override
    public boolean getVideoTrackEnable(int idx) {
        return false;
    }

    @Override
    public boolean setAudioTrackEnable(int idx, boolean enable) {
        return false;
    }

    @Override
    public boolean getAudioTrackEnable(int idx) {
        return false;
    }

    @Override
    public int[] getRtcVideoOutputLeftTop(int id) {
        return new int[0];   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public int getRtcVideoOutputCount() {
        return 0;   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public boolean linkVideoStream(String peerId, int trackId, int videoOutputId) {
        return false;   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public boolean unlinkVideoStream(String peerId, int trackId) {
        return false;   // RTC Video is not supported by ImageDisplay
    }

    @Override
    public int unlinkVideoStream(int videoOutputId) {
        return 0;   // RTC Video is not supported by ImageDisplay
    }
}
