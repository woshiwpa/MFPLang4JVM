// MFP project, FlatChart.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.PlotAdapter;

import java.io.IOException;

import com.cyzapps.VisualMFP.CoordAxis;
import com.cyzapps.VisualMFP.LineStyle;
import com.cyzapps.VisualMFP.LineStyle.LINEPATTERN;
import com.cyzapps.VisualMFP.PointStyle;
import com.cyzapps.VisualMFP.Position3D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class FlatChart extends MFPChart {

    public static final String LOG_TAG = "com.apps.cyzsoft.PlotAdapter.FlatChart";

    public String mstrChartTitle = "";

    public final double MAX_SCALING_RATIO = 1.0e32;
    public final double MIN_SCALING_RATIO = 1.0e-32;

    public double mdVeryTinySize = 2;
    public double mdTinySize = 4;
    public double mdVerySmallSize = 8;
    public double mdSmallSize = 16;
    public double mdMediumSize = 32;
    public double mdLargeSize = 64;
    public double mdVeryLargeSize = 128;
    public double mdHugeSize = 256;
    public double mdVeryHugeSize = 512;

    public double mdArrowAngle = Math.PI / 3;
    public double mdSpaceBtwTxtLines = 5;

    public com.cyzapps.VisualMFP.Color mcolorBkGrnd = new com.cyzapps.VisualMFP.Color();

    // default foreground color
    public com.cyzapps.VisualMFP.Color mcolorForeGrnd = new com.cyzapps.VisualMFP.Color(255, 255, 255);

    public boolean mbUseInit2CalibrateZoom = false;

    public boolean mbCfgWindowStarted = false;

    public double mdWidth = 0, mdHeight = 0;

    /**
     * The config icon.
     */
    public BufferedImage mcfgImage_24, mcfgImage_32, mcfgImage_48, mcfgImage_64;
    /**
     * The zoom in icon.
     */
    public BufferedImage mzoomInImage_24, mzoomInImage_32, mzoomInImage_48, mzoomInImage_64;
    /**
     * The zoom out icon.
     */
    public BufferedImage mzoomOutImage_24, mzoomOutImage_32, mzoomOutImage_48, mzoomOutImage_64;
    /**
     * The fit zoom icon.
     */
    public BufferedImage mfitZoomImage_24, mfitZoomImage_32, mfitZoomImage_48, mfitZoomImage_64;
    /**
     * The xy1To1 zoom icon.
     */
    public BufferedImage mxy1To1ZoomImage_24, mxy1To1ZoomImage_32, mxy1To1ZoomImage_48, mxy1To1ZoomImage_64;
    /**
     * The zoom buttons background color.
     */
    public java.awt.Color mcolorCfgZoomBtnsBk = new java.awt.Color(150, 150, 150, 175);
    /**
     * The zoom buttons rectangle.
     */
    public Rectangle2D mrectZoomR = new Rectangle2D.Double();

    public boolean mbLandScapeMode = false;

    public FlatChart() {
        initialize();
    }

    /**
     * Calculates the best text to fit into the available space.
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
     */
    public static int getNumOfCharsInWidth(double width, Graphics2D graphics) {
        if (width <= 0) {
            return 0;    // width cannot be negative.
        }
        FontMetrics metrics = graphics.getFontMetrics();
        double dWWidth = metrics.stringWidth("W");
        return (int) (width / dWWidth);
    }

    /**
     * Draw a multiple lines text.
     */
    public void drawText(Graphics2D graphics, String text, double x, double y,
            com.cyzapps.VisualMFP.Color color, double dTextSize, double dRotateRadian) {

        float fRotateRadian = (float) dRotateRadian, fX = (float) x, fY = (float) y;
        if (dRotateRadian != 0) {
            graphics.rotate(fRotateRadian, fX, fY);
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }    // otherwise, use paint's color.
        float fPaintOriginalTxtSize = graphics.getFont().getSize2D();
        Font fontNew = new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) dTextSize);
        graphics.setFont(fontNew);
        graphics.setFont(fontNew);
        // get metrics from the graphics
        FontMetrics metrics = graphics.getFontMetrics();
        // get the height of a line of text in this font and render context
        int nFontHeight = metrics.getHeight();
        // get the advance of my text in this font and render context
        //int adv = metrics.stringWidth(text);
        String[] lines = text.split("\n");
        int yOff = 0;
        for (int i = 0; i < lines.length; ++i) {
            graphics.drawString(lines[i], fX, fY + metrics.getLeading() + metrics.getAscent() + yOff);
            yOff = (int) (yOff + nFontHeight + mdSpaceBtwTxtLines); // space between lines
        }
        graphics.setFont(new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) fPaintOriginalTxtSize));
        graphics.setColor(colorPaintOriginalColor);
        if (dRotateRadian != 0) {
            graphics.rotate(-fRotateRadian, fX, fY);
        }
    }

    /**
     * Draw a single line text, x, y is in the middle of the line.
     */
    public void draw1LineTextAnchorMid(Graphics2D graphics, String text, double x, double y,
            com.cyzapps.VisualMFP.Color color, double dTextSize, double dRotateRadian) {

        float fRotateRadian = (float) dRotateRadian, fX = (float) x, fY = (float) y;
        if (dRotateRadian != 0) {
            graphics.rotate(fRotateRadian, fX, fY);
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        float fPaintOriginalTxtSize = graphics.getFont().getSize2D();
        if (color != null) {
            graphics.setColor(new java.awt.Color(color.getF1R(), color.getF1G(), color.getF1B(), color.getF1Alpha()));
        }    // otherwise, use paint's color.
        Font fontNew = new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) dTextSize);
        graphics.setFont(fontNew);
        float fWidth = graphics.getFontMetrics().stringWidth(text);
        float fDescent = graphics.getFontMetrics().getDescent();
        graphics.drawString(text, fX - fWidth / 2, fY + (float) dTextSize / 2 - fDescent);
        graphics.setFont(new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) fPaintOriginalTxtSize));
        graphics.setColor(colorPaintOriginalColor);
        if (dRotateRadian != 0) {
            graphics.rotate(-fRotateRadian, fX, fY);
        }
    }

    /**
     * Draws the chart background.
     */
    public void drawBackground(Graphics2D graphics, double x, double y, double width, double height,
            com.cyzapps.VisualMFP.Color newColor) {
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();
        if (newColor != null) {    // a valid color
            graphics.setColor(new java.awt.Color(newColor.getF1R(), newColor.getF1G(), newColor.getF1B(), newColor.getF1Alpha()));
        } else if (mcolorBkGrnd != null) {
            graphics.setColor(new java.awt.Color(mcolorBkGrnd.getF1R(), mcolorBkGrnd.getF1G(), mcolorBkGrnd.getF1B(), mcolorBkGrnd.getF1Alpha()));
        }
        graphics.setStroke(new BasicStroke(strokeOriginal.getLineWidth(),
                strokeOriginal.getEndCap(),
                strokeOriginal.getLineJoin()));  // assume by default it is a solid fill.
        float fLeft = (float) x, fTop = (float) y, fRight = (float) (x + width), fBottom = (float) (y + height);
        graphics.fillRect((int) fLeft, (int) fTop, (int) (fRight - fLeft), (int) (fBottom - fTop));
        // restore paint attribute.
        graphics.setStroke(strokeOriginal);
        graphics.setColor(colorPaintOriginalColor);
    }

    public void drawPoint(Graphics2D graphics, Position3D point, PointStyle pointStyle) {
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();

        if (pointStyle.mclr != null) {
            graphics.setColor(new java.awt.Color(pointStyle.mclr.getF1R(), pointStyle.mclr.getF1G(), pointStyle.mclr.getF1B(), pointStyle.mclr.getF1Alpha()));
        }
        graphics.setStroke(new BasicStroke(1.0f, strokeOriginal.getEndCap(), // new width is 1.0f
                strokeOriginal.getLineJoin(), 1.0f,
                new float[]{(float) mdMediumSize, 0}, 0.0f));
        double dSize = pointStyle.mdSize;
        switch (pointStyle.menumPointShape) {
            case POINTSHAPE_CIRCLE: {
                float fX = (float) point.getX();
                float fY = (float) point.getY();
                float fRadius = (float) (dSize / 2);
                graphics.drawOval((int) (fX - fRadius / 2.0), (int) (fY - fRadius / 2.0), (int) fRadius, (int) fRadius);
                break;
            }
            case POINTSHAPE_DIAMOND: {
                float fX = (float) point.getX();
                float fY = (float) point.getY();
                float fX1 = (float) (point.getX() - dSize / 2), fX2 = (float) (point.getX() + dSize / 2);
                float fY1 = (float) (point.getY() - dSize / 2), fY2 = (float) (point.getY() + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY, (int) fX, (int) fY1);
                graphics.drawLine((int) fX1, (int) fY, (int) fX, (int) fY2);
                graphics.drawLine((int) fX2, (int) fY, (int) fX, (int) fY1);
                graphics.drawLine((int) fX2, (int) fY, (int) fX, (int) fY2);
                break;
            }
            case POINTSHAPE_CROSS: {
                float fX = (float) point.getX();
                float fY = (float) point.getY();
                float fX1 = (float) (point.getX() - dSize / 2), fX2 = (float) (point.getX() + dSize / 2);
                float fY1 = (float) (point.getY() - dSize / 2), fY2 = (float) (point.getY() + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY, (int) fX2, (int) fY);
                graphics.drawLine((int) fX, (int) fY1, (int) fX, (int) fY2);
                break;
            }
            case POINTSHAPE_X: {
                float fX1 = (float) (point.getX() - dSize / 2), fX2 = (float) (point.getX() + dSize / 2);
                float fY1 = (float) (point.getY() - dSize / 2), fY2 = (float) (point.getY() + dSize / 2);
                graphics.drawLine((int) fX1, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY1);
                break;
            }
            case POINTSHAPE_DOWNTRIANGLE: {
                float fX = (float) point.getX();
                float fX1 = (float) (point.getX() - dSize * 0.433), fX2 = (float) (point.getX() + dSize * 0.433);
                float fY1 = (float) (point.getY() + dSize / 2), fY2 = (float) (point.getY() - dSize / 4);
                graphics.drawLine((int) fX, (int) fY1, (int) fX1, (int) fY2);
                graphics.drawLine((int) fX, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY2);
                break;
            }
            case POINTSHAPE_UPTRIANGLE: {
                float fX = (float) point.getX();
                float fX1 = (float) (point.getX() - dSize * 0.433), fX2 = (float) (point.getX() + dSize * 0.433);
                float fY1 = (float) (point.getY() - dSize / 2), fY2 = (float) (point.getY() + dSize / 4);
                graphics.drawLine((int) fX, (int) fY1, (int) fX1, (int) fY2);
                graphics.drawLine((int) fX, (int) fY1, (int) fX2, (int) fY2);
                graphics.drawLine((int) fX1, (int) fY2, (int) fX2, (int) fY2);
                break;
            }
            case POINTSHAPE_SQUARE: {
                float fX1 = (float) (point.getX() - dSize / 2);
                float fX2 = (float) (point.getX() + dSize / 2);
                float fY1 = (float) (point.getY() - dSize / 2);
                float fY2 = (float) (point.getY() + dSize / 2);
                graphics.drawRect((int) fX1, (int) fY1, (int) dSize, (int) dSize);
                break;
            }
            default:    // dot.
                graphics.drawLine((int) (point.getX()), (int) (point.getY()), (int) (point.getX()), (int) (point.getY()));
        }

        // restore paint attribute.
        graphics.setStroke(strokeOriginal);
        graphics.setColor(colorPaintOriginalColor);
    }

    public void drawLine(Graphics2D graphics, Position3D p3PrevPoint, Position3D p3NextPoint, LineStyle lineStyle) {
        if (lineStyle.menumLinePattern == LINEPATTERN.LINEPATTERN_NON) {
            return;
        }
        java.awt.Color colorPaintOriginalColor = graphics.getColor();
        BasicStroke strokeOriginal = (BasicStroke) graphics.getStroke();

        if (lineStyle.mclr != null) {
            graphics.setColor(new java.awt.Color(lineStyle.mclr.getF1R(), lineStyle.mclr.getF1G(), lineStyle.mclr.getF1B(), lineStyle.mclr.getF1Alpha()));
        }
        switch (lineStyle.menumLinePattern) {
            case LINEPATTERN_DASH: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) mdVerySmallSize, (float) mdTinySize}, 0.0f));
                break;
            }
            case LINEPATTERN_DOT: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) mdVeryTinySize, (float) mdTinySize}, 0.0f));
                break;
            }
            case LINEPATTERN_DASH_DOT: {
                graphics.setStroke(new BasicStroke((float) lineStyle.mdLineWidth, strokeOriginal.getEndCap(),
                        strokeOriginal.getLineJoin(), 1.0f,
                        new float[]{(float) mdVerySmallSize, (float) mdTinySize, (float) mdVeryTinySize, (float) mdTinySize}, 0.0f));
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

        float fX0 = (float) p3PrevPoint.getX(), fY0 = (float) p3PrevPoint.getY(),
                fX1 = (float) p3NextPoint.getX(), fY1 = (float) p3NextPoint.getY();
        graphics.drawLine((int) fX0, (int) fY0, (int) fX1, (int) fY1);

        // restore paint attribute.
        graphics.setStroke(strokeOriginal);
        graphics.setColor(colorPaintOriginalColor);
    }

    public void drawButtons(Graphics2D graphics, double left, double top, double width, double height) {

    }

    public void draw(Graphics2D graphics, double x, double y, double width, double height) {
        drawBackground(graphics, x, y, width, height, null);
        float fPaintOriginalTxtSize = graphics.getFont().getSize2D();
        graphics.setFont(new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) (width / 2)));
        draw1LineTextAnchorMid(graphics, mstrChartTitle, x / 2, y + mdSmallSize, null, mdSmallSize, 0);
        drawButtons(graphics, x, y, width, height);
        graphics.setFont(new Font(graphics.getFont().getName(), graphics.getFont().getStyle(), (int) fPaintOriginalTxtSize));
    }

    public void calcCoordArea(double x, double y, double width, double height) {
    }

    public void update() {

    }

    public boolean isZoomInEnabled() {
        return true;
    }

    public boolean isZoomOutEnabled() {
        return true;
    }

    public void config() {

    }

    public void initialize() {
        try {
            mcfgImage_24 = ImageIO.read(getClass().getResource("/jcmdline/resources/setting_gear_24.png"));
        } catch (IOException ex) {
            mcfgImage_24 = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mcfgImage_32 = ImageIO.read(getClass().getResource("/jcmdline/resources/setting_gear_32.png"));
        } catch (IOException ex) {
            mcfgImage_32 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mcfgImage_48 = ImageIO.read(getClass().getResource("/jcmdline/resources/setting_gear_48.png"));
        } catch (IOException ex) {
            mcfgImage_48 = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mcfgImage_64 = ImageIO.read(getClass().getResource("/jcmdline/resources/setting_gear_64.png"));
        } catch (IOException ex) {
            mcfgImage_64 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomInImage_24 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_in_24.png"));
        } catch (IOException ex) {
            mzoomInImage_24 = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomInImage_32 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_in_32.png"));
        } catch (IOException ex) {
            mzoomInImage_32 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomInImage_48 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_in_48.png"));
        } catch (IOException ex) {
            mzoomInImage_48 = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomInImage_64 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_in_64.png"));
        } catch (IOException ex) {
            mzoomInImage_64 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomOutImage_24 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_out_24.png"));
        } catch (IOException ex) {
            mzoomOutImage_24 = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomOutImage_32 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_out_32.png"));
        } catch (IOException ex) {
            mzoomOutImage_32 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomOutImage_48 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_out_48.png"));
        } catch (IOException ex) {
            mzoomOutImage_48 = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mzoomOutImage_64 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_out_64.png"));
        } catch (IOException ex) {
            mzoomOutImage_64 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mfitZoomImage_24 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_fit_24.png"));
        } catch (IOException ex) {
            mfitZoomImage_24 = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mfitZoomImage_32 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_fit_32.png"));
        } catch (IOException ex) {
            mfitZoomImage_32 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mfitZoomImage_48 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_fit_48.png"));
        } catch (IOException ex) {
            mfitZoomImage_48 = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mfitZoomImage_64 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_fit_64.png"));
        } catch (IOException ex) {
            mfitZoomImage_64 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mxy1To1ZoomImage_24 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_1_24.png"));
        } catch (IOException ex) {
            mxy1To1ZoomImage_24 = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mxy1To1ZoomImage_32 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_1_32.png"));
        } catch (IOException ex) {
            mxy1To1ZoomImage_32 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mxy1To1ZoomImage_48 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_1_48.png"));
        } catch (IOException ex) {
            mxy1To1ZoomImage_48 = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        }
        try {
            mxy1To1ZoomImage_64 = ImageIO.read(getClass().getResource("/jcmdline/resources/zoom_1_64.png"));
        } catch (IOException ex) {
            mxy1To1ZoomImage_64 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public void zoom(double dXRatio, double dYRatio) {

    }

    public void slide(double dOldX, double dOldY, double dNewX, double dNewY) {

    }

    public void resize(double dWidth, double dHeight, double dOldWidth, double dOldHeight) {
        if (dWidth != dOldWidth || dHeight != dOldHeight) {
            mdWidth = dWidth;
            mdHeight = dHeight;
            calcCoordArea(0, 0, dWidth, dHeight);
            update();
        }
    }

    public void zoom1To1() {

    }

    public void zoomReset() {

    }

    public void resetAllSizesBasedOnMedium() {
        mdVeryTinySize = mdMediumSize / 16;
        mdTinySize = mdMediumSize / 8;
        mdVerySmallSize = mdMediumSize / 4;
        mdSmallSize = mdMediumSize / 2;
        mdLargeSize = mdMediumSize * 2;
        mdVeryLargeSize = mdMediumSize * 4;
        mdHugeSize = mdMediumSize * 8;
        mdVeryHugeSize = mdMediumSize * 16;
    }

    public static double solve1OrderLinearEq(Position3D p31, Position3D p32, double input, boolean bSolveY) {
        if (bSolveY) {
            if (p31.getX() == p32.getX()) {
                if (input == p31.getX()) {
                    return Double.NaN;
                } else {
                    return Double.POSITIVE_INFINITY;
                }
            } else {
                double deltaX = p32.getX() - p31.getX();
                return (p32.getY() - p31.getY()) / deltaX * input + (p32.getX() * p31.getY() - p31.getX() * p32.getY()) / deltaX;
            }
        } else {
            if (p31.getY() == p32.getY()) {
                if (input == p31.getY()) {
                    return Double.NaN;
                } else {
                    return Double.POSITIVE_INFINITY;
                }
            } else {
                double deltaY = p32.getY() - p31.getY();
                return (p32.getX() - p31.getX()) / deltaY * input + (p32.getY() * p31.getX() - p31.getY() * p32.getX()) / deltaY;
            }
        }
    }

    public static Position3D[] findInRangeLine(double x1, double x2, double y1, double y2, Position3D p31, Position3D p32) {
        // assume p31 and p32 are not null.

        double dXMax = Math.max(x1, x2), dXMin = Math.min(x1, x2), dYMax = Math.max(y1, y2), dYMin = Math.min(y1, y2);
        if (p31.getX() >= dXMin && p31.getX() <= dXMax && p31.getY() >= dYMin && p31.getY() <= dYMax) {
            if (p32.getX() >= dXMin && p32.getX() <= dXMax && p32.getY() >= dYMin && p32.getY() <= dYMax) {
                Position3D[] points = new Position3D[2];
                points[0] = p31;
                points[1] = p32;
                return points;
            } else {
                // p31 in range while p32 is not
                Position3D p3End2 = null;
                double dXMinY = Double.NaN, dXMaxY = Double.NaN, dYMinX = Double.NaN, dYMaxX = Double.NaN;
                if (p32.getX() < dXMin) {
                    dXMinY = solve1OrderLinearEq(p31, p32, dXMin, true);
                    if (dXMinY >= dYMin && dXMinY <= dYMax) {
                        p3End2 = new Position3D(dXMin, dXMinY);
                    }
                } else if (p32.getX() > dXMax) {
                    dXMaxY = solve1OrderLinearEq(p31, p32, dXMax, true);
                    if (dXMaxY >= dYMin && dXMaxY <= dYMax) {
                        p3End2 = new Position3D(dXMax, dXMaxY);
                    }
                }
                if (p32.getY() < dYMin && p3End2 == null) {
                    dYMinX = solve1OrderLinearEq(p31, p32, dYMin, false);
                    if (dYMinX >= dXMin && dYMinX <= dXMax) {
                        p3End2 = new Position3D(dYMinX, dYMin);
                    }
                } else if (p32.getY() > dYMax && p3End2 == null) {
                    dYMaxX = solve1OrderLinearEq(p31, p32, dYMax, false);
                    if (dYMaxX >= dXMin && dYMaxX <= dXMax) {
                        p3End2 = new Position3D(dYMaxX, dYMax);
                    }
                }
                Position3D[] points;
                if (p3End2 == null) {
                    points = new Position3D[1];
                    points[0] = p31;
                } else {
                    points = new Position3D[2];
                    points[0] = p31;
                    points[1] = p3End2;
                }
                return points;
            }
        } else if (p32.getX() >= dXMin && p32.getX() <= dXMax && p32.getY() >= dYMin && p32.getY() <= dYMax) {
            // p32 in range while p31 is not
            Position3D p3End1 = null;
            double dXMinY = Double.NaN, dXMaxY = Double.NaN, dYMinX = Double.NaN, dYMaxX = Double.NaN;
            if (p31.getX() < dXMin) {
                dXMinY = solve1OrderLinearEq(p31, p32, dXMin, true);
                if (dXMinY >= dYMin && dXMinY <= dYMax) {
                    p3End1 = new Position3D(dXMin, dXMinY);
                }
            } else if (p31.getX() > dXMax) {
                dXMaxY = solve1OrderLinearEq(p31, p32, dXMax, true);
                if (dXMaxY >= dYMin && dXMaxY <= dYMax) {
                    p3End1 = new Position3D(dXMax, dXMaxY);
                }
            }
            if (p31.getY() < dYMin && p3End1 == null) {
                dYMinX = solve1OrderLinearEq(p31, p32, dYMin, false);
                if (dYMinX >= dXMin && dYMinX <= dXMax) {
                    p3End1 = new Position3D(dYMinX, dYMin);
                }
            } else if (p31.getY() > dYMax && p3End1 == null) {
                dYMaxX = solve1OrderLinearEq(p31, p32, dYMax, false);
                if (dYMaxX >= dXMin && dYMaxX <= dXMax) {
                    p3End1 = new Position3D(dYMaxX, dYMax);
                }
            }
            Position3D[] points;
            if (p3End1 == null) {
                points = new Position3D[1];
                points[0] = p32;
            } else {
                points = new Position3D[2];
                points[0] = p3End1;
                points[1] = p32;
            }
            return points;
        } else {
            double dXMinY = Double.NaN, dXMaxY = Double.NaN, dYMinX = Double.NaN, dYMaxX = Double.NaN;
            Position3D p3Ends[] = new Position3D[4];
            Position3D p3End1 = null, p3End2 = null;
            int nNumofEnds = 0;
            if ((dXMin - p31.getX()) * (dXMin - p32.getX()) <= 0) {
                dXMinY = solve1OrderLinearEq(p31, p32, dXMin, true);
                if (Double.isInfinite(dXMinY) == false && Double.isNaN(dXMinY) == false
                        && (dXMinY - dYMin) * (dXMinY - dYMax) <= 0) {
                    p3Ends[0] = new Position3D(dXMin, dXMinY);
                    p3End1 = p3Ends[0];
                    nNumofEnds++;
                }
            }
            if ((dXMax - p31.getX()) * (dXMax - p32.getX()) <= 0) {
                dXMaxY = solve1OrderLinearEq(p31, p32, dXMax, true);
                if (Double.isInfinite(dXMaxY) == false && Double.isNaN(dXMaxY) == false
                        && (dXMaxY - dYMin) * (dXMaxY - dYMax) <= 0) {
                    p3Ends[1] = new Position3D(dXMax, dXMaxY);
                    if (nNumofEnds == 0) {
                        p3End1 = p3Ends[1];
                    } else {    // nNumofEnds == 1
                        p3End2 = p3Ends[1];
                    }
                    nNumofEnds++;
                }
            }
            Position3D p3End2Candidate = null;
            if ((dYMin - p31.getY()) * (dYMin - p32.getY()) <= 0 && nNumofEnds < 2) {
                dYMinX = solve1OrderLinearEq(p31, p32, dYMin, false);
                if (Double.isInfinite(dYMinX) == false && Double.isNaN(dYMinX) == false
                        && (dYMinX - dXMin) * (dYMinX - dXMax) <= 0) {
                    p3Ends[2] = new Position3D(dYMinX, dYMin);
                    if (nNumofEnds == 0) {
                        p3End1 = p3Ends[2];
                        nNumofEnds++;
                    } else if (nNumofEnds == 1) {
                        p3End2Candidate = p3Ends[2];
                    }
                }
            }
            if ((dYMax - p31.getY()) * (dYMax - p32.getY()) <= 0 && nNumofEnds < 2) {
                dYMaxX = solve1OrderLinearEq(p31, p32, dYMax, false);
                if (Double.isInfinite(dYMaxX) == false && Double.isNaN(dYMaxX) == false
                        && (dYMaxX - dXMin) * (dYMaxX - dXMax) <= 0) {
                    p3Ends[3] = new Position3D(dYMaxX, dYMax);
                    if (nNumofEnds == 0) {
                        p3End1 = p3Ends[3];
                        nNumofEnds++;
                    } else if (nNumofEnds == 1) {
                        if (p3End2Candidate == null) {
                            p3End2 = p3Ends[3];
                        } else {
                            p3End1 = p3End2Candidate;
                            p3End2 = p3Ends[3];
                        }
                        nNumofEnds++;
                    }
                }
            }

            if (nNumofEnds == 1 && p3End2Candidate != null) {
                p3End2 = p3End2Candidate;
                nNumofEnds++;
            }

            Position3D[] points;
            if (nNumofEnds == 0) {
                points = new Position3D[0];
            } else if (nNumofEnds == 1) {
                points = new Position3D[1];
                points[0] = p3End1;
            } else {    // nNumofEnds == 2
                points = new Position3D[2];
                points[0] = p3End1;
                points[1] = p3End2;
            }
            return points;
        }
    }
}
