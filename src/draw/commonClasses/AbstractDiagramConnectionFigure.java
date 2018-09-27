/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import org.jhotdraw.draw.Figure;

/**
 *
 * @author Korisnik
 */
public abstract class AbstractDiagramConnectionFigure extends LabeledLineConnectionFigure implements IDataFigure,IRemovableConnection{
    
    @Override
    public void fireHandleDisconnect(){
        this.handleDisconnect(this.getStartFigure(), this.getEndFigure());
    }
    
    public void setConnectionEndpoints(Figure startFigure, Figure endFigure)
    {
         this.setStartConnector(startFigure.findConnector(null, this));
         this.setEndConnector(endFigure.findConnector(null, this));
         
         this.setBounds(this.getStartConnector().getAnchor(), this.getEndConnector().getAnchor());
         this.updateConnection();
    }
}
