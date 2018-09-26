package businessLogic.UseCaseDiagrams;
import businessLogic.AbstractClassHierarchy.*;
import java.io.Serializable;
import org.jhotdraw.draw.*;


public class Aktor extends UseCaseDiagramElement implements Serializable{

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

        public String getNaziv() {
            return naziv;
        }

        public void setNaziv(String naziv) {
            this.naziv = naziv;
        }
        
        @Override
        public int getElemId() {
            return this.ID;
        }
        
        @Override
        public void setElemId(int id){
            this.ID=id;
        }
        
        @Override
        public void setCrtezIdentifikator(int id) {
            this.crtezID=id;
        }
        @Override
        public Object clone(){
            Aktor that= new Aktor();
            that.setCrtezID(this.crtezID);
            that.setElemId(this.ID);
            that.setNaziv(this.naziv);
            that.setOkvir(this.okvir.clone());
            
            return that;
        }

       

}