/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;

/**
 *
 * @author Korisnik
 */
//interfejs koji omogucava da iz figure koja predstavlja element izvucemo
//objekat koji sadrzi same podatke i koji se cuva u bazi ili se salje preko mreze
//koristi ga UmlDrawing objekat da bi iz figure dodate na crtez izvukao objekat, i smestio ga u objekat crtez
//takodje ga koristi i DefaultDiagramElementFigure da bi izvukao id istog tog objekta, koji je potreba pri kreiranju veza
public interface IDataFigure {
    
    public AbstractDiagramElement getDataObject();
}
