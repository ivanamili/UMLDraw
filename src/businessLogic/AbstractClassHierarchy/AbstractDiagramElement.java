/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.AbstractClassHierarchy;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author Korisnik
 */
public abstract class AbstractDiagramElement {
    public abstract int getElemId();
    public abstract void setElemId(int id);
    public abstract void setCrtezIdentifikator(int id);
    
    public abstract Rectangle2D.Double getPresentationBounds();
    public abstract void recreatePresentationFigure(Rectangle2D.Double bounds);
    public abstract void setBoundsToNull();
    
    
    @Override
    public Object clone(){
         return null;
    }
    
    
}
