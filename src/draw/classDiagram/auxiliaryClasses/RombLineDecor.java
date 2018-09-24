/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.auxiliaryClasses;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import org.jhotdraw.draw.AbstractLineDecoration;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Korisnik
 */
public class RombLineDecor extends AbstractLineDecoration {
    
    private int step;
    public RombLineDecor(int step,boolean isFilled,boolean isStroked){
        super(isFilled,isStroked,true );
        this.step=step;
    }
    public RombLineDecor(boolean isFilled, boolean isStroked, boolean isSolid){
        super(isFilled, isStroked, isSolid);
    }

    @Override
    protected GeneralPath getDecoratorPath(Figure f) {
         double offset = (isStroked()) ? 1 : 0;
        
        double startOffset= (step*Math.sqrt(2))/2;
        
        GeneralPath path = new GeneralPath();
        
        path.moveTo(0,0);
        path.lineTo(-step, step);
        path.lineTo(-2*step,0);
        path.lineTo(-step,-step);
        path.closePath();
        
        AffineTransform transform = new AffineTransform();
        transform.translate(startOffset+2, startOffset+4);
        
        path.transform(transform);
        return path;
    }

    @Override
    protected double getDecoratorPathRadius(Figure f) {
        double offset = (isStroked()) ? 0.5 : -0.1;
        return 2*step;
    }
    
}
