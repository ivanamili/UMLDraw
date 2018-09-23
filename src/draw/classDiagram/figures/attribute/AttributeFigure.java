/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures.attribute;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.Atribut;
import draw.commonClasses.IDataFigure;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINED;
import org.jhotdraw.draw.TextFigure;

/**
 *
 * @author Korisnik
 */
public class AttributeFigure extends TextFigure {

    private Atribut atribut;
    
    public Atribut getAtribut(){
        return this.atribut;
    }
    
    public AttributeFigure(Atribut atribut){
        this.atribut=atribut;
        initFigure();
    }
    
    private void initFigure()
    {
        String attrString="";
        //vidljivost
        switch(atribut.getVidljivost()){
            case PRIVATE:{
                attrString+="-";
                break;
            }
            case PUBLIC:{
                attrString+="+";
                break;
            }
            case PROTECTED:{
                attrString+="#";
                break;
            }
        }
        
        //ime
        attrString+=" "+this.atribut.getNaziv();
        
        //tip
        attrString+=" : "+this.atribut.getTip();
        
        this.setText(attrString);
        
        //ovo bi trebalo da podvuce ako je staticki atribut, ali je diskutabilno
        //da li ce da radi
        if(this.atribut.getIsStatic())
        {
            try
            {
                this.setAttribute(FONT_UNDERLINED, true);
                
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
            
    }
    
    
}
