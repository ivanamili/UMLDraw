/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.UseCaseDiagrams.Aktor;
import draw.usecase.auxiliaryClasses.AktorNameTextFigure;
import draw.usecase.auxiliaryClasses.CoveculjakFigure;
import draw.commonClasses.IDataFigure;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import org.jhotdraw.draw.AbstractFigureListener;
import org.jhotdraw.draw.AttributeKey;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import org.jhotdraw.draw.AttributedFigure;
import org.jhotdraw.draw.BoxHandleKit;
import static org.jhotdraw.draw.CompositeFigure.LAYOUT_INSETS;
import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.VerticalLayouter;
import org.jhotdraw.geom.Insets2DDouble;
import draw.commonClasses.AbstractDiagramElementFigure;

/**
 *
 * @author Korisnik
 */
public class AktorFigure extends AbstractDiagramElementFigure {
    
    private Aktor aktor;

    
    
     private static class AktorNameAdapter extends AbstractFigureListener {
        private AktorFigure target;
        public AktorNameAdapter(AktorFigure target) {
            this.target = target;
        }
        public void figureAttributeChanged(FigureEvent e) {
            if(e.getNewValue() instanceof String)
                target.aktor.setNaziv((String)e.getNewValue());
            // We could fire a property change event here, in case
            // some other object would like to observe us.
            //target.firePropertyChange("name", e.getOldValue(), e.getNewValue());
        }
     }

    @Override
    public AbstractDiagramElement getDataObject() {
        return this.aktor;
    }
    
    public AktorFigure()
    {
        super(new RectangleFigure());
        aktor= new Aktor();
        aktor.setNaziv("New Aktor");
        aktor.setOkvir((RectangleFigure)this.getPresentationFigure());
        initFigure();        
    }
    
    public AktorFigure(Aktor aktor) {
        super(aktor.getOkvir());
        this.aktor=aktor;
        initFigure();
    }
    
    public void initFigure()
    {
        STROKE_COLOR.set(this.getPresentationFigure(), null);
        ((RectangleFigure)this.getPresentationFigure()).setAttributeEnabled(STROKE_COLOR, false);
        setLayouter(new VerticalLayouter());
        Insets2DDouble insets = new Insets2DDouble(8,8,8,8);
        LAYOUT_INSETS.set(this, insets);
        applyAttributes(getPresentationFigure());
        CoveculjakFigure cicaglisa=new CoveculjakFigure();
        add(cicaglisa);
        
        AktorNameTextFigure name=new AktorNameTextFigure();
        name.setText(aktor.getNaziv());
        Insets2DDouble insets2 = new Insets2DDouble(8,0,8,0);
        LAYOUT_INSETS.set(name, insets2);
        //osluskuje kada se promeni tekst i onda menja naziv u objektu Aktor
        name.addFigureListener(new AktorNameAdapter(this));
        add(name);
    }
    
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        if (detailLevel == 0) {
            BoxHandleKit.addBoxHandles(this, handles);
        }
        return handles;
    }
     
     public Rectangle2D.Double getBounds() {
        Rectangle2D.Double bounds = (Rectangle2D.Double) this.getPresentationFigure().getBounds().clone();
        return bounds;
    }
     
     private TextFigure getNameFigure() {
        return (TextFigure) getChild(1);
    }
     
     public AktorFigure clone() {
        AktorFigure that = (AktorFigure) super.clone();
        that.aktor= (this.aktor==null)? null : (Aktor)this.aktor.clone();
        that.aktor.setOkvir((RectangleFigure)that.getPresentationFigure());
        that.getNameFigure().addFigureListener(new AktorFigure.AktorNameAdapter(that));      
        return that;
    }
     
     private void applyAttributes(Figure f) {
        Map<AttributeKey,Object> attr = ((AttributedFigure) getPresentationFigure()).getAttributes();
        for (Map.Entry<AttributeKey, Object> entry : attr.entrySet()) {
            f.setAttribute(entry.getKey(), entry.getValue());
        }
    }
    
}
