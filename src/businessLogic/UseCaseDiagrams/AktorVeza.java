package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.RectangleFigure;
import store.entity.AktorDb;
import store.entity.AktorDbId;
import store.entity.AktorKonekcijaDb;
import store.entity.AktorKonekcijaDbId;
import store.entity.UseCaseDb;
import store.entity.UseCaseDbId;

public class AktorVeza extends UseCaseDiagramVeza {

	private int crtezID;
	private int ID;
	private Aktor aktor;
	private UseCase useCase;
        
        public AktorVeza()
        {
            this.aktor=null;
            this.useCase=null;
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

	public Aktor getAktor() {
		return this.aktor;
	}
	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	public UseCase getUseCase() {
		return this.useCase;
	}
	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}
        
         @Override
        public int getVezaId() {
            return this.ID;
        }
        
        @Override
        public void setVezaId(int id){
            this.ID=id;
        }
        
        @Override
        public void setCrtezIdentifikator(int id) {
            this.crtezID=id;
        }

}