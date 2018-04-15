package store.entity;
// Generated Jan 9, 2018 4:38:35 PM by Hibernate Tools 4.3.1



/**
 * InterfejsDb generated by hbm2java
 */
public class InterfejsDb  implements java.io.Serializable {


     private InterfejsDbId id;
     private CrtezDb crtezDb;
     private String naziv;
     private Integer metodCounter;
     private Double pocetnaKoorX;
     private Double pocetnaKoorY;
     private Double visina;
     private Double sirina;

    public InterfejsDb() {
    }

	
    public InterfejsDb(InterfejsDbId id, CrtezDb crtezDb) {
        this.id = id;
        this.crtezDb = crtezDb;
    }
    public InterfejsDb(InterfejsDbId id, CrtezDb crtezDb, String naziv, Integer metodCounter) {
       this.id = id;
       this.crtezDb = crtezDb;
       this.naziv = naziv;
       this.metodCounter = metodCounter;
    }
   
    public InterfejsDbId getId() {
        return this.id;
    }
    
    public void setId(InterfejsDbId id) {
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
    public Integer getMetodCounter() {
        return this.metodCounter;
    }
    
    public void setMetodCounter(Integer metodCounter) {
        this.metodCounter = metodCounter;
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




}


