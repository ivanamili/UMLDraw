/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import java.util.Set;

/**
 *
 * @author Korisnik
 */
//ovaj interfejs treba da implementirjau klase koje mogu da budu povezane konnektorima
//svaka takva klasa treba da ima set figura sa kojima je povezana
//ovaj interfejs implementira AbstractDiagramConnection
public interface IConnectable {
    //dodaje figuru u skup povezanih (kada se kreira nova veza)
    public void addConnectedFigure(IConnectable figure);
    
    //uklanja figuru iz skupa povezanih (kada se obrise veza)
    public void removeConnectedFigure(IConnectable figure);
    
    //vraca skup povezanih figura, radi provere da li neka figura vec postoji u tom skupu
    public Set<IConnectable> getConnectedFigures();
    
    //vraca id elementa koji opisuje figuru, da bi mogo da se postavi u objekat koji predstavlja vezu
    public int getDataObjectID();
}
