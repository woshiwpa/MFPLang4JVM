// MFP project, FlatChartView.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.PlotAdapter;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
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

public class FlatChartView extends Frame {

    /**
     * the chart to be drawn
     */
    public FlatChart mflatChart = null;

    /**
     * The view bounds.
     */
    protected Canvas mcanvas = null;

    public FlatChartView(String strFrameTitle) {
        super(strFrameTitle);
    }

    public FlatChartView(String strFrameTitle, FlatChart flatChart) {
        super(strFrameTitle);
        mflatChart = flatChart;
        mflatChart.setGraphContainer(this);
    }

    public Canvas getCanvas() {
        return mcanvas;
    }

    public static void launchChart(FlatChart flatChart) {

        final FlatChartView flatChartView = new FlatChartView(flatChart.mstrChartTitle, flatChart);
        FlatChartEventHandler flatChartEventHandler = new FlatChartEventHandler(flatChart);
        flatChartView.mcanvas = new FlatChartCanvas(flatChart);
        flatChartView.mcanvas.addMouseListener(flatChartEventHandler);
        flatChartView.mcanvas.addMouseMotionListener(flatChartEventHandler);
        flatChartView.mcanvas.addComponentListener(flatChartEventHandler);
        flatChartView.add(flatChartView.mcanvas);
        flatChartView.setSize(640, 480);
        flatChartView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {

                    public void run() {
                        //System.exit(0);
                        flatChartView.dispose();
                    }
                }).start();
            }
        });
        // Center frame
        flatChartView.setLocationRelativeTo(null);
        flatChartView.setVisible(true);
    }

    static public class FlatChartCanvas extends Canvas {

        protected Dimension mdimSize = new Dimension();
        protected Image mimageBuffer;
        protected Graphics mgraphBuffer;
        protected FlatChart mflatChart = null;

        public FlatChartCanvas(FlatChart flatChart) {
            mflatChart = flatChart;
        }

        protected void resetBuffer() {
            // always keep track of the image size
            mdimSize.width = getSize().width;
            mdimSize.height = getSize().height;

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
            //    checks the buffersize with the current panelsize
            //    or initialises the image with the first paint
            if (mdimSize.width != getSize().width
                    || mdimSize.height != getSize().height
                    || mimageBuffer == null || mgraphBuffer == null) {
                resetBuffer();
            }

            if (mgraphBuffer != null) {
                //this clears the offscreen image, not the onscreen one
                mgraphBuffer.clearRect(0, 0, mdimSize.width, mdimSize.height);

                //calls the paintbuffer method with 
                //the offscreen graphics as a param
                paintBuffer(mgraphBuffer);

                //we finaly paint the offscreen image onto the onscreen image
                getGraphics().drawImage(mimageBuffer, 0, 0, this);
            }
        }

        public void paintBuffer(Graphics graphics) {
            mflatChart.draw((Graphics2D) graphics, 0, 0, mdimSize.width, mdimSize.height);
        }

        @Override
        public void update(Graphics g) {
            paint(g);
        }

    }

    static public class FlatChartEventHandler implements MouseListener, MouseMotionListener, ComponentListener {

        /**
         * the chart to be drawn
         */
        public FlatChart mflatChart = null;

        /**
         * The old x coordinates. Use float here coz they definitely are
         * Android's coordinate
         */
        private float mfOldX = -1;
        /**
         * The old y coordinate.
         */
        private float mfOldY = -1;
        /**
         * The old x2 coordinate.
         */
        private float mfOldX2 = -1;
        /**
         * The old y2 coordinate.
         */
        private float mfOldY2 = -1;

        FlatChartEventHandler() {

        }

        FlatChartEventHandler(FlatChart flatChart) {
            mflatChart = flatChart;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            mfOldX = (float) e.getPoint().getX();
            mfOldY = (float) e.getPoint().getY();
            if (mflatChart.mrectZoomR.contains(mfOldX, mfOldY)) {
                int nNumOfButtons = 4;
                int nCfgBtnPosition = 0, nZoomInBtnPosition = 0, nZoomOutBtnPosition = 0, nZoom1BtnPosition = 0, nZoomFitBtnPosition = 0;
                // keep in mind the order, has to be polar first then xy, expr first than non-expr
                // because a mflatChart can be instance of both polar and xy.
                if (mflatChart instanceof PolarExprChart || mflatChart instanceof PolarChart) {
                    if (mflatChart instanceof PolarExprChart) {
                        nNumOfButtons = 4;
                        nCfgBtnPosition = 1;
                    } else if (mflatChart instanceof PolarChart) {
                        nNumOfButtons = 3;
                        nCfgBtnPosition = 0;
                    }
                    nZoomInBtnPosition = nCfgBtnPosition + 1;
                    nZoomOutBtnPosition = nCfgBtnPosition + 2;
                    nZoomFitBtnPosition = nCfgBtnPosition + 3;
                } else if (mflatChart instanceof XYExprChart || mflatChart instanceof XYChart) {
                    if (mflatChart instanceof XYExprChart) {
                        nNumOfButtons = 5;
                        nCfgBtnPosition = 1;
                    } else if (mflatChart instanceof XYChart) {
                        nNumOfButtons = 4;
                        nCfgBtnPosition = 0;
                    }
                    nZoomInBtnPosition = nCfgBtnPosition + 1;
                    nZoomOutBtnPosition = nCfgBtnPosition + 2;
                    nZoom1BtnPosition = nCfgBtnPosition + 3;
                    nZoomFitBtnPosition = nCfgBtnPosition + 4;
                }
                if (!mflatChart.mbLandScapeMode) {
                    if (mfOldX < mflatChart.mrectZoomR.getX() + mflatChart.mrectZoomR.getWidth() * nCfgBtnPosition / nNumOfButtons) {
                        mflatChart.config();
                    } else if (mfOldX < mflatChart.mrectZoomR.getX() + mflatChart.mrectZoomR.getWidth() * nZoomInBtnPosition / nNumOfButtons) {
                        mflatChart.zoom(2.0, 2.0);
                    } else if (mfOldX < mflatChart.mrectZoomR.getX() + mflatChart.mrectZoomR.getWidth() * nZoomOutBtnPosition / nNumOfButtons) {
                        mflatChart.zoom(0.5, 0.5);
                    } else if (mfOldX < mflatChart.mrectZoomR.getX() + mflatChart.mrectZoomR.getWidth() * nZoom1BtnPosition / nNumOfButtons) {
                        mflatChart.zoom1To1();
                    } else {    // zoom fit
                        mflatChart.zoomReset();
                    }
                } else {
                    if (mfOldY < mflatChart.mrectZoomR.getY() + mflatChart.mrectZoomR.getHeight() * nCfgBtnPosition / nNumOfButtons) {
                        mflatChart.config();
                    } else if (mfOldY < mflatChart.mrectZoomR.getY() + mflatChart.mrectZoomR.getHeight() * nZoomInBtnPosition / nNumOfButtons) {
                        mflatChart.zoom(2.0, 2.0);
                    } else if (mfOldY < mflatChart.mrectZoomR.getY() + mflatChart.mrectZoomR.getHeight() * nZoomOutBtnPosition / nNumOfButtons) {
                        mflatChart.zoom(0.5, 0.5);
                    } else if (mfOldY < mflatChart.mrectZoomR.getY() + mflatChart.mrectZoomR.getHeight() * nZoom1BtnPosition / nNumOfButtons) {
                        mflatChart.zoom1To1();
                    } else {    // zoom fit
                        mflatChart.zoomReset();
                    }
                }
                e.getComponent().repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mfOldX = (float) e.getPoint().getX();
            mfOldY = (float) e.getPoint().getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mfOldX = (float) e.getPoint().getX();
            mfOldY = (float) e.getPoint().getY();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            float newX = (float) e.getPoint().getX();
            float newY = (float) e.getPoint().getY();
            if (mfOldX >= 0 && mfOldY >= 0) {    // valid last position.
                /* // Android recalculates point values very slowly so that usually it cannot deliver multiple slide events in
                 * // one mouse movement. But for JAVA on PC is able to transfer multi slide events in one mouse action. As such
                 * // in Android, each slide event make the chart slide a constant length along x or y axis while in Java on PC
                 * // we handle slide events normally for XYExprChart.
                if (mflatChart instanceof XYExprChart || mflatChart instanceof PolarExprChart)    {
                    // multiple continues slides are not supported.
                    float fXMove = newX - mfOldX, fYMove = newY - mfOldY;
                    if (fXMove != 0 || fYMove != 0)    {
                        float fAdjustXMove, fAdjustYMove;
                        if (Math.abs(fXMove) > Math.abs(fYMove))    {
                            fAdjustXMove = (float) (fXMove / Math.abs(fXMove) * 0.375 * (e.getComponent().getWidth() + e.getComponent.getHeight())/2.0);
                            fAdjustYMove = fAdjustXMove * fYMove/fXMove;
                        } else    {
                            fAdjustYMove = (float) (fYMove / Math.abs(fYMove) * 0.375 * (e.getComponent().getWidth() + e.getComponent.getHeight())/2.0);
                            fAdjustXMove = fAdjustYMove * fXMove/fYMove;
                        }
                        mflatChart.slide(0, 0, fAdjustXMove, fAdjustYMove);
                    }
                } else    {
                    mflatChart.slide(mfOldX, mfOldY, newX, newY);
                }
                 */
                mflatChart.slide(mfOldX, mfOldY, newX, newY);
                e.getComponent().repaint();
            }
            mfOldX = newX;
            mfOldY = newY;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentResized(ComponentEvent e) {
            mflatChart.mdMediumSize = Math.min(e.getComponent().getWidth(), e.getComponent().getHeight()) / 10;    // medium size should be at least 1/10 of shorter edge
            mflatChart.resetAllSizesBasedOnMedium();
            mflatChart.resize(e.getComponent().getWidth(), e.getComponent().getHeight(), mflatChart.mdWidth, mflatChart.mdHeight);
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentShown(ComponentEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // Do nothing.
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
