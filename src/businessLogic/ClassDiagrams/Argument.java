package businessLogic.ClassDiagrams;

import java.io.Serializable;

public class Argument implements Serializable{

	private int crtezID;
	private int klasaID;
	private int metodID;
	private int ID;
	private String naziv;
	private String tip;

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

	public int getMetodID() {
		return this.metodID;
	}
	public void setMetodID(int metodID) {
		this.metodID = metodID;
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

	public String getTip() {
		return this.tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
        
        @Override 
        public boolean equals(Object obj){
            if(!(obj instanceof Argument))
                return false;
            
            Argument object=(Argument)obj;
            
            if(this.naziv.equals(object.naziv) && this.tip.equals(object.tip) && this.ID==object.ID
                    && this.metodID==object.metodID && this.klasaID==object.klasaID
                    && this.crtezID==object.crtezID)
                return true;
            
            return false;
        }
        
         @Override
        public Object clone(){
            Argument that= new Argument();
            that.setCrtezID(this.crtezID);
            that.setKlasaID(this.klasaID);
            that.setID(this.ID);
            that.setTip(this.tip);
            that.setNaziv(this.naziv);
            
            return that;
        }
        
        @Override
        public String toString(){
            return naziv+" : "+tip;
        }
}