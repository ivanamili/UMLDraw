package businessLogic.ClassDiagrams;

import businessLogic.AbstractClassHierarchy.ClassDiagramElement;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jhotdraw.draw.RectangleFigure;
import store.entity.KlasaDb;
import store.entity.KlasaDbId;

public class Klasa extends ClassDiagramElement implements Serializable {

	private int crtezID;
	private int ID;
        private RectangleFigure okvir;
        private String ime;
	private ArrayList<Atribut> atributi;
	private ArrayList<Metod> metode;
	private boolean isAbstract;
	private boolean isStatic;
	private int atributCounter;
	private int metodCounter;
        
        public Klasa(){
            this.atributCounter=0;
            this.metodCounter=0;
            this.atributi=new ArrayList<Atribut>();
            this.metode= new ArrayList<Metod>();
            this.isAbstract=false;
            this.isStatic=false;
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

	public ArrayList<Atribut> getAtributi() {
		return this.atributi;
	}
	public void setAtributi(ArrayList<Atribut> atributi) {
		this.atributi = atributi;
	}

	public ArrayList<Metod> getMetode() {
		return this.metode;
	}
	public void setMetode(ArrayList<Metod> metode) {
		this.metode = metode;
	}

	public boolean getIsAbstract() {
		return this.isAbstract;
	}
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean getIsStatic() {
		return this.isStatic;
	}
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
        
        public String getIme() {
            return ime;
        }
        public void setIme(String ime) {
            this.ime = ime;
        }
	
	public int getAtributCounter() {
		return this.atributCounter;
	}
	public void setAtributCounter(int atributCounter) {
		this.atributCounter = atributCounter;
	}

	public int getMetodCounter() {
		return this.metodCounter;
	}
	public void setMetodCounter(int metodCounter) {
		this.metodCounter = metodCounter;
	}

	public void dodajAtribut(Atribut attr) {
		attr.setCrtezID(this.crtezID);
                attr.setKlasaID(this.ID);
                attr.setID(this.atributCounter);
                this.atributi.add(attr);
                this.atributCounter++;
	}
	public void obrisiAtribut(int id) {
		this.atributi.removeIf((Atribut obj) -> obj.getID() == id);
	}
	
        public void dodajMetodu(Metod metoda) {
		metoda.setCrtezID(this.crtezID);
                metoda.setKlasaIliInterfejsID(this.ID);
                metoda.setID(this.metodCounter);
                this.metode.add(metoda);
                this.metodCounter++;
	}
	public void obrisiMetodu(int id) {
		this.metode.removeIf((Metod obj) -> obj.getID() == id);
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
            Klasa that= new Klasa();
            that.setCrtezID(this.crtezID);
            that.setID(this.ID);
            that.setIme(this.ime);
            that.setIsAbstract(this.isAbstract);
            that.setIsStatic(this.isStatic);
            that.setAtributCounter(this.atributCounter);
            that.setMetodCounter(this.metodCounter);
            //array liste ce da postavi default construktor
            return that;
        }
        
        @Override 
        public boolean equals(Object obj){
            
            if(!(obj instanceof Klasa))
                return false;
            
            Klasa object= (Klasa)obj;
             
            Rectangle2D.Double thisBounds=this.okvir.getBounds();
            Rectangle2D.Double objectBounds= object.okvir.getBounds();
            
            if(thisBounds.x==objectBounds.x && thisBounds.y ==objectBounds.y
                    && thisBounds.width==objectBounds.width && thisBounds.height==objectBounds.height
                    && this.metodCounter==object.metodCounter && this.atributCounter==object.atributCounter
                    && this.ime.equals(object.ime) && this.ID==object.ID
                    && this.isAbstract==object.isAbstract && this.isStatic==object.isStatic
                    && this.crtezID==object.crtezID)
            {
                //proveri atribute i metode
                
                if(this.atributi.size()==object.atributi.size()
                        && this.metode.size()==object.metode.size())
                {
                    //proveri sve atribute
                    for(int i=0;i<this.atributi.size();i++){
                        if(!(this.atributi.get(i).equals(object.atributi.get(i))))
                            return false;
                    }
                    
                    //proveri sve metode
                    for(int i=0;i<this.metode.size();i++){
                        if(!(this.metode.get(i).equals(object.metode.get(i))))
                            return false;
                    }
                }
            }
                
            return true;
        }

    
        public RectangleFigure getOkvir() {
            return okvir;
        }
        public void setOkvir(RectangleFigure okvir) {
            this.okvir = okvir;
        }
        
        @Override
        public Rectangle2D.Double getPresentationBounds() {
           return this.okvir.getBounds();
        }

        @Override
        public void recreatePresentationFigure(Rectangle2D.Double bounds) {
            this.okvir=new RectangleFigure(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        public void setBoundsToNull() {
            this.okvir=null;
        }

}