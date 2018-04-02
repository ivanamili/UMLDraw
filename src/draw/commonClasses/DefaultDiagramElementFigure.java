/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import java.util.HashSet;
import java.util.Set;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GraphicalCompositeFigure;

//OSNOVNA IMPLEMENTACIJA ZA ELEMENTE DIJAGRAMA KOJI SU PREDSTAVLJENI KAO KOMPOZICIJE i KOJI MOGU DA BUDU POVEZIVANI
//KONEKCIJAMA
//implementira IConnectable interfejs koji omogucava provere pri povezivanju komponenti konekcijama
//implementira IDataFigure koji omogucava vracanje dataObjekta iz figure ali ne OVERRIDUJE METODU. to treba podklase da urade
public abstract class DefaultDiagramElementFigure extends GraphicalCompositeFigure implements IConnectable,IDataFigure{
    
    //omogucava da se proveri pri kreiranju veze da li je objekat vec povezan sa datim objektom
    //da bi mogle da se izbegnu kreiranja visestrukih veza izmedju istih objekata gde to nije pozeljno
    protected HashSet<IConnectable> connectedFigures;
    
    public DefaultDiagramElementFigure()
    {
        super();
        connectedFigures=new HashSet<IConnectable>();
    }
    
    public DefaultDiagramElementFigure(Figure presentationFigure)
    {
        super(presentationFigure);
        connectedFigures=new HashSet<IConnectable>();
    }
    
    @Override
    public void addConnectedFigure(IConnectable figure) {
        connectedFigures.add(figure);
    }

    @Override
    public void removeConnectedFigure(IConnectable figure) {
        connectedFigures.remove(figure);
    }

    @Override
    public Set<IConnectable> getConnectedFigures() {
        return this.connectedFigures;
    }
    
    //ovako implementiran radice za svaku figuru  koja implementira IDataFigure
    @Override
    public int getDataObjectID(){
        IDataFigure fig= (IDataFigure) this;
        return fig.getDataObject().getElemId();
    }
    
    @Override
    public GraphicalCompositeFigure clone(){
        DefaultDiagramElementFigure that= (DefaultDiagramElementFigure) super.clone();
        that.connectedFigures=new HashSet<IConnectable>();
        return that;
    }
              
    
}
