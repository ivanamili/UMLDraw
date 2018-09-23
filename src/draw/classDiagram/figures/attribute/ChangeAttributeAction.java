/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.attribute;

import businessLogic.ClassDiagrams.Atribut;
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
public class ChangeAttributeAction extends AbstractAction{

    private AttributeFigure callingFigure;
    
    public ChangeAttributeAction(AttributeFigure figure)
    {
        super("Edit attribute");
        this.callingFigure=figure;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //kreirati input komponente za JOptionPane
        JTextField attrIme= new JTextField();
        attrIme.setText(this.callingFigure.getAtribut().getNaziv());
        
        JTextField attrTip= new JTextField();
        attrTip.setText(this.callingFigure.getAtribut().getTip());
        
        JComboBox attrVidljivost = new JComboBox(VisibilityTypeEnum.values());
        
        JCheckBox attrIsStatic = new JCheckBox("is Static");
        attrIsStatic.setSelected(this.callingFigure.getAtribut().getIsStatic());
        
        final JComponent[] inputs = new JComponent[] {
            new JLabel("Name"),
            attrIme,
            new JLabel("Tip"),
            attrTip,
            new JLabel("Visibility"),
            attrVidljivost,
            attrIsStatic
        };
        
        int result = JOptionPane.showConfirmDialog(null, inputs, "Add new class attribute", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            //business objekat
            Atribut noviAttribut= new Atribut();
            noviAttribut.setNaziv(attrIme.getText());
            noviAttribut.setTip(attrTip.getText());
            noviAttribut.setVidljivost((VisibilityTypeEnum)attrVidljivost.getSelectedItem());
            noviAttribut.setIsStatic(attrIsStatic.isSelected());
            
            //update figure objekat
            this.callingFigure.updateFigure(noviAttribut);
        }
    
    }
}
