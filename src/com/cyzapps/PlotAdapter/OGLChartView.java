/*
 * MFP project, OGLChartView.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.PlotAdapter;

import com.jogamp.opengl.util.Animator;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;

/**
 * OGLChartView.java <BR>
 * author: Yuan Cui
 * <P>
 *
 * This file create an open gl view to hold the chart.
 */
public class OGLChartView extends Frame {

    protected OGLChart moglChart = null;

    /**
     * The view bounds.
     */
    protected Canvas mcanvas = null;

    public OGLChartView(String strChartTitle) {
        super(strChartTitle);
    }

    public OGLChartView(String strChartTitle, OGLChart oglOrthoChart) {
        super(strChartTitle);
        moglChart = oglOrthoChart;
        moglChart.setGraphContainer(this);
    }

    public Canvas getCanvas() {
        return mcanvas;
    }

    public static void launchChart(OGLChart oglChart) {

        final OGLChartView oglChartView = new OGLChartView(oglChart.mstrChartTitle, oglChart);
        OGLChartEventHandler oglChartEventHandler = new OGLChartEventHandler(oglChart);
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(oglChartEventHandler);
        oglChartView.add(canvas);
        oglChartView.mcanvas = canvas;
        oglChartView.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        oglChartView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        //System.exit(0);
                        oglChartView.dispose();
                    }
                }).start();
            }
        });
        // Center frame
        oglChartView.setLocationRelativeTo(null);
        oglChartView.setVisible(true);
        animator.start();
    }

    static public class OGLChartEventHandler implements GLEventListener {

        protected OGLChart moglChart = null;

        public OGLChartEventHandler(OGLChart oglChart) {
            moglChart = oglChart;
        }

        @Override
        public void init(GLAutoDrawable drawable) {
            moglChart.init(drawable);
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            moglChart.reshape(drawable, x, y, width, height);
        }

        @Override
        public void display(GLAutoDrawable drawable) {
            moglChart.display(drawable);
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
            // need further test to verify it works.
        }
    }
}
