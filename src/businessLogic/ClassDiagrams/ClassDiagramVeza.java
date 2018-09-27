package businessLogic.ClassDiagrams;

import businessLogic.AbstractClassHierarchy.ClassDiagramElement;
import businessLogic.AbstractClassHierarchy.Veza;
import businessLogic.UseCaseDiagrams.UseCaseVeza;
import enumerations.ClassConnTypeEnum;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.DijagramKonekcijaDb;
import store.entity.DijagramKonekcijaDbId;

public class ClassDiagramVeza extends Veza implements Serializable{

	private int crtezID;
	private int ID;
	private int odKoga;//id elementa koji je pocetak veze (StartFigure)
	private int doKoga;//id elementa koji je kraj veze (EndFigure)
        private ClassConnTypeEnum tip;
        
        public ClassDiagramVeza(){
            
        }

	public int getCrtezID() {
		return this.crtezID;
	}
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}
        
	public int getOdKoga() {
		return this.odKoga;
	}
	public void setOdKoga(int odKoga) {
		this.odKoga=odKoga;
	}

	public int getDoKoga() {
		return this.doKoga;
	}
	public void setDoKoga(int doKoga) {
		this.doKoga = doKoga;
	}
  
        public ClassConnTypeEnum getTip() {
            return tip;
        }
        public void setTip(ClassConnTypeEnum tip) {
            this.tip = tip;
        }
        
        public int getID() {
            return ID;
        }
        public void setID(int ID) {
            this.ID = ID;
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
            ClassDiagramVeza that= new ClassDiagramVeza();
            that.setCrtezID(this.crtezID);
            that.setID(this.ID);
            that.setOdKoga(this.odKoga);
            that.setDoKoga(this.doKoga);
            that.setTip(this.tip);
            
            return that;
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