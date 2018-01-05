package store.entity;
// Generated Jan 5, 2018 2:02:22 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * KorisnikDb generated by hbm2java
 */
public class KorisnikDb  implements java.io.Serializable {


     private String ime;
     private CrtezDb crtezDb;
     private String sifra;
     private Set crtezDbs = new HashSet(0);

    public KorisnikDb() {
    }

	
    public KorisnikDb(String ime) {
        this.ime = ime;
    }
    public KorisnikDb(String ime, CrtezDb crtezDb, String sifra, Set crtezDbs) {
       this.ime = ime;
       this.crtezDb = crtezDb;
       this.sifra = sifra;
       this.crtezDbs = crtezDbs;
    }
   
    public String getIme() {
        return this.ime;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }
    public CrtezDb getCrtezDb() {
        return this.crtezDb;
    }
    
    public void setCrtezDb(CrtezDb crtezDb) {
        this.crtezDb = crtezDb;
    }
    public String getSifra() {
        return this.sifra;
    }
    
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public Set getCrtezDbs() {
        return this.crtezDbs;
    }
    
    public void setCrtezDbs(Set crtezDbs) {
        this.crtezDbs = crtezDbs;
    }




}


