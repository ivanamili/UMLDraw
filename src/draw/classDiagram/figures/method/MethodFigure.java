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
import draw.commonClasses.LabelFigure;
import java.util.ArrayList;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINED;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.TextFigure;

/**
 *
 * @author Korisnik
 */
public class MethodFigure extends LabelFigure{
    
    private Metod method;
    private IMethodContainer parent;
    
    public Metod getMethod(){
        return this.method;
    }
    
    public MethodFigure(Metod method){
        this.method=method;
        initFigure();
    }
    
    private void initFigure(){
        String text="";
        
        //vidljivost
         switch(method.getVidljivost()){
            case PRIVATE:{
                text+="-";
                break;
            }
            case PUBLIC:{
                text+="+";
                break;
            }
            case PROTECTED:{
                text+="#";
                break;
            }
        }
         
         //ime
         text+=" "+method.getNaziv();
         
         //argumenti
         text+=" (";
         ArrayList<Argument> argumenti;
         argumenti = method.getArgumenti();
         
         if(argumenti.size()!=0)
         {
            int i;
            for(i=0; i<argumenti.size()-1; i++){
                text+= argumenti.get(i).getNaziv()+":"+argumenti.get(i).getTip()+",";
            }
            text+=argumenti.get(i).getNaziv()+":"+argumenti.get(i).getTip();
         }
         text+=")";
         
         //povratna vrednost
         text+=": "+this.method.getPovratnaVrednost();
         
         this.setText(text);
         
         //ako je metoda staticka
         if(this.method.getIsStatic())
             this.setAttribute(FONT_UNDERLINED, true);
         
         //ako je metoda abstraktna
         if(this.method.getIsAbstract())
             this.setAttribute(FONT_ITALIC, true);
    }
    
    public void updateFigure(Metod newMetod){
        this.method.updateMetod(newMetod);
        initFigure();
    }

    /**
     * @return the parent
     */
    public IMethodContainer getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(IMethodContainer parent) {
        this.parent = parent;
    }
   
}
