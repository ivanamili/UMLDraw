/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.AbstractLayouter;
import static org.jhotdraw.draw.AttributeKeys.LAYOUT_INSETS;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.VerticalLayouter;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Insets2DDouble;

/**
 *
 * @author Korisnik
 */

//omogucava da se resizuje useCase elipsa i da tekst uvek bude na sredini
public class UseCaseVerticalLayouter extends AbstractLayouter{
   
    public Rectangle2D.Double calculateLayout(CompositeFigure layoutable, Point2D.Double anchor, Point2D.Double lead) {
        Insets2DDouble layoutInsets = LAYOUT_INSETS.get(layoutable);
        if (layoutInsets == null) layoutInsets = new Insets2DDouble(0,0,0,0);
        
        Rectangle2D.Double layoutBounds = new Rectangle2D.Double(anchor.x,anchor.y,0,0);
        for (Figure child : layoutable.getChildren()) {
            if (child.isVisible()) {
                Dimension2DDouble preferredSize = child.getPreferredSize();
                Insets2DDouble ins = getInsets(child);
                layoutBounds.width = Math.max(layoutBounds.width, preferredSize.width + ins.left + ins.right);
                layoutBounds.height += preferredSize.height + ins.top + ins.bottom;
                }
        }
        layoutBounds.width += layoutInsets.left + layoutInsets.right;
        layoutBounds.height += layoutInsets.top + layoutInsets.bottom;
        
        Rectangle2D.Double borderBounds = layoutable.getBounds();
        layoutBounds.width=Math.max(layoutBounds.width,borderBounds.width);
        layoutBounds.height=Math.max(layoutBounds.height,borderBounds.height);
        
        return layoutBounds;
    }
    
     public Rectangle2D.Double layout(CompositeFigure layoutable, Point2D.Double anchor, Point2D.Double lead) {
        Insets2DDouble layoutInsets = LAYOUT_INSETS.get(layoutable);
        
        if (layoutInsets == null) layoutInsets = new Insets2DDouble(0,0,0,0);
        
        Rectangle2D.Double layoutBounds = calculateLayout(layoutable, anchor, lead);
        double y = layoutBounds.y + layoutInsets.top;
        for (Figure child : layoutable.getChildren()) {
            if (child.isVisible()) {
                Insets2DDouble insets = getInsets(child);
                double height = child.getPreferredSize().height;
                child.basicSetBounds(
                        new Point2D.Double(
                        layoutBounds.x + layoutInsets.left + insets.left,
                        y + insets.top
                        ),
                        new Point2D.Double(
                        layoutBounds.x + layoutBounds.width - layoutInsets.right - insets.right,
                        y + insets.top + layoutBounds.height
                        )
                        );
                y += height + insets.top + insets.bottom;
                }
        }
        return layoutBounds;
    }
    
}
