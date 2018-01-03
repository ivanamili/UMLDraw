package store.entity;
// Generated Jan 3, 2018 7:59:26 PM by Hibernate Tools 4.3.1



/**
 * Atribut generated by hbm2java
 */
public class Atribut  implements java.io.Serializable {


     private AtributId id;
     private Klasa klasa;
     private String naziv;
     private String tip;
     private String vidljivost;
     private Byte isStatic;

    public Atribut() {
    }

	
    public Atribut(AtributId id, Klasa klasa) {
        this.id = id;
        this.klasa = klasa;
    }
    public Atribut(AtributId id, Klasa klasa, String naziv, String tip, String vidljivost, Byte isStatic) {
       this.id = id;
       this.klasa = klasa;
       this.naziv = naziv;
       this.tip = tip;
       this.vidljivost = vidljivost;
       this.isStatic = isStatic;
    }
   
    public AtributId getId() {
        return this.id;
    }
    
    public void setId(AtributId id) {
        this.id = id;
    }
    public Klasa getKlasa() {
        return this.klasa;
    }
    
    public void setKlasa(Klasa klasa) {
        this.klasa = klasa;
    }
    public String getNaziv() {
        return this.naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getTip() {
        return this.tip;
    }
    
    public void setTip(String tip) {
        this.tip = tip;
    }
    public String getVidljivost() {
        return this.vidljivost;
    }
    
    public void setVidljivost(String vidljivost) {
        this.vidljivost = vidljivost;
    }
    public Byte getIsStatic() {
        return this.isStatic;
    }
    
    public void setIsStatic(Byte isStatic) {
        this.isStatic = isStatic;
    }




}

