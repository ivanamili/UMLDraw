package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import org.jhotdraw.draw.EllipseFigure;

public class UseCase extends UseCaseDiagramElement {

	private int crtezID;
	private int ID;
	private String naziv;
	private EllipseFigure elipsa;
        
        public UseCase(){
            
        }
        public UseCase(int crtezId,int id){
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

	public String getNaziv() {
		return this.naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public EllipseFigure getElipsa() {
		return this.elipsa;
	}
	public void setElipsa(EllipseFigure elipsa) {
		this.elipsa = elipsa;
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

}