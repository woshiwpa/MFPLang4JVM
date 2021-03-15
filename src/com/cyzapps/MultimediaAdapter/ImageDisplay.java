/*
 * MFP project, ImageDisplay.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.MultimediaAdapter;

import com.cyzapps.GI2DAdapter.FlatGDI;
import com.cyzapps.JGI2D.Display2D;
import com.cyzapps.JGI2D.DisplayLib;
import com.cyzapps.JGI2D.DisplayLib.GraphicDisplayType;
import com.cyzapps.JGI2D.DrawLib.PaintingExtraInfo;
import com.cyzapps.JGI2D.DrawLib.PorterDuffMode;
import com.cyzapps.JGI2D.GIEvent;
import com.cyzapps.JGI2D.PaintingCallBacks;
import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.MultimediaAdapter.ImageMgrJava.ImageWrapper;
import com.cyzapps.VisualMFP.Color;
import com.cyzapps.VisualMFP.LineStyle;
import com.cyzapps.VisualMFP.PointStyle;
import com.cyzapps.VisualMFP.TextStyle;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A feature of this class is, there is no real draw happens until we try to get image or copy of image.
 * Also image display is different from screen display, it is not supposed to be multithreading, so no sync
 * functions.
 * @author tony
 */
public class ImageDisplay extends Display2D {

    public static class SizeChoices {
        public double mdVeryTinySize = 2;
        public double mdTinySize = 4;
        public double mdVerySmallSize = 8;
        public double mdSmallSize = 16;
        public double mdMediumSize = 32;
        public double mdLargeSize = 64;
        public double mdVeryLargeSize = 128;
        public double mdHugeSize = 256;
        public double mdVeryHugeSize = 512;

        public void resetAllSizesBasedOnMedium(double dNewMediumSize) {
            mdMediumSize = dNewMediumSize;
            mdVeryTinySize = mdMediumSize / 16;
            mdTinySize = mdMediumSize / 8;
            mdVerySmallSize = mdMediumSize / 4;
            mdSmallSize = mdMediumSize / 2;
            mdLargeSize = mdMediumSize * 2;
            mdVeryLargeSize = mdMediumSize * 4;
            mdHugeSize = mdMediumSize * 8;
            mdVeryHugeSize = mdMediumSize * 16;
        }

        public void resetAllSizesBasedOnMedium() {
            resetAllSizesBasedOnMedium(mdMediumSize);
        }
    }
    
    public static final String LOG_TAG = "MultimediaAdapter.ImageDisplay";

    protected SizeChoices msizeChoices = new SizeChoices();

    protected ConcurrentLinkedQueue<PaintingCallBacks.PaintingCallBack> mqueuePaintingCallBacks = new ConcurrentLinkedQueue<>();
    
    protected Color mcolorBkGrnd = new Color(0, 0, 0, 0);
    protected int mBkgrndImgMode = 0;   // 0 means actually, 1 means scaled, 2 means tiled
    protected ImageWrapper mimageBkGrnd = null;    // background image which is the loaded image.
    protected int mnTargetWidth = 0;
    protected int mnTargetHeight = 0;
    protected String mstrFilePath = null;
    protected BufferedImage mcurrentImage = null;
    protected boolean mbImageUptoDate = false;
    
    protected Boolean mbDisplayOnLive = true;   // it is true until display shutdown.
    
    /** this function is unsafe. Comment it. Use cloneImage instead
    public BufferedImage getImage(boolean bUpdateScreen) {
        if (mbDisplayOnLive == false) {
            return null;
        } else {
            if (bUpdateScreen) {
                startPainting();
            }
            if (mcurrentImage == null && mnTargetWidth > 0 && mnTargetHeight > 0) {
                // if no need to update screen, at least we paint the background.
                BufferedImage img2Clone = new BufferedImage(mnTargetWidth, mnTargetHeight, BufferedImage.TYPE_INT_ARGB);
                drawBackground(img2Clone, mcolorBkGrnd, mimageBkGrnd, mBkgrndImgMode);
                return img2Clone;
            } else {
                return mcurrentImage;
            }
        }
    }*/
    
    public BufferedImage cloneImage(boolean bUpdateScreen, double wRatio, double hRatio) {
        if (mbDisplayOnLive == false) {
            return null;
        } else {
            if (bUpdateScreen) {
                startPainting();
            }
            if (mcurrentImage == null && mnTargetWidth > 0 && mnTargetHeight > 0) {
                // if no need to update screen, at least we paint the background.
                BufferedImage imgBkGrnd = new BufferedImage(mnTargetWidth, mnTargetHeight, BufferedImage.TYPE_INT_ARGB);
                drawBackground(imgBkGrnd, mcolorBkGrnd, mimageBkGrnd, mBkgrndImgMode);
                int destW = (int) (mnTargetWidth * wRatio);
                int destH = (int) (mnTargetHeight * hRatio);
                if (destW < 1) {
                    destW = 1;
                }
                if (destH < 1) {
                    destH = 1;
                }
                BufferedImage imgClone = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = imgClone.createGraphics();
                g.drawImage(imgBkGrnd, 0, 0, destW, destH, null);
                g.dispose();
                return imgClone;
            } else if (mcurrentImage != null) {
                if (wRatio == 1 && hRatio == 1) {
                    ColorModel cm = mcurrentImage.getColorModel();
                    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
                    WritableRaster raster = mcurrentImage.copyData(null);
                    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
                } else {
                    int destW = (int) (mcurrentImage.getWidth() * wRatio);
                    int destH = (int) (mcurrentImage.getHeight() * hRatio);
                    if (destW < 1) {
                        destW = 1;
                    }
                    if (destH < 1) {
                        destH = 1;
                    }
                    BufferedImage imgClone = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = imgClone.createGraphics();
                    g.drawImage(mcurrentImage, 0, 0, destW, destH, null);
                    g.dispose();
                    return imgClone;
                }
            } else {
                return null;
            }
        }
    }
    
    @Override
    public GIEvent pullGIEvent() {
        return null;    // no GI event is supported.
    }

    @Override
    public void addPaintingCallBack(PaintingCallBacks.PaintingCallBack paintingCallBack) {
        if (mbDisplayOnLive == false) {
            return;
        }
        if (paintingCallBack instanceof PaintingCallBacks.UpdatePaintingCallBack) {
            // remove obsolete call backs first.
            int idx = mqueuePaintingCallBacks.size() - 1;
            for (PaintingCallBacks.PaintingCallBack callBack : mqueuePaintingCallBacks) {
                if (callBack.isCallBackOutDated(paintingCallBack)) {
                    mqueuePaintingCallBacks.remove(callBack);
                }
            }
            // seems it is much slower.
            //mqueuePaintingCallBacks.removeIf(elem -> elem.isCallBackOutDated(paintingCallBack));
        }
        mqueuePaintingCallBacks.add(paintingCallBack);
        mbImageUptoDate = false;
    }

    @Override
    public void clearPaintingCallBacks() {
        // mqueuePaintingCallBacks is private to each image display, so we can do this quickly.
        mqueuePaintingCallBacks.clear();
        mbImageUptoDate = false;
    }
    
    @Override
    public boolean isDisplayOnLive() {
        return mbDisplayOnLive;
    }

    @Override
    public void setDisplayOrientation(int orientation) {
        // not supported.
    }

    @Override
    public int getDisplayOrientation() {
        return -1000;  // always return -1000 for an image.
    }

    @Override
    public void setDisplaySize(int width, int height) {
        if (mbDisplayOnLive) {
            mnTargetWidth = width;
            mnTargetHeight = height;
        }
    }

    @Override
    public int[] getDisplaySize() {
        if (mbDisplayOnLive) {
            return new int[] {mnTargetWidth, mnTargetHeight};
        } else {
            return new int[] {0,0};
        }
    }

    @Override
    public void setDisplayResizable(boolean resizable) {
        // do nothing.
    }

    @Override
    public boolean getDisplayResizable() {
        return false;
    }

    @Override
    public void setDisplayCaption(String strCaption) {
        // do nothing.
    }

    @Override
    public String getDisplayCaption() {
        return "";
    }

    @Override
    public void setBackgroundColor(Color clr) {
        if (mbDisplayOnLive) {
            mcolorBkGrnd = clr;
            mbImageUptoDate = false;
        }
    }

    @Override
    public Color getBackgroundColor() {
        if (mbDisplayOnLive) {
            return mcolorBkGrnd;
        } else {
            return new Color(); // invalid color.
        }
    }

    @Override
    public void setBackgroundImage(DataClassExtObjRef imgHandler, int mode) {
        if (mbDisplayOnLive) {
            try {
                if (imgHandler == null) {
                    mimageBkGrnd = null;
                    mBkgrndImgMode = mode;
                } else if (imgHandler.getExternalObject() instanceof ImageWrapper) {
                    // only if it is a buffered image.
                    mimageBkGrnd = (ImageWrapper)imgHandler.getExternalObject();
                    mBkgrndImgMode = mode;
                }
            } catch (JFCALCExpErrException ex) {
                // will not be here.
                Logger.getLogger(ImageDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            mbImageUptoDate = false;
        }
    }

    @Override
    public DataClass getBackgroundImage() {
        if (!mbDisplayOnLive || mimageBkGrnd == null) {
            return new DataClassNull();
        } else {
            try {
                return new DataClassExtObjRef(mimageBkGrnd);
            } catch (JFCALCExpErrException ex) {
                return new DataClassNull(); // will not be here.
            }
        }
    }

    @Override
    public int getBackgroundImageMode() {
        if (mbDisplayOnLive) {
            return mBkgrndImgMode;
        } else {
            return 0;
        }
    }
    
    @Override
    public void setSnapshotAsBackground(boolean bUpdateScreen, boolean bClearPaintingCallbacks) {
        // image display only has two modes, clearing or not clearing drawing events.
        // there is no sync mode or async mode.
        if (mbDisplayOnLive) {
            mimageBkGrnd = new ImageWrapper(cloneImage(bUpdateScreen, 1.0, 1.0));
            // no need to worry about background image mode because size of
            // mimageBkGrnd should exactly fit display.
            if (bClearPaintingCallbacks) {
                clearPaintingCallBacks();
            }
            mbImageUptoDate = false;
        }
    }
    
    @Override
    public DataClass getSnapshotImage(boolean bUpdateDisplay, double wRatio, double hRatio) {
        if (mbDisplayOnLive) {
            try {
                BufferedImage image = cloneImage(bUpdateDisplay, wRatio, hRatio);
                if (image == null) {
                    return new DataClassNull();
                } else {
                    return new DataClassExtObjRef(new ImageWrapper(image));
                }
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
        // do nothing.
    }

    @Override
    public boolean getDisplayConfirmClose() {
        return false;
    }

    public static AlphaComposite convert2AlphaComposite(PorterDuffMode pdm) {
        switch(pdm) {
        case CLEAR:
            return AlphaComposite.getInstance(AlphaComposite.CLEAR);
        case SRC:
            return AlphaComposite.getInstance(AlphaComposite.SRC);
        case DST:
            return AlphaComposite.getInstance(AlphaComposite.DST);
        case SRC_OVER:
            return AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        case DST_OVER:
            return AlphaComposite.getInstance(AlphaComposite.DST_OVER);
        case SRC_IN:
            return AlphaComposite.getInstance(AlphaComposite.SRC_IN);
        case DST_IN:
            return AlphaComposite.getInstance(AlphaComposite.DST_IN);
        case SRC_OUT:
            return AlphaComposite.getInstance(AlphaComposite.SRC_OUT);
        case DST_OUT:
            return AlphaComposite.getInstance(AlphaComposite.DST_OUT);
        case SRC_ATOP:
            return AlphaComposite.getInstance(AlphaComposite.SRC_ATOP);
        case DST_ATOP:
            return AlphaComposite.getInstance(AlphaComposite.DST_ATOP);
        case XOR:
            return AlphaComposite.getInstance(AlphaComposite.XOR);
        default:
            return AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
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
        // this is not a drawing function, so not use mcurrentGraphics.
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int[] rect = calcTextBoundary(g, text, x, y, txtStyle);
        g.dispose();
        return rect;
    }
    public static int[] calcTextBoundary(Graphics2D graphics, String text, int x, int y, TextStyle txtStyle) {
        double dTextSize = txtStyle.mdSize;
        Font fontOriginal = graphics.getFont();
        Boolean bBoldStyle = (txtStyle.mnStyle & 1) == 1;
        Boolean bItalicStyle = (txtStyle.mnStyle & 2) == 2;
        int nStyle;
        if (bBoldStyle && bItalicStyle) {
            nStyle = Font.BOLD | Font.ITALIC;
        } else if (bBoldStyle) {
            nStyle = Font.BOLD;
        } else if (bItalicStyle) {
            nStyle = Font.ITALIC;
        } else {
            nStyle = Font.PLAIN;
        }
        
        Font fontNew = new Font(txtStyle.mstrFont, nStyle, (int) dTextSize);    // will not throw exception even if font name is invalid.
        graphics.setFont(fontNew);
        FontRenderContext frc = graphics.getFontRenderContext();
        // get metrics from the graphics
        FontMetrics metrics = graphics.getFontMetrics();
        // get the height of a line of text in this font and render context
        int nFontHeight = metrics.getHeight();
        int nLineGap = (int)Math.max(nFontHeight / 3.0, 4);
        // get the advance of my text in this font and render context
        //int adv = metrics.stringWidth(text);
        String[] lines = text.split("\n");
        int textWidth = 0;
        int lastLineHeight = nFontHeight;
        for (int i = 0; i < lines.length; ++i) {
            textWidth = Math.max(textWidth, (int)fontNew.getStringBounds(lines[i], frc).getWidth());
            lastLineHeight = (int)fontNew.getStringBounds(lines[i], frc).getHeight();
        }
        graphics.setFont(fontOriginal);
        return new int[] {x, y, textWidth, (nFontHeight + nLineGap) * (lines.length - 1) + lastLineHeight};
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
        // this is not a drawing function, so not use mcurrentGraphics.
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int[] origin = calcTextOrigin(g, text, x, y, w, h, horAlign, verAlign, txtStyle);
        g.dispose();
        return origin;
    }
    public static int[] calcTextOrigin(Graphics2D graphics, String text, int x, int y, int w, int h, int horAlign, int verAlign, TextStyle txtStyle) {
        double dTextSize = txtStyle.mdSize;
        Font fontOriginal = graphics.getFont();
        Boolean bBoldStyle = (txtStyle.mnStyle & 1) == 1;
        Boolean bItalicStyle = (txtStyle.mnStyle & 2) == 2;
        int nStyle;
        if (bBoldStyle && bItalicStyle) {
            nStyle = Font.BOLD | Font.ITALIC;
        } else if (bBoldStyle) {
            nStyle = Font.BOLD;
        } else if (bItalicStyle) {
            nStyle = Font.ITALIC;
        } else {
            nStyle = Font.PLAIN;
        }
        
        Font fontNew = new Font(txtStyle.mstrFont, nStyle, (int) dTextSize);    // will not throw exception even if font name is invalid.
        graphics.setFont(fontNew);
        // get metrics from the graphics
        FontMetrics metrics = graphics.getFontMetrics();
        // get the height of a line of text in this font and render context
        int nFontHeight = metrics.getHeight();
        int nLineGap = (int)Math.max(nFontHeight / 3.0, 4);
        // get the advance of my text in this font and render context
        //int adv = metrics.stringWidth(text);
        String[] lines = text.split("\n");
        int textWidth = 0;
        for (int i = 0; i < lines.length; ++i) {
            textWidth = Math.max(textWidth, metrics.stringWidth(lines[i]));
        }
        graphics.setFont(fontOriginal);
        int xOrigin = 0, yOrigin = 0;
        int left = xOrigin, top = yOrigin;
        int right = left + textWidth, bottom = top + (nFontHeight + nLineGap) * lines.length - nLineGap;
        double centerX = (left + right)/2.0, centerY = (top + bottom)/2.0;
        int xAligned = 0, yAligned = 0;
        if (horAlign < 0) {
            xAligned = x + (xOrigin - left); 
        } else if (horAlign > 0) {
            xAligned = (x + w) + (xOrigin - right);
        } else {
            xAligned = (int)((x + w/2.0) + (xOrigin - centerX));
        }
        if (verAlign < 0) {
            yAligned = y + (yOrigin - top); 
        } else if (verAlign > 0) {
            yAligned = (y + h) + (yOrigin - bottom);
        } else {
            yAligned = (int)((y + h/2.0) + (yOrigin - centerY));
        }
        return new int[] {xAligned, yAligned};
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawText(g, text, x, y, txtStyle, dRotateRadian, pei);
        g.dispose();
    }
    public static void drawText(Graphics2D graphics, String text, double x, double y, TextStyle txtStyle, double dRotateRadian, PaintingExtraInfo pei) {
        float fRotateRadian = (float) dRotateRadian, fX = (float) x, fY = (float) y;
        if (dRotateRadian != 0) {
            graphics.rotate(fRotateRadian, fX, fY);
        }
        Composite compositeOriginal = graphics.getComposite();
        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        com.cyzapps.VisualMFP.Color color = txtStyle.mclr;
        double dTextSize = txtStyle.mdSize;
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }    // otherwise, use paint's color.
        Font fontOriginal = graphics.getFont();
        Boolean bBoldStyle = (txtStyle.mnStyle & 1) == 1;
        Boolean bItalicStyle = (txtStyle.mnStyle & 2) == 2;
        int nStyle;
        if (bBoldStyle && bItalicStyle) {
            nStyle = Font.BOLD | Font.ITALIC;
        } else if (bBoldStyle) {
            nStyle = Font.BOLD;
        } else if (bItalicStyle) {
            nStyle = Font.ITALIC;
        } else {
            nStyle = Font.PLAIN;
        }
        
        Font fontNew = new Font(txtStyle.mstrFont, nStyle, (int) dTextSize);    // will not throw exception even if font name is invalid.
        /*if (txtStyle.mstrFont != null && txtStyle.mstrFont.trim().length() > 0 && dTextSize >= 1.0) {
            fontNew = new Font(txtStyle.mstrFont, graphics.getFont().getStyle(), (int) dTextSize);
        }*/
        graphics.setFont(fontNew);
        // get metrics from the graphics
        FontMetrics metrics = graphics.getFontMetrics();
        // get the height of a line of text in this font and render context
        int nFontHeight = metrics.getHeight();
        int nLineGap = (int)Math.max(nFontHeight / 3.0, 4);
        // get the advance of my text in this font and render context
        //int adv = metrics.stringWidth(text);
        String[] lines = text.split("\n");
        int yOff = 0;
        for (int i = 0; i < lines.length; ++i) {
            graphics.drawString(lines[i], fX, fY + metrics.getLeading() + metrics.getAscent() + yOff);
            yOff = (int) (yOff + nFontHeight + nLineGap); // nLineGap is space between lines
        }
        graphics.setFont(fontOriginal);
        graphics.setColor(colorPaintOriginalColor);
        graphics.setComposite(compositeOriginal);
        if (dRotateRadian != 0) {
            graphics.rotate(-fRotateRadian, fX, fY);
        }
    }

    @Override
    public void drawPoint(double x, double y, PointStyle pointStyle, PaintingExtraInfo pei) {
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawPoint(g, msizeChoices, x, y, pointStyle, pei);
        g.dispose();
    }
    
    public static void drawPoint(Graphics2D graphics, SizeChoices sizeChoices, double x, double y, PointStyle pointStyle, PaintingExtraInfo pei) {
        Composite compositeOriginal = graphics.getComposite();
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();

        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        if (pointStyle.mclr != null) {
            graphics.setColor(new java.awt.Color(pointStyle.mclr.getF1R(), pointStyle.mclr.getF1G(), pointStyle.mclr.getF1B(), pointStyle.mclr.getF1Alpha()));
        }
        graphics.setStroke(new BasicStroke(1.0f, strokeOriginal.getEndCap(), // new width is 1.0f
                strokeOriginal.getLineJoin(), 1.0f,
                new float[]{(float) sizeChoices.mdMediumSize, 0}, 0.0f));
        double dSize = pointStyle.mdSize;
        switch (pointStyle.menumPointShape) {
            case POINTSHAPE_CIRCLE: {
                float fX = (float) x;
                float fY = (float) y;
                float fRadius = (float) (dSize / 2);
                graphics.drawOval((int) (fX - fRadius / 2.0), (int) (fY - fRadius / 2.0), (int) fRadius, (int) fRadius);
                break;
            }
            case POINTSHAPE_DIAMOND: {
                float fX = (float) x;
                float fY = (float) y;
                float fX1 = (float) (x - dSize / 2), fX2 = (float) (x + dSize / 2);
                float fY1 = (float) (y - dSize / 2), fY2 = (float) (y + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY, (int) fX, (int) fY1);
                graphics.drawLine((int) fX1, (int) fY, (int) fX, (int) fY2);
                graphics.drawLine((int) fX2, (int) fY, (int) fX, (int) fY1);
                graphics.drawLine((int) fX2, (int) fY, (int) fX, (int) fY2);
                break;
            }
            case POINTSHAPE_CROSS: {
                float fX = (float) x;
                float fY = (float) y;
                float fX1 = (float) (x - dSize / 2), fX2 = (float) (x + dSize / 2);
                float fY1 = (float) (y - dSize / 2), fY2 = (float) (y + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY, (int) fX2, (int) fY);
                graphics.drawLine((int) fX, (int) fY1, (int) fX, (int) fY2);
                break;
            }
            case POINTSHAPE_X: {
                float fX1 = (float) (x - dSize / 2), fX2 = (float) (x + dSize / 2);
                float fY1 = (float) (y - dSize / 2), fY2 = (float) (y + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY1);
                break;
            }
            case POINTSHAPE_DOWNTRIANGLE: {
                float fX = (float) x;
                float fX1 = (float) (x - dSize * 0.433), fX2 = (float) (x + dSize * 0.433);
                float fY1 = (float) (y + dSize / 2), fY2 = (float) (y - dSize / 4);
                graphics.drawLine((int) fX, (int) fY1, (int) fX1, (int) fY2);
                graphics.drawLine((int) fX, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY2);
                break;
            }
            case POINTSHAPE_UPTRIANGLE: {
                float fX = (float) x;
                float fX1 = (float) (x - dSize * 0.433), fX2 = (float) (x + dSize * 0.433);
                float fY1 = (float) (y - dSize / 2), fY2 = (float) (y + dSize / 4);
                graphics.drawLine((int) fX, (int) fY1, (int) fX1, (int) fY2);
                graphics.drawLine((int) fX, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY2);
                break;
            }
            case POINTSHAPE_SQUARE: {
                float fX1 = (float) (x - dSize / 2);
                float fX2 = (float) (x + dSize / 2);
                float fY1 = (float) (y - dSize / 2);
                float fY2 = (float) (y + dSize / 2);
                graphics.drawRect((int) fX1, (int) fY1, (int) dSize, (int) dSize);
                break;
            }
            default:    // dot.
                graphics.drawLine((int)x, (int)y, (int)x, (int)y);
        }

        // restore paint attribute.
        graphics.setStroke(strokeOriginal);
        graphics.setColor(colorPaintOriginalColor);
        graphics.setComposite(compositeOriginal);
    }

    @Override
    public void drawLine(double x0, double y0, double x1, double y1, LineStyle lineStyle, PaintingExtraInfo pei) {
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawLine(g, msizeChoices, x0, y0, x1, y1, lineStyle, pei);
        g.dispose();
    }
    
    public static void drawLine(Graphics2D graphics, SizeChoices sizeChoices, double x0, double y0, double x1, double y1, LineStyle lineStyle, PaintingExtraInfo pei) {
        if (lineStyle.menumLinePattern == LineStyle.LINEPATTERN.LINEPATTERN_NON) {
            return;
        }
        Composite compositeOriginal = graphics.getComposite();
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();

        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        if (lineStyle.mclr != null) {
            graphics.setColor(new java.awt.Color(lineStyle.mclr.getF1R(), lineStyle.mclr.getF1G(), lineStyle.mclr.getF1B(), lineStyle.mclr.getF1Alpha()));
        }
        switch (lineStyle.menumLinePattern) {
            case LINEPATTERN_DASH: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) sizeChoices.mdVerySmallSize, (float) sizeChoices.mdTinySize}, 0.0f));
                break;
            }
            case LINEPATTERN_DOT: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) sizeChoices.mdVeryTinySize, (float) sizeChoices.mdTinySize}, 0.0f));
                break;
            }
            case LINEPATTERN_DASH_DOT: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) sizeChoices.mdVerySmallSize, (float) sizeChoices.mdTinySize,
                            (float) sizeChoices.mdVeryTinySize, (float) sizeChoices.mdTinySize}, 0.0f));
                break;
            }
            /* case LINEPATTERN_NON:    // need not to consider LINEPATTERN_NON
        {
            graphics.setStroke(new BasicStroke((float)lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                                               strokeOriginal.getLineJoin(), 1.0f,
                                               new float[]{0, (float)mdMediumSize}, 0.0f));
            break;
        } */
            default:    // solid
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth));  // by default it is solid.
        }

        graphics.drawLine((int)x0, (int)y0, (int)x1, (int)y1);

        // restore paint attribute.
        graphics.setStroke(strokeOriginal);
        graphics.setColor(colorPaintOriginalColor);
        graphics.setComposite(compositeOriginal);
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawPolygon(g, points, color, drawMode, pei);
        g.dispose();
    }
    
    public static void drawPolygon(Graphics2D graphics, LinkedList<double[]> points,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        if (points.size() < 3) {
            return; // The number of points is invalid.
        }
        int[] xValues = new int[points.size()];
        int[] yValues = new int[points.size()];
        for (int idx = 0; idx < points.size(); idx ++) {
            xValues[idx] = (int)points.get(idx)[0];
            yValues[idx] = (int)points.get(idx)[1];
        }
        Composite compositeOriginal = graphics.getComposite();
        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }
        if (drawMode <= 0) {
            // fill.
            graphics.fillPolygon(xValues, yValues, points.size());
        } else {
            // draw the frame.
            BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();        
            graphics.setStroke(new BasicStroke(drawMode));  // by default it is solid.
            graphics.drawPolygon(xValues, yValues, points.size());
            graphics.setStroke(strokeOriginal);
        }
        
        graphics.setColor(colorPaintOriginalColor); // restore original color.
        graphics.setComposite(compositeOriginal);    // restore original composite.
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawRect(g, x, y, width, height, color, drawMode, pei);
        g.dispose();
    }
    
    public static void drawRect(Graphics2D graphics, double x, double y, double width, double height,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        Composite compositeOriginal = graphics.getComposite();
        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }
        if (drawMode <= 0) {
            // fill.
            graphics.fillRect((int)x, (int)y, (int)width, (int)height);
        } else {
            // draw the frame.
            BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();        
            graphics.setStroke(new BasicStroke(drawMode));  // by default it is solid.
            graphics.drawRect((int)x, (int)y, (int)width, (int)height);
            graphics.setStroke(strokeOriginal);
        }
        
        graphics.setColor(colorPaintOriginalColor); // restore original color.
        graphics.setComposite(compositeOriginal);    // restore original composite.
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        clearRect(g, x, y, width, height);
        g.dispose();
    }
    
    public static void clearRect(Graphics2D graphics, double x, double y, double width, double height) {
        Composite cOriginal = graphics.getComposite();
        Paint pOriginal = graphics.getPaint();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0));
        graphics.setPaint(java.awt.Color.BLACK);
        graphics.fillRect((int)x, (int)y, (int)width, (int)height);
        graphics.setPaint(pOriginal);
        graphics.setComposite(cOriginal);
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        drawOval(g, x, y, width, height, color, drawMode, pei);
        g.dispose();
    }
    
    public static void drawOval(Graphics2D graphics, double x, double y, double width, double height,
            com.cyzapps.VisualMFP.Color color, int drawMode, PaintingExtraInfo pei) {
        Composite compositeOriginal = graphics.getComposite();
        if (pei != null) {
            graphics.setComposite(convert2AlphaComposite(pei.mpdm));
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }
        if (drawMode <= 0) {
            // fill.
            graphics.fillOval((int)x, (int)y, (int)width, (int)height);
        } else {
            // draw the frame.
            BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();        
            graphics.setStroke(new BasicStroke(drawMode));  // by default it is solid.
            graphics.drawOval((int)x, (int)y, (int)width, (int)height);
            graphics.setStroke(strokeOriginal);
        }
        
        graphics.setColor(colorPaintOriginalColor); // restore original color.
        graphics.setComposite(compositeOriginal);    // restore original composite.
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
        if (mcurrentImage == null) {
            return; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        clearOval(g, x, y, width, height);
        g.dispose();
    }
    
    public static void clearOval(Graphics2D graphics, double x, double y, double width, double height) {
        Composite cOriginal = graphics.getComposite();
        Paint pOriginal = graphics.getPaint();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0));
        graphics.setPaint(java.awt.Color.BLACK);
        graphics.fillOval((int)x, (int)y, (int)width, (int)height);
        graphics.setPaint(pOriginal);
        graphics.setComposite(cOriginal);
    }
    
    /**
     * return false if loading image fails.
     * @param imgHandle
     * @param left
     * @param top
     * @param wRatio
     * @param hRatio
     * @param pei
     * @return 
     */
    @Override
    public boolean drawImage(DataClass imgHandle, double left, double top, double wRatio, double hRatio, PaintingExtraInfo pei) {
        if (mcurrentImage == null) {
            return false; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        boolean b = drawImage(g, imgHandle, left, top, wRatio, hRatio, pei);
        g.dispose();
        return b;
    }
    
    public static boolean drawImage(Graphics2D graphics, DataClass imgHandle, double left, double top, double wRatio, double hRatio, PaintingExtraInfo pei) {
        BufferedImage img = null;
        DataClass datumConverted = null;
        if (null != (datumConverted = DCHelper.try2LightCvtOrRetDCString(imgHandle))) {
            // string or derivative of string
            try {
                img = ImageIO.read(new File(DCHelper.lightCvtOrRetDCString(datumConverted).getStringValue()));
            } catch (IOException e) {
                // failed to load image.
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);  // unexpected exception.
            }
        } else if (null != (datumConverted = DCHelper.try2LightCvtOrRetDCExtObjRef(imgHandle))) {
            try {
                // should be a buffered image
                if (DCHelper.lightCvtOrRetDCExtObjRef(datumConverted).getExternalObject() instanceof ImageWrapper) { 
                    img = ((ImageWrapper)DCHelper.lightCvtOrRetDCExtObjRef(datumConverted).getExternalObject()).getImageFromWrapper();
                }
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);  // unexpected exception.
            }
        }
        if (img != null) {
            Composite compositeOriginal = graphics.getComposite();
            if (pei != null) {
                graphics.setComposite(convert2AlphaComposite(pei.mpdm));
            }
            if (wRatio == 1.0 && hRatio == 1.0) {
                graphics.drawImage(img, (int)left, (int)top, null);
            } else {
                int destW = (int) (img.getWidth() * wRatio);
                int destH = (int) (img.getHeight() * hRatio);
                if (destW < 1) {
                    destW = 1;
                }
                if (destH < 1) {
                    destH = 1;
                }
                graphics.drawImage(img, (int)left, (int)top, destW, destH, null);
            }
            graphics.setComposite(compositeOriginal);
            return true;
        }
        return false;
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
        if (mcurrentImage == null) {
            return false; // if current image hasn't been fully initialized, return.
        }
        Graphics2D g = mcurrentImage.createGraphics();
        boolean b = drawImage(g, imgHandle, srcX1, srcY1, srcX2, srcY2, dstX1, dstY1, dstX2, dstY2, pei);
        g.dispose();
        return b;
    }
    
    public static boolean drawImage(Graphics2D graphics, DataClass imgHandle,
            double srcX1, double srcY1, double srcX2, double srcY2,
            double dstX1, double dstY1, double dstX2, double dstY2,
            PaintingExtraInfo pei) {
        BufferedImage img = null;
        DataClass datumConverted = null;
        if (null != (datumConverted = DCHelper.try2LightCvtOrRetDCString(imgHandle))) {
            // string or derivative of string
            try {
                img = ImageIO.read(new File(DCHelper.lightCvtOrRetDCString(datumConverted).getStringValue()));
            } catch (IOException e) {
                // failed to load image.
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);  // unexpected exception.
            }
        } else if (null != (datumConverted = DCHelper.try2LightCvtOrRetDCExtObjRef(imgHandle))) {
            try {
                // should be an image wrapper
                if (DCHelper.lightCvtOrRetDCExtObjRef(datumConverted).getExternalObject() instanceof ImageWrapper) { 
                    img = ((ImageWrapper)DCHelper.lightCvtOrRetDCExtObjRef(datumConverted).getExternalObject()).getImageFromWrapper();
                }
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(FlatGDI.class.getName()).log(Level.SEVERE, null, ex);  // unexpected exception.
            }
        }
        if (img != null) {
            Composite compositeOriginal = graphics.getComposite();
            if (pei != null) {
                graphics.setComposite(convert2AlphaComposite(pei.mpdm));
            }
            graphics.drawImage(img, (int)dstX1, (int)dstY1, (int)dstX2, (int)dstY2,
                    (int)srcX1, (int)srcY1, (int)srcX2, (int)srcY2, null);
            graphics.setComposite(compositeOriginal);
            return true;
        }
        return false;
    }

    public static void drawBackgroundImage(Graphics2D g, int width, int height, ImageWrapper imageBkGrnd, int bkGrndImgMode) {
        // draw background image
        if (imageBkGrnd != null && width >= 0 && height >= 0) {
            // no need to worry about invalid parameter because width and height now are valid.
            int w = imageBkGrnd.getImageFromWrapper().getWidth();
            int h = imageBkGrnd.getImageFromWrapper().getHeight();
            switch(bkGrndImgMode) {
                case 3: // original image in the middle
                    int overlappedWidth = Math.min(width, w);
                    int overlappedHeight = Math.min(height, h);
                    int srcX1 = 0, srcX2 = w, dstX1 = 0, dstX2 = width;
                    if (overlappedWidth < w) {
                        srcX1 = (w - overlappedWidth) / 2;
                        srcX2 = srcX1 + overlappedWidth;
                    }
                    if (overlappedWidth < width) {
                        dstX1 = (width - overlappedWidth) / 2;
                        dstX2 = dstX1 + overlappedWidth;
                    }
                    int srcY1 = 0, srcY2 = h, dstY1 = 0, dstY2 = height;
                    if (overlappedHeight < h) {
                        srcY1 = (h - overlappedHeight) / 2;
                        srcY2 = srcY1 + overlappedHeight;
                    }
                    if (overlappedHeight < height) {
                        dstY1 = (height - overlappedHeight) / 2;
                        dstY2 = dstY1 + overlappedHeight;
                    }
                    g.drawImage(imageBkGrnd.getImageFromWrapper(), dstX1, dstY1, dstX2, dstY2,
                                srcX1, srcY1, srcX2, srcY2, null);
                    break;
                case 2: // tiled

                    for (int x = 0; x < width; x += w) {
                        for (int y = 0; y < height; y += h) {
                            g.drawImage(imageBkGrnd.getImageFromWrapper(), x, y, null);
                        }
                    }
                    break;
                case 1: // scaled
                    g.drawImage(imageBkGrnd.getImageFromWrapper(), 0, 0, width, height,
                                0, 0, w, h, null);
                    break;
                default: // original image.
                    g.drawImage(imageBkGrnd.getImageFromWrapper(), 0, 0, null);
            }
        }
        
    }
    
    public static void drawBackground(BufferedImage currentImg, Color colorBkGrnd, ImageWrapper imageBkGrnd, int bkGrndImgMode) {
        if (currentImg == null) {
            return;
        }
        Graphics2D g = currentImg.createGraphics();
        // draw background color.
        g.setBackground(new java.awt.Color(
                            colorBkGrnd.mnR,
                            colorBkGrnd.mnG,
                            colorBkGrnd.mnB,
                            colorBkGrnd.mnAlpha));
        g.clearRect(0, 0, currentImg.getWidth(), currentImg.getHeight());
        
        drawBackgroundImage(g, currentImg.getWidth(), currentImg.getHeight(), imageBkGrnd, bkGrndImgMode);
        g.dispose();        
    }
    
    protected void startPainting() {
        if (mnTargetWidth <= 0 || mnTargetHeight <= 0 || !mbDisplayOnLive) {
            mcurrentImage = null;
            return;    // no need to paint.
        } 
        if (!mbImageUptoDate) { // current image cannot be reused.
            mcurrentImage = new BufferedImage(mnTargetWidth, mnTargetHeight, BufferedImage.TYPE_INT_ARGB);
            draw(0, 0, mnTargetWidth, mnTargetHeight);
            mbImageUptoDate = true;
        }
    }
    
    public void draw(double x, double y, double width, double height) {
        if (mbDisplayOnLive) {
            drawBackground(mcurrentImage, mcolorBkGrnd, mimageBkGrnd, mBkgrndImgMode);
            // no need to remove all obsolete painting call backs because these call backs
            // have been removed when updatepainting call back is added.
            double right = x + width;
            double bottom = y + height;
            boolean bWholeDisplayInRange = (x == 0) && (y == 0)
                    && (width == mnTargetWidth) // no need to worry about getGDIView() is null.
                    && (height == mnTargetHeight);
            mqueuePaintingCallBacks.stream().filter((pcb) -> (this.equals(pcb.getDisplay2D())))
                    .filter((pcb) -> (bWholeDisplayInRange || pcb.isInPaintingRange(x, y, right, bottom)))
                    .forEachOrdered((pcb) -> {
                pcb.call(); // draw all the painting call backs.
            }); // the same Display2D
        }
    }
    
    @Override
    public void repaint() {
        startPainting();
    }

    @Override
    public DisplayLib.GraphicDisplayType getDisplayType() {
        if (!mbDisplayOnLive) {
            return GraphicDisplayType.INVALID_DISPLAY;
        } else if (mstrFilePath == null) {
            return GraphicDisplayType.IMAGE_DISPLAY;
        } else {
            return GraphicDisplayType.IMAGE_PATH_DISPLAY;
        }
    }

    @Override
    public void initialize() {
        // do nothing.
    }

    @Override
    public void update() {
        // at this moment, do nothing here.
    }

    @Override
    public void close() {
        if (!mbDisplayOnLive) {
            return;
        } else {
            // do not draw image or save image, just close.
            mcolorBkGrnd = new Color();
            mBkgrndImgMode = 0;
            mimageBkGrnd = null;
            mnTargetWidth = 0;
            mnTargetHeight = 0;
            mstrFilePath = null;
            mcurrentImage = null;
            mbImageUptoDate = false;
            mbDisplayOnLive = false;
        }
    }
}
