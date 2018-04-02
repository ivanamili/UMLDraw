package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import enumerations.UseCaseConnType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.UseCaseKonekcijaDb;
import store.entity.UseCaseKonekcijaDbId;

public class UseCaseVeza extends UseCaseDiagramVeza {

	private int crtezID;
	private int ID;
	private int odKoga;//id elementa koji je start veze tj od koga veza pocinje (StartFigure)
	private int doKoga;//id elementa koji je end veze tj na kome se veza zavrsava (EndFigure)
	private UseCaseConnType tipVeze;

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

	public int getOdKoga() {
		return this.odKoga;
	}
	public void setOdKoga(int odKoga) {
		this.odKoga = odKoga;
	}

	public int getDoKoga() {
		return this.doKoga;
	}
	public void setDoKoga(int doKoga) {
		this.doKoga = doKoga;
	}

	public UseCaseConnType getTipVeze() {
		return this.tipVeze;
	}
	public void setTipVeze(UseCaseConnType tipVeze) {
		this.tipVeze = tipVeze;
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