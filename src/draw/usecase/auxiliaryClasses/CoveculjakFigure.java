/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.auxiliaryClasses;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.UseCaseDiagrams.Aktor;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.Dimension2DDouble;

/**
 *
 * @author Korisnik
 */
public class CoveculjakFigure extends GroupFigure {
    
    public CoveculjakFigure()
    {
        super();
        init();
    }
    
    private void init(){
        EllipseFigure head= new EllipseFigure(-15,-15,30,30);
        add(head);
        LineFigure body= new LineFigure();
        body.basicSetBounds(new Point2D.Double(0.0,15.0), new Point2D.Double(0.0,50.0));
        add(body);
        LineFigure leftLeg= new LineFigure();
        leftLeg.basicSetBounds(new Point2D.Double(-15.0,60.0), new Point2D.Double(0.0,50.0));
        add(leftLeg);
        LineFigure rightLeg= new LineFigure();
        rightLeg.basicSetBounds(new Point2D.Double(15.0,60.0), new Point2D.Double(0.0,50.0));
        add(rightLeg);
        LineFigure leftArm= new LineFigure();
        leftArm.basicSetBounds(new Point2D.Double(-15.0,20.0), new Point2D.Double(0.0,30.0));
        add(leftArm);
        LineFigure rightArm= new LineFigure();
        rightArm.basicSetBounds(new Point2D.Double(15.0,20.0), new Point2D.Double(0.0,30.0));
        add(rightArm);
    }
    
    @Override
    public Dimension2DDouble getPreferredSize()
    {
        Rectangle2D.Double r = getBounds();
        double w,h;
        w=r.width;
        h=r.height;
        if(w<30)
            w=30;
        if(h<110)
            h=110;
        
        return new Dimension2DDouble (w,h);
    }
}
