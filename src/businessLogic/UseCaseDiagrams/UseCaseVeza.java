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
	private UseCase odKoga;
	private UseCase doKoga;
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

	public UseCase getOdKoga() {
		return this.odKoga;
	}
	public void setOdKoga(UseCase odKoga) {
		this.odKoga = odKoga;
	}

	public UseCase getDoKoga() {
		return this.doKoga;
	}
	public void setDoKoga(UseCase doKoga) {
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