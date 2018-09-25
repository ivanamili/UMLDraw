/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.manager;

import enumerations.RuntimeClassEnum;

/**
 *
 * @author Korisnik
 * zaduzen za crud operacije sa business objektima
 */
public interface IPersistenceManager {
    
    //cuvanje objekata u bazu, drugi parametar je tip objekta
    public void save(Object obj, RuntimeClassEnum objectClass);
    //modifikacije postojecih objekata
    public void update(Object obj,RuntimeClassEnum objectClass);
    //brisanje objekata
    public void delete(Object obj, RuntimeClassEnum objectClass);
    //dobijanje objekata po id-ju
    public Object getById(int[] idComponents,RuntimeClassEnum desiredClass);
    
}
