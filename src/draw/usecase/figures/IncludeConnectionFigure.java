/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.UseCaseDiagrams.UseCaseVeza;
import draw.commonClasses.AbstractDiagramConnectionFigure;
import draw.commonClasses.HorizontalLayouter;
import draw.commonClasses.IConnectable;
import draw.commonClasses.IDataFigure;
import draw.commonClasses.LabelFigure;
import draw.commonClasses.LabeledLineConnectionFigure;
import draw.usecase.auxiliaryClasses.AktorNameTextFigure;
import enumerations.UseCaseConnType;
import org.jhotdraw.draw.AbstractLineDecoration;
import org.jhotdraw.draw.ArrowTip;
import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.CompositeFigure.LAYOUT_INSETS;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.LineDecoration;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.Insets2DDouble;

/**
 *
 * @author Korisnik
 */
public class IncludeConnectionFigure extends AbstractDiagramConnectionFigure implements IDataFigure{

    private UseCaseVeza useCaseVeza;  
    
    public IncludeConnectionFigure(){
        super();
        this.useCaseVeza= new UseCaseVeza();
        initFigure();
    }
    
    private void initFigure(){
        this.useCaseVeza.setTipVeze(UseCaseConnType.INCLUDE);
        
        this.setLayouter(new VerticalLayouter());
        LabelFigure label= new LabelFigure("<<include>>");
        double[] dashes={4d, 4d};
        STROKE_DASHES.set(this, dashes);
        Insets2DDouble insets = new Insets2DDouble(15,15,15,15);
        LAYOUT_INSETS.set(label, insets);
        END_DECORATION.set(this, new ArrowTip());
        
        //this.add(label);
        

    }
    //ne dozvoljava kreiranje visestrukih veza izmedju ista dva use case objekta
    //ne dozvoljava ciklicnu vezu use case-a sa samim sobom 
    @Override
    public boolean canConnect(Figure start, Figure end){
        
        //da se izbegne ciklicna veza
        //MOZDA NE RADI KAKO TREBA, U PITANJU SU REFERENCE KOJE POREDI!TESTIRAJ
        if(start==end)
            return false;
        
        //veza je izmedju dva usecase-a
        if(start instanceof UseCaseFigure && end instanceof UseCaseFigure)
        {
            IConnectable st=(IConnectable) start;
            //da se spreci da se isti usecase vise puta povezuje sa istim useCase-om
            if(st.getConnectedFigures().contains(end))
                return false;
            else
                return true;
        }
        return false;
    }
    
    //za rani feedback 
    //interaguje samo sa usecase-ovima
    public boolean canConnect(Figure start) {
        return (start instanceof UseCaseFigure );
    }
    
    //oba useCase-a dobijaju referencu na onaj drugi u skupu povezanih figura iz razloga
    //sto nema mnogo smisla da dva useCase-a budu ciklicno povezana
    protected void handleConnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.addConnectedFigure(en);
        en.addConnectedFigure(st);
        
        //dodaju se odgovarajuci id-jevi
        this.useCaseVeza.setOdKoga(st.getDataObjectID());
        this.useCaseVeza.setDoKoga(en.getDataObjectID());
    }
    
    protected void handleDisconnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.removeConnectedFigure(en);
        en.removeConnectedFigure(st);
    }
    
    public IncludeConnectionFigure clone() {
        IncludeConnectionFigure that = (IncludeConnectionFigure) super.clone();
        that.useCaseVeza= (this.useCaseVeza==null)? null : (UseCaseVeza)this.useCaseVeza.clone();
        return that;
    }

    @Override
    public AbstractDiagramElement getDataObject() {
        return this.useCaseVeza;
    }
    
}
