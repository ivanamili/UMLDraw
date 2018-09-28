package businessLogic.ClassDiagrams;
import enumerations.VisibilityTypeEnum;
import java.io.Serializable;

public class Atribut implements Serializable {

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
        
         @Override 
        public boolean equals(Object obj){
            if(!(obj instanceof Atribut))
                return false;
            
            Atribut object=(Atribut)obj;
            
            if(this.naziv.equals(object.naziv) && this.tip.equals(object.tip) && this.ID==object.ID
                    && this.isStatic==object.isStatic && this.vidljivost==object.vidljivost
                    && this.klasaID==object.klasaID && this.crtezID==object.crtezID)
                return true;
            
            return false;
        }
        
         @Override
        public Object clone(){
            Atribut that= new Atribut();
            that.setCrtezID(this.crtezID);
            that.setKlasaID(this.klasaID);
            that.setID(this.ID);
            that.setTip(this.tip);
            that.setNaziv(this.naziv);
            that.setIsStatic(this.isStatic);
            that.setVidljivost(this.vidljivost);
            
            return that;
        }
        
        
        //pokuplja sve osim id-eva, oni se ne menjaju nikad
        public void update(Atribut noviAtribut){
            this.setNaziv(noviAtribut.getNaziv());
            this.setTip(noviAtribut.getTip());
            this.setVidljivost(noviAtribut.getVidljivost());
            this.setIsStatic(noviAtribut.getIsStatic());
        }

}