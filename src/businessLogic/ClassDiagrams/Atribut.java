package businessLogic.ClassDiagrams;
import enumerations.VisibilityTypeEnum;

public class Atribut {

	private int crtezID;
	private int klasaID;
	private int ID;
	private String naziv;
	private String tip;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;

	public int getCrtezID() {
		return this.crtezID;
	}
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}

	public int getKlasaID() {
		return this.klasaID;
	}
	public void setKlasaID(int klasaID) {
		this.klasaID = klasaID;
	}

	public int getID() {
		return this.ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}

	public String getTip() {
		return this.tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
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
        
        public String getNaziv() {
            return naziv;
        }
        public void setNaziv(String naziv) {
            this.naziv = naziv;
        }
        
        //pokuplja sve osim id-eva, oni se ne menjaju nikad
        public void update(Atribut noviAtribut){
            this.setNaziv(noviAtribut.getNaziv());
            this.setTip(noviAtribut.getTip());
            this.setVidljivost(noviAtribut.getVidljivost());
            this.setIsStatic(noviAtribut.getIsStatic());
        }

}