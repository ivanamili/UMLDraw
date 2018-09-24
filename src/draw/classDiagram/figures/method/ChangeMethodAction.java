/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.method;

import businessLogic.ClassDiagrams.Metod;
import enumerations.VisibilityTypeEnum;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Korisnik
 */
public class ChangeMethodAction extends AbstractAction {

    private MethodFigure callingFigure;
    
    public ChangeMethodAction(MethodFigure figure){
        super("Edit method");
        this.callingFigure=figure;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        JTextField metIme= new JTextField();
        metIme.setText(callingFigure.getMethod().getNaziv());
        
        JTextField metReturnType= new JTextField();
        metReturnType.setText(callingFigure.getMethod().getPovratnaVrednost());
        
        JComboBox metVidljivost = new JComboBox(VisibilityTypeEnum.values());
        metVidljivost.setSelectedItem(callingFigure.getMethod().getVidljivost());
        
        JCheckBox metIsStatic = new JCheckBox("is Static");
        metIsStatic.setSelected(callingFigure.getMethod().getIsStatic());
        
        JCheckBox metIsAbstract = new JCheckBox("is Abstract");
        metIsAbstract.setSelected(callingFigure.getMethod().getIsAbstract());
        
        final JComponent[] inputs = new JComponent[] {
            new JLabel("Name"),
            metIme,
            new JLabel("Return type"),
            metReturnType,
            new JLabel("Visibility"),
            metVidljivost,
            metIsStatic,
            metIsAbstract
        };
        
        int result = JOptionPane.showConfirmDialog(null, inputs, "Add new class attribute", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            
            Metod noviMetod= new Metod();
            noviMetod.setNaziv(metIme.getText());
            noviMetod.setPovratnaVrednost(metReturnType.getText());
            noviMetod.setVidljivost((VisibilityTypeEnum)metVidljivost.getSelectedItem());;
            noviMetod.setIsStatic(metIsStatic.isSelected());
            noviMetod.setIsAbstract(metIsAbstract.isSelected());
            
            this.callingFigure.updateFigure(noviMetod);
        }
    }
    
}
