/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.method;

import businessLogic.ClassDiagrams.Argument;
import businessLogic.ClassDiagrams.Metod;
import draw.classDiagram.auxiliaryClasses.IMethodContainer;
import draw.classDiagram.figures.ClassFigure;
import enumerations.VisibilityTypeEnum;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Korisnik
 */
public class AddMethodAction extends AbstractAction{

    private IMethodContainer callingFigure;
    
    public AddMethodAction(IMethodContainer figure){
        super("New method");
        this.callingFigure=figure;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         //kreirati input komponente za JOptionPane
        JTextField metIme= new JTextField();
        JTextField metReturnType= new JTextField();
        JComboBox metVidljivost = new JComboBox(VisibilityTypeEnum.values());
        JCheckBox metIsStatic = new JCheckBox("is Static");
        JCheckBox metIsAbstract = new JCheckBox("is Abstract");
        
         //lista argumenata sa modelom
        ArrayList<Argument> argList= new ArrayList();
        JList metArguments= new JList();
        DefaultListModel<Argument> model = new DefaultListModel();
        metArguments.setModel(model);
        
        JScrollPane listScroll= new JScrollPane(metArguments);
        listScroll.setPreferredSize(new Dimension(200,100));
        
        //argumenti
        JTextField argNaziv= new JTextField();
        JTextField argTip = new JTextField();
        JButton argAdd= new JButton("Add");
        argAdd.addActionListener((ActionEvent e1) -> {
            //your actions
            Argument noviArg= new Argument();
            noviArg.setNaziv(argNaziv.getText());
            noviArg.setTip(argTip.getText());
            model.addElement(noviArg);
            
            //ocisti polja
            argNaziv.setText("");
            argTip.setText("");
        });
        
        final JComponent[] inputs = new JComponent[] {
            new JLabel("Name"),
            metIme,
            new JLabel("Return type"),
            metReturnType,
            new JLabel("Visibility"),
            metVidljivost,
            metIsStatic,
            metIsAbstract,
            new JLabel("Arguments"),
            new JLabel(""),
            new JLabel("Name"),
            argNaziv,
            new JLabel("Type"),
            argTip,
            argAdd,
            new JLabel("Argument list"),
            listScroll
        };
        
        int result = JOptionPane.showConfirmDialog(null, inputs, "Add new class attribute", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            
            Metod noviMetod= new Metod();
            noviMetod.setNaziv(metIme.getText());
            noviMetod.setPovratnaVrednost(metReturnType.getText());
            noviMetod.setVidljivost((VisibilityTypeEnum)metVidljivost.getSelectedItem());;
            noviMetod.setIsStatic(metIsStatic.isSelected());
            noviMetod.setIsAbstract(metIsAbstract.isSelected());
            
            //dodavanje argumenata
            if(model.getSize()!=0)
            {
                for(int i=0; i< model.getSize();i++){
                    noviMetod.dodajArgument(model.get(i));
                }
            }
            
            MethodFigure newFigure= new MethodFigure(noviMetod);
            this.callingFigure.AddNewMethod(newFigure);
        }
    }
    
}
