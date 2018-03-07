package businessLogic.ClassDiagrams;

import businessLogic.AbstractClassHierarchy.ClassDiagramElement;
import businessLogic.AbstractClassHierarchy.Veza;
import enumerations.ClassConnTypeEnum;
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
	private ClassDiagramElement odKoga;
	private ClassDiagramElement doKoga;
        private ClassConnTypeEnum tip;
        
        public ClassDiagramVeza()
        {
            this.odKoga=null;
            this.doKoga=null;
        }

	public int getCrtezID() {
		return this.crtezID;
	}
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}
        
	public ClassDiagramElement getOdKoga() {
		return this.odKoga;
	}
	public void setOdKoga(ClassDiagramElement odKoga) {
		this.odKoga=odKoga;
	}

	public ClassDiagramElement getDoKoga() {
		return this.doKoga;
	}
	public void setDoKoga(ClassDiagramElement doKoga) {
		this.doKoga = doKoga;
	}

  
   
    
    /**
     * @return the tip
     */
    public ClassConnTypeEnum getTip() {
        return tip;
    }

    /**
     * @param tip the tip to set
     */
    public void setTip(ClassConnTypeEnum tip) {
        this.tip = tip;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

}