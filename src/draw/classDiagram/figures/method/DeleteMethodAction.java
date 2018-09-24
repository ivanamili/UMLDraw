/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.method;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Korisnik
 */
public class DeleteMethodAction extends AbstractAction{

    private MethodFigure callingFigure;
    
    public DeleteMethodAction(MethodFigure figure){
        super("Delete method");
        this.callingFigure=figure;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        callingFigure.getParent().DeleteMethod(callingFigure);
    }
    
}
