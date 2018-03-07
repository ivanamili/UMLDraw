/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 *
 * @author Korisnik
 */
//ova enumeracija sadrzi nazive svih klasa objekata koji postoje u aplikacji
//sluzi kako bi se preko poruka prenela informacija o tome koji objekat se zeli
//ovo se uvodi da ne bi moralo da se proverava sa instanceof, posto ce nakon serializacije
//i slanja objekta kroz mrezu stici objekat kao Object, a nacin rada sa njim zavisi od
//klase kojoj pripada
public enum RuntimeClassEnum {
    ATRIBUT,
    ARGUMENT,
    METOD,
    KLASA,
    INTERFEJS,
    CLASS_DIAGRAM_VEZA,
}
