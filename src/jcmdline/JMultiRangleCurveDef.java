/*
 * MFP project, JMultiRangleCurveDef.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JMultiRangleCurveDef extends JMultiXYCurveDef {

    public JMultiRangleCurveDef(JMultiXYChartDef chartDef) {
        super(chartDef);
        lblXEqual.setText("R =");
        lblYEqual.setText("\u03B8 =");
    }

}
