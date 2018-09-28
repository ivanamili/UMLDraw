/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.ClassDiagramVeza;
import businessLogic.UseCaseDiagrams.UseCaseVeza;
import draw.commonClasses.AbstractDiagramConnectionFigure;
import draw.commonClasses.IConnectable;
import draw.commonClasses.IDataFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import enumerations.ClassConnTypeEnum;
import org.jhotdraw.draw.ArrowTip;
import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Korisnik
 */
public class GeneralisationConnectionFigure extends AbstractDiagramConnectionFigure implements IDataFigure {

    private ClassDiagramVeza veza;
    
    public GeneralisationConnectionFigure()
    {
        super();
        this.veza= new ClassDiagramVeza();
        initFigure();
    }

    public GeneralisationConnectionFigure(ClassDiagramVeza bussinesObject) {
        super();
        this.veza=bussinesObject;
        initFigure();
    }
    @Override
    public AbstractDiagramElement getDataObject() {
        return this.veza;
    }

    private void initFigure() {
        this.veza.setTip(ClassConnTypeEnum.GENERALISATION);
        
        ArrowTip tip= new ArrowTip(0.35, 12, 11.3,false,true,true);
        END_DECORATION.set(this, tip);
    }
    
     @Override
    public boolean canConnect(Figure start, Figure end){
        
        //da se izbegne ciklicna veza
        //MOZDA NE RADI KAKO TREBA, U PITANJU SU REFERENCE KOJE POREDI!TESTIRAJ
        if(start==end)
            return false;
        
        //veza je izmedju dve klase
        if(start instanceof ClassFigure && end instanceof ClassFigure)
        {
            IConnectable st=(IConnectable) start;
            IConnectable en=(IConnectable) end;
            
            if(st.getConnectedFigures().contains(end))
                return false;
            //da se spreci cirkularno nasledjivanje
            else if(en.getConnectedFigures().contains(start))
                return false;
            else
                return true;
        }
        return false;
    }
    
     public boolean canConnect(Figure start) {
        return (start instanceof ClassFigure );
    }
     
     protected void handleConnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.addConnectedFigure(en);
        en.addConnectedFigure(st);
        
        //dodaju se odgovarajuci id-jevi
        this.veza.setOdKoga(st.getDataObjectID());
        this.veza.setDoKoga(en.getDataObjectID());
    }
     
     protected void handleDisconnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.removeConnectedFigure(en);
        en.removeConnectedFigure(st);
    }
     
    @Override
    public GeneralisationConnectionFigure clone(){
         
        GeneralisationConnectionFigure that= (GeneralisationConnectionFigure) super.clone();
        that.veza= (this.veza==null)? null : (ClassDiagramVeza) this.veza.clone();
        return that;
    }
    
}
