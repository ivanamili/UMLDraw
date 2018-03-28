/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.AbstractClassHierarchy.*;
import businessLogic.CommonClasses.Crtez;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DrawingEvent;
import org.jhotdraw.draw.DrawingListener;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;

/**
 *
 * @author Korisnik
 */
public class UmlDrawing extends DefaultDrawing implements DrawingListener{
    
    private Crtez UmlCrtez;

    public UmlDrawing()
    {
        super();
        UmlCrtez=new Crtez();
        this.addDrawingListener(this);
    }
   
    public Crtez getUmlCrtez() {
        return UmlCrtez;
    }

    public void setUmlCrtez(Crtez useCaseDrawing) {
        this.UmlCrtez = useCaseDrawing;
    }
    
    //OVERRIDE metode
    
    @Override
    public void basicAdd(int index, Figure figure) {
        super.basicAdd(index,figure);
        AbstractDiagramElement elemToAdd=((IDataFigure)figure).getDataObject();
        this.UmlCrtez.dodaj(elemToAdd);
    }
    
    @Override
    public void basicRemove(Figure figure) {
        super.basicRemove(figure);
        AbstractDiagramElement elemToRemove=((IDataFigure)figure).getDataObject();
        this.UmlCrtez.obrisi(elemToRemove);
    }
    

    //METODE IZ DrawingListener interfejsa
    @Override
    public void areaInvalidated(DrawingEvent de) {
        //NE ZNAM DA LI CE DA TREBA, NEKA STOJI
    }

    @Override
    public void figureAdded(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        System.out.println("Dodat element: ID "+elem.getElemId());
        //dodati slanje poruke o ADD NEW FIGURE
    }

    @Override
    public void figureRemoved(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        System.out.println("Uklonjen element: ID "+elem.getElemId());
        //dodati slanje poruke za brisanje REMOVE FIGURE
    }
    
    //metode iz figure listener
    @Override
    public void figureChanged(FigureEvent e){
        
        Figure fig= e.getFigure();
        //mora provera, jer jhotdraw baca figureChanged i za figure decu nase figure, a nama treba
        //samo nasa figura da je uhvatimo i prosledimo dalje
        if(fig instanceof IDataFigure)
        {
            //posalji poruku ostalima da je na datoj figuri doslo do promene
        }
        
        this.UmlCrtez.getID();
    }
    
    
}
