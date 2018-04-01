/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.auxiliaryClasses;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.geom.Dimension2DDouble;

/**
 *
 * @author Korisnik
 */
public class AktorNameTextFigure extends TextFigure {
    
    //omogucava da se naziv aktora centrira ispod coveculjka
    @Override
    public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
        double width=Math.abs(lead.x-anchor.x);
        double height=Math.abs(lead.y-anchor.y);
        Rectangle2D textbounds=this.getTextLayout().getBounds();
        double x= (width-textbounds.getWidth())/2.0;
        double y=(height-textbounds.getHeight())/2.0 -textbounds.getHeight();
        origin = new Point2D.Double(anchor.x+x, anchor.y);
    }
    
    //LAYOUTER koristi getPreffered size kada odredjue kolika ce da budu deca
    //i composite figura u celosti
    //time sto je stavljeno da je withd=60 se postize da se coveculjak ne resijzuje kada je
    //naziv aktora dugacak.
    //naslov ostaje cenntriran
    @Override
    public Dimension2DDouble getPreferredSize()
    {
        Rectangle2D.Double r = getBounds();
               
        return new Dimension2DDouble (60,r.height);
    }
    
    
    
}
