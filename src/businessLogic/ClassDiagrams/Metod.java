package businessLogic.ClassDiagrams;

import enumerations.VisibilityTypeEnum;
import java.io.Serializable;
import java.util.ArrayList;


public class Metod implements Serializable {

	private int crtezID;
	private int klasaIliInterjfejsID;
	private int ID;
	private String naziv;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;
	private boolean isAbstract;
	private String povratnaVrednost;
	private ArrayList<Argument> argumenti;
        private int argumentIDCounter;//sluzi za dodelu id-ja argumentima uokviru metode
        
        public Metod()
        {
            this.argumenti= new ArrayList<Argument>();
            this.argumentIDCounter=0;
        }

	public int getCrtezID() {
		return this.crtezID;
	}
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}

	public int getKlasaIliInterjfejsID() {
		return this.klasaIliInterjfejsID;
	}
	public void setKlasaIliInterfejsID(int klasaID) {
		this.klasaIliInterjfejsID=klasaID;
	}

	public int getID() {
		return this.ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}

	public String getNaziv() {
		return this.naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public VisibilityTypeEnum getVidljivost() {
		return this.vidljivost;
	}
	public void setVidljivost(VisibilityTypeEnum vidljivost) {
		this.vidljivost = vidljivost;
	}

	public boolean getIsStatic() {
		return this.isStatic;
	}
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean getIsAbstract() {
		return this.isAbstract;
	}
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getPovratnaVrednost() {
		return this.povratnaVrednost;
	}
	public void setPovratnaVrednost(String povratnaVrednost) {
		this.povratnaVrednost = povratnaVrednost;
	}

	public ArrayList<Argument> getArgumenti() {
		return this.argumenti;
	}
	public void setArgumenti(ArrayList<Argument> argumenti) {
		this.argumenti = argumenti;
	}

	public int getArgumentIDCounter() {
		return this.argumentIDCounter;
	}
	public void setArgumentIDCounter(int argumentiCounter) {
		this.argumentIDCounter = argumentiCounter;
	}
	
	public void obrisiArgument(int id) {
		this.argumenti.removeIf((Argument obj) -> obj.getID() == id);
                
                
	}
        public void dodajArgument(Argument arg){
            arg.setCrtezID(this.crtezID);
            arg.setKlasaID(this.klasaIliInterjfejsID);
            arg.setMetodID(this.ID);
            arg.setID(this.argumentIDCounter);
            this.argumentIDCounter++;
            this.argumenti.add(arg);
        }
        
        //updatuje samo ime, vidljivost i povratnu vrednost, ne i argumente
        public void updateMetod(Metod newMetod){
            this.setNaziv(newMetod.getNaziv());
            this.setPovratnaVrednost(newMetod.getPovratnaVrednost());
            this.setVidljivost(newMetod.getVidljivost());
        }

}