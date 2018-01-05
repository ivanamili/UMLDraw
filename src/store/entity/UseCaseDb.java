package store.entity;
// Generated Jan 5, 2018 2:02:22 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * UseCaseDb generated by hbm2java
 */
public class UseCaseDb  implements java.io.Serializable {


     private UseCaseDbId id;
     private CrtezDb crtezDb;
     private String naziv;
     private Double pocetnaKoorX;
     private Double pocetnaKoorY;
     private Double visina;
     private Double sirina;
     private Set useCaseKonekcijaDbsForUseCaseConnOdKoga = new HashSet(0);
     private Set aktorKonekcijaDbs = new HashSet(0);
     private Set useCaseKonekcijaDbsForUseCaseConnDoKoga = new HashSet(0);

    public UseCaseDb() {
    }

	
    public UseCaseDb(UseCaseDbId id, CrtezDb crtezDb) {
        this.id = id;
        this.crtezDb = crtezDb;
    }
    public UseCaseDb(UseCaseDbId id, CrtezDb crtezDb, String naziv, Double pocetnaKoorX, Double pocetnaKoorY, Double visina, Double sirina, Set useCaseKonekcijaDbsForUseCaseConnOdKoga, Set aktorKonekcijaDbs, Set useCaseKonekcijaDbsForUseCaseConnDoKoga) {
       this.id = id;
       this.crtezDb = crtezDb;
       this.naziv = naziv;
       this.pocetnaKoorX = pocetnaKoorX;
       this.pocetnaKoorY = pocetnaKoorY;
       this.visina = visina;
       this.sirina = sirina;
       this.useCaseKonekcijaDbsForUseCaseConnOdKoga = useCaseKonekcijaDbsForUseCaseConnOdKoga;
       this.aktorKonekcijaDbs = aktorKonekcijaDbs;
       this.useCaseKonekcijaDbsForUseCaseConnDoKoga = useCaseKonekcijaDbsForUseCaseConnDoKoga;
    }
   
    public UseCaseDbId getId() {
        return this.id;
    }
    
    public void setId(UseCaseDbId id) {
        this.id = id;
    }
    public CrtezDb getCrtezDb() {
        return this.crtezDb;
    }
    
    public void setCrtezDb(CrtezDb crtezDb) {
        this.crtezDb = crtezDb;
    }
    public String getNaziv() {
        return this.naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Double getPocetnaKoorX() {
        return this.pocetnaKoorX;
    }
    
    public void setPocetnaKoorX(Double pocetnaKoorX) {
        this.pocetnaKoorX = pocetnaKoorX;
    }
    public Double getPocetnaKoorY() {
        return this.pocetnaKoorY;
    }
    
    public void setPocetnaKoorY(Double pocetnaKoorY) {
        this.pocetnaKoorY = pocetnaKoorY;
    }
    public Double getVisina() {
        return this.visina;
    }
    
    public void setVisina(Double visina) {
        this.visina = visina;
    }
    public Double getSirina() {
        return this.sirina;
    }
    
    public void setSirina(Double sirina) {
        this.sirina = sirina;
    }
    public Set getUseCaseKonekcijaDbsForUseCaseConnOdKoga() {
        return this.useCaseKonekcijaDbsForUseCaseConnOdKoga;
    }
    
    public void setUseCaseKonekcijaDbsForUseCaseConnOdKoga(Set useCaseKonekcijaDbsForUseCaseConnOdKoga) {
        this.useCaseKonekcijaDbsForUseCaseConnOdKoga = useCaseKonekcijaDbsForUseCaseConnOdKoga;
    }
    public Set getAktorKonekcijaDbs() {
        return this.aktorKonekcijaDbs;
    }
    
    public void setAktorKonekcijaDbs(Set aktorKonekcijaDbs) {
        this.aktorKonekcijaDbs = aktorKonekcijaDbs;
    }
    public Set getUseCaseKonekcijaDbsForUseCaseConnDoKoga() {
        return this.useCaseKonekcijaDbsForUseCaseConnDoKoga;
    }
    
    public void setUseCaseKonekcijaDbsForUseCaseConnDoKoga(Set useCaseKonekcijaDbsForUseCaseConnDoKoga) {
        this.useCaseKonekcijaDbsForUseCaseConnDoKoga = useCaseKonekcijaDbsForUseCaseConnDoKoga;
    }




}


