package store.entity;
// Generated Jan 2, 2018 5:10:25 PM by Hibernate Tools 4.3.1



/**
 * Klasa generated by hbm2java
 */
public class Klasa  implements java.io.Serializable {


     private KlasaId id;
     private Crtez crtez;
     private String atribut;
     private String metoda;

    public Klasa() {
    }

	
    public Klasa(KlasaId id, Crtez crtez) {
        this.id = id;
        this.crtez = crtez;
    }
    public Klasa(KlasaId id, Crtez crtez, String atribut, String metoda) {
       this.id = id;
       this.crtez = crtez;
       this.atribut = atribut;
       this.metoda = metoda;
    }
   
    public KlasaId getId() {
        return this.id;
    }
    
    public void setId(KlasaId id) {
        this.id = id;
    }
    public Crtez getCrtez() {
        return this.crtez;
    }
    
    public void setCrtez(Crtez crtez) {
        this.crtez = crtez;
    }
    public String getAtribut() {
        return this.atribut;
    }
    
    public void setAtribut(String atribut) {
        this.atribut = atribut;
    }
    public String getMetoda() {
        return this.metoda;
    }
    
    public void setMetoda(String metoda) {
        this.metoda = metoda;
    }




}

