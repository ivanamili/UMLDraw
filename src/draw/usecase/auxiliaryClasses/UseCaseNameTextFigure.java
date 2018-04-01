/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.auxiliaryClasses;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.TextFigure;

/**
 *
 * @author Korisnik
 */
public class UseCaseNameTextFigure extends TextFigure{
    
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
        double width=Math.abs(lead.x-anchor.x);
        double height=Math.abs(lead.y-anchor.y);
        Rectangle2D textbounds=this.getTextLayout().getBounds();
        double x= (width-textbounds.getWidth())/2.0;
        double y=(height-textbounds.getHeight())/2.0 -textbounds.getHeight();
        origin = new Point2D.Double(anchor.x+x, anchor.y+y);
    }
    
}
