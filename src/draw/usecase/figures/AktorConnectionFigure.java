/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.UseCaseDiagrams.AktorVeza;
import draw.commonClasses.AbstractDiagramConnectionFigure;
import draw.commonClasses.IConnectable;
import draw.commonClasses.IDataFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.LineConnectionFigure;
import org.jhotdraw.samples.pert.figures.TaskFigure;

/**
 *
 * @author Korisnik
 */
public class AktorConnectionFigure extends AbstractDiagramConnectionFigure implements IDataFigure{
    
    private AktorVeza aktorVeza;
    
    public AktorConnectionFigure()
    {
        super();
        this.aktorVeza=new AktorVeza();
    }

    public AktorConnectionFigure(AktorVeza aktorVeza) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean canConnect(Figure start, Figure end){
        
        //aktor veza moze ici samo od aktora ka useCase-u
        if(start instanceof AktorFigure && end instanceof UseCaseFigure)
        {
            IConnectable st=(IConnectable) start;
            //da se spreci da se isti aktor vise puta povezuje sa istim useCase-om
            if(st.getConnectedFigures().contains(end))
                return false;
            else
                return true;
        }
        return false;
    }
    
    //za rani feedback 
    
    public boolean canConnect(Figure start) {
        return (start instanceof UseCaseFigure || start instanceof AktorFigure);
    }
    //potrebno je da se start figuri (AktorFigure) doda use case sa kojim je upravo povezan
    //nije potrebno dodavati aktora useCaase objektu jer iovako ova veza ne moze da se povuce
    //od use case a ka aktoru
    protected void handleConnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.addConnectedFigure(en);
        
        //dodaju se odgovarajuci id-jevi
        this.aktorVeza.setAktor(st.getDataObjectID());
        this.aktorVeza.setUseCase(en.getDataObjectID());
    }
    
    protected void handleDisconnect(Figure start, Figure end) {
        IConnectable st=(IConnectable) start;
        IConnectable en=(IConnectable) end;
        st.removeConnectedFigure(en);
    }
    
    public AktorConnectionFigure clone() {
        AktorConnectionFigure that = (AktorConnectionFigure) super.clone();
        that.aktorVeza= (this.aktorVeza==null)? null : (AktorVeza)this.aktorVeza.clone();
        return that;
    }

    @Override
    public AbstractDiagramElement getDataObject() {
        return this.aktorVeza;
    }
    
}
