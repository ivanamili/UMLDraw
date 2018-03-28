/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;

/**
 *
 * @author Korisnik
 */
//interfejs koji omogucava da iz figure koja predstavlja element izvucemo
//objekat koji sadrzi same podatke i koji se cuva u bazi ili se salje preko mreze
public interface IDataFigure {
    
    public AbstractDiagramElement getDataObject();
}
