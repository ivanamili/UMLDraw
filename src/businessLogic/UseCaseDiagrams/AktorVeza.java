package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
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

public class AktorVeza extends UseCaseDiagramVeza implements Serializable {

	private int crtezID;
	private int ID;
	private int aktor;//id aktora
	private int useCase;//id useCase-a
        
        public AktorVeza()
        {
            
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

	public int getAktor() {
		return this.aktor;
	}
	public void setAktor(int aktor) {
		this.aktor = aktor;
	}

	public int getUseCase() {
		return this.useCase;
	}
	public void setUseCase(int useCase) {
		this.useCase = useCase;
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
            AktorVeza that=new AktorVeza();
            that.setCrtezID(this.crtezID);
            that.setID(this.ID);
            that.setAktor(this.aktor);
            that.setUseCase(this.useCase);
            
            return that;
        }
        
         @Override
        public boolean equals(Object obj){
            //nisu istog tipa sigurno nisu jednaki
            if(!(obj instanceof AktorVeza))
                return false;
            AktorVeza object=(AktorVeza) obj;
            
            //da li su sva polja jednaka
            if(this.ID==object.ID && this.crtezID==object.crtezID 
                    && this.aktor==object.aktor && this.useCase==object.useCase)
                return true;
            
            return false;
        }

    
     //veze nemaju okvirnu figuru, pa ove metode ne rade nista        
    @Override
    public Rectangle2D.Double getPresentationBounds() {
        //iako nema okvir, bitno je da vrati null da bi tamo znali da ta figura nema okvir
        return null;
    }

    @Override
    public void recreatePresentationFigure(Rectangle2D.Double bounds) {
        //ova figura je tipa veza, pa nema okvir te ne radi nista
    }

    @Override
    public void setBoundsToNull() {
        //ova figura nema bounds pa ne treba nista da radi
    }

}