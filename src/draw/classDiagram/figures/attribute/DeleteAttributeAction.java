/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.attribute;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Korisnik
 */
public class DeleteAttributeAction extends AbstractAction{

    private AttributeFigure callingFigure;
    
    public DeleteAttributeAction(AttributeFigure figure){
        super("Delete Attribute");
        this.callingFigure=figure;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        callingFigure.getParent().DeleteAttribute(callingFigure);
    }
    
}
