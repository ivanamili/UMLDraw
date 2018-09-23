/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.UseCaseDiagrams.UseCase;
import draw.commonClasses.IDataFigure;
import draw.usecase.auxiliaryClasses.UseCaseNameTextFigure;
import draw.usecase.auxiliaryClasses.UseCaseVerticalLayouter;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import org.jhotdraw.draw.AbstractFigureListener;
import org.jhotdraw.draw.BoxHandleKit;
import static org.jhotdraw.draw.CompositeFigure.LAYOUT_INSETS;
import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.VerticalLayouter;
import org.jhotdraw.geom.Insets2DDouble;
import org.jhotdraw.samples.pert.figures.TaskFigure;
import draw.commonClasses.AbstractDiagramElementFigure;

/**
 *
 * @author Korisnik
 */
public class UseCaseFigure extends AbstractDiagramElementFigure{

    @Override
    //vraca useCase
    public AbstractDiagramElement getDataObject() {
        return this.useCase;
    }
    
    //ADAPTERI KOJI MENJAJU UseCase objekat kako se menja ime figure
    //kada se promeni polozaj elipsa se sama promeni jer i useCase i figura IMAJU REFERENCE NA ISTU ELIPSU!
     private static class UseCaseNameAdapter extends AbstractFigureListener {
        private UseCaseFigure target;
        public UseCaseNameAdapter(UseCaseFigure target) {
            this.target = target;
        }
        public void figureAttributeChanged(FigureEvent e) {
            if(e.getNewValue() instanceof String)
                target.useCase.setNaziv((String)e.getNewValue());
            // We could fire a property change event here, in case
            // some other object would like to observe us.
            //target.firePropertyChange("name", e.getOldValue(), e.getNewValue());
        }
    }
    private UseCase useCase;
    
    //kada se kreira novi use case koriscenjem tool-a
    public UseCaseFigure()
    {
        super(new EllipseFigure());
        useCase=new UseCase();
        useCase.setNaziv("New UseCase");
        useCase.setElipsa((EllipseFigure)this.getPresentationFigure());
        initFigure();
    }
    
    //kada se kreira use case figura na osnovu vec postojeceg use case objekta
    public UseCaseFigure(UseCase uc)
    {
        super(uc.getElipsa());
        this.useCase=uc;
        initFigure();
    }
    
    private void initFigure()
    {
        //jhotdraw klasa
        setLayouter(new UseCaseVerticalLayouter());
        
        //prikazuje naziv useCase-a
        TextFigure ucName= new UseCaseNameTextFigure();
        ucName.setText(useCase.getNaziv());
        
        //razmak od elipse
        Insets2DDouble insets = new Insets2DDouble(8,8,8,8);
        LAYOUT_INSETS.set(ucName, insets);
        add(ucName);
        
        //menja name u useCase-u kada se promeni ime figure
        ucName.addFigureListener(new UseCaseNameAdapter(this));
        
        
        
    }
    
    //koristi se u clone funkciji
     private TextFigure getNameFigure() {
        return (TextFigure) getChild(0);
    }
    
     //tool koristi ovo da kreira prototip. treba DODATI ADAPTERE!
     public UseCaseFigure clone() {
        UseCaseFigure that = (UseCaseFigure) super.clone();
        that.useCase= (this.useCase==null)? null : (UseCase)this.useCase.clone();
        that.useCase.setElipsa((EllipseFigure)that.getPresentationFigure());
        that.getNameFigure().addFigureListener(new UseCaseNameAdapter(that));
        
        return that;
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
    
    
}
