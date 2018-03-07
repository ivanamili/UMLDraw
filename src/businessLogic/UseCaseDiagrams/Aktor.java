package businessLogic.UseCaseDiagrams;
import businessLogic.AbstractClassHierarchy.*;
import org.jhotdraw.draw.*;


public class Aktor extends UseCaseDiagramElement {

	private int crtezID;
	private int ID;
	private RectangleFigure okvir;
        private String naziv;
        
        public Aktor(){
            
        }
        
        public Aktor(int crtezId,int id){
            this.crtezID=crtezId;
            this.ID=id;
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

	public RectangleFigure getOkvir() {
		return this.okvir;
	}
	public void setOkvir(RectangleFigure okvir) {
		this.okvir = okvir;
	}

   
    
    
    

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}