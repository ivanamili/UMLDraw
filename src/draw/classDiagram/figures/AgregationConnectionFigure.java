/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.ClassDiagramVeza;
import draw.classDiagram.auxiliaryClasses.RombLineDecor;
import draw.commonClasses.AbstractDiagramConnectionFigure;
import draw.commonClasses.IConnectable;
import draw.commonClasses.IDataFigure;
import enumerations.ClassConnTypeEnum;
import java.awt.Color;
import org.jhotdraw.draw.ArrowTip;
import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Korisnik
 */
public class AgregationConnectionFigure extends AbstractDiagramConnectionFigure implements IDataFigure {
    
    private ClassDiagramVeza veza;
    
    public AgregationConnectionFigure()
    {
        super();
        this.veza= new ClassDiagramVeza();
        initFigure();
    }

    public AgregationConnectionFigure(ClassDiagramVeza bussinesObject) {
        super();
        this.veza=bussinesObject;
        initFigure();
    }
    @Override
    public AbstractDiagramElement getDataObject() {
        return this.veza;
    }

    private void initFigure() {
        this.veza.setTip(ClassConnTypeEnum.AGREGATION);
        
       
       RombLineDecor romb= new RombLineDecor(10,false,true);
       FILL_COLOR.set(this, Color.white);
        START_DECORATION.set(this, romb);
    }
    
     @Override
    public boolean canConnect(Figure start, Figure end){
        
        //da se izbegne ciklicna veza
        //MOZDA NE RADI KAKO TREBA, U PITANJU SU REFERENCE KOJE POREDI!TESTIRAJ
        if(start==end)
            return false;
        
        //veza je izmedju klase i interfejsa
        if(start instanceof ClassFigure && (end instanceof ClassFigure || end instanceof InterfaceFigure))
        {
//            IConnectable st=(IConnectable) start;
//            IConnectable en=(IConnectable) end;
//            if(st.getConnectedFigures().contains(end))
//                return false;
//            else
//                return true;
            
            return true;
        }
        return false;
    }
    
    //ovo se zove svaki put kad connection tool udje u neku figuru
    //ne samo za pocetku figuru
     public boolean canConnect(Figure start) {
        return (start instanceof ClassFigure || start instanceof InterfaceFigure );
    }
     
     protected void handleConnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        //st.addConnectedFigure(en);
        //en.addConnectedFigure(st);
        
        //dodaju se odgovarajuci id-jevi
        this.veza.setOdKoga(st.getDataObjectID());
        this.veza.setDoKoga(en.getDataObjectID());
    }
     
     protected void handleDisconnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        //st.removeConnectedFigure(en);
        //en.removeConnectedFigure(st);
    }
     
    @Override
    public AgregationConnectionFigure clone(){
         
        AgregationConnectionFigure that= (AgregationConnectionFigure) super.clone();
        that.veza= (this.veza==null)? null : (ClassDiagramVeza) this.veza.clone();
        return that;
    }
    
}
