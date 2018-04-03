/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

/**
 *
 * @author Korisnik
 */
public abstract class AbstractDiagramConnectionFigure extends LabeledLineConnectionFigure implements IDataFigure,IRemovableConnection{
    
    @Override
    public void fireHandleDisconnect(){
        this.handleDisconnect(this.getStartFigure(), this.getEndFigure());
    }
}
