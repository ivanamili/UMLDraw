/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.manager;

import businessLogic.CommonClasses.Crtez;

/**
 *
 * @author Korisnik
 * zaduzen za komunikaciju sa bazom za potrebe registracije, logovanja i kreiranja crteza
 * ili pridruzivanje crtezu
 */
public interface IClientLoggingManager {
    
    //treba da proveri da li postoji vec korisnik sa datim username-om i ako
    //ne postoji, dodaje korisnika u bazu, vraca true
    //ako korisnik postoji, registracija neuspesna, vraca false
    public boolean tryRegister(String username, String password);

    //proverava da li su kredencijali ispravni
    public boolean tryLogin(String username, String password);
    //proverava da li je kreiran crtez
    public int tryCrtez(Crtez crtez);
    
}
