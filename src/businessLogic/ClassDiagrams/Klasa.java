package businessLogic.ClassDiagrams;

import businessLogic.AbstractClassHierarchy.ClassDiagramElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.KlasaDb;
import store.entity.KlasaDbId;

public class Klasa extends ClassDiagramElement {

	private int crtezID;
	private int ID;
        private String ime;
	private ArrayList<Atribut> atributi;
	private ArrayList<Metod> metode;
	private boolean isAbstract;
	private boolean isStatic;
	private int atributCounter;
	private int metodCounter;
        
        public Klasa(){
            this.atributCounter=0;
            this.metodCounter=0;
            this.atributi=new ArrayList<Atribut>();
            this.metode= new ArrayList<Metod>();
            this.isAbstract=false;
            this.isStatic=false;
        }

	public int getCrtezID() {
		return this.crtezID;
	}
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}

	public int getID() {
		return this.ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}

	public ArrayList<Atribut> getAtributi() {
		return this.atributi;
	}
	public void setAtributi(ArrayList<Atribut> atributi) {
		this.atributi = atributi;
	}

	public ArrayList<Metod> getMetode() {
		return this.metode;
	}
	public void setMetode(ArrayList<Metod> metode) {
		this.metode = metode;
	}

	public boolean getIsAbstract() {
		return this.isAbstract;
	}
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean getIsStatic() {
		return this.isStatic;
	}
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
        
        public String getIme() {
            return ime;
        }
        public void setIme(String ime) {
            this.ime = ime;
        }
	
	public int getAtributCounter() {
		return this.atributCounter;
	}
	public void setAtributCounter(int atributCounter) {
		this.atributCounter = atributCounter;
	}

	public int getMetodCounter() {
		return this.metodCounter;
	}
	public void setMetodCounter(int metodCounter) {
		this.metodCounter = metodCounter;
	}

	public void dodajAtribut(Atribut attr) {
		attr.setCrtezID(this.crtezID);
                attr.setKlasaID(this.ID);
                attr.setID(this.atributCounter);
                this.atributi.add(attr);
                this.atributCounter++;
	}
	public void obrisiAtribut(int id) {
		this.atributi.removeIf((Atribut obj) -> obj.getID() == id);
	}
	
        public void dodajMetodu(Metod metoda) {
		metoda.setCrtezID(this.crtezID);
                metoda.setKlasaIliInterfejsID(this.ID);
                metoda.setID(this.metodCounter);
                this.metode.add(metoda);
                this.metodCounter++;
	}
	public void obrisiMetodu(int id) {
		this.metode.removeIf((Metod obj) -> obj.getID() == id);
	}
        
        @Override
        public int getElemId() {
            return this.ID;
        }
        
        @Override
        public void setElemId(int id){
            this.ID=id;
        }

}