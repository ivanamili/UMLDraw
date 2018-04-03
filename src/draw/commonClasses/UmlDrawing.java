/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.AbstractClassHierarchy.*;
import businessLogic.CommonClasses.Crtez;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DrawingEvent;
import org.jhotdraw.draw.DrawingListener;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.LineConnectionFigure;

/**
*Drawing koji sadrzi u sebi crtez.
*kada se doda nova figura, REFERENCA na business objekat pridruzen figuri se dodaje u crtez objekat
*ovo je neophodno kako bi, kada treba da posaljemo ceo crtez na server radi cuvanja u bazu, mi imamo
*sve objekte crteza koje bazi trebaju na jednom mestu.
*kako sam Crtez sadrzi reference business objekata, kako se oni budu menjali (a za njihovo azuriranje su zaduzene figure
*koje ih sadrze) promene ce automatsi biti vidljive i u crtezu
* */
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
        //da bi se uklonile reference u povezanim objektima
        // memory leak jer bi te reference na povezane objekte
        //ostajale i time sprecavale da garbage collector obrise objekte koji se inace vise nigde ne koriste
        //a i dodavanje veze izmedju istih objekata nakon predhodnog brisanja ne bi bio moguc
        if(figure instanceof IRemovableConnection)
            ((IRemovableConnection)figure).fireHandleDisconnect();;
            
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
    //PREVISE KOMPLIKOVANO, PREVISE PUTA SE BACA EVENT. MOGUCE RESENJE DA DEFINISEMO NAS TIP EXCEPTION-A I DA NJEGA BACAMO
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
