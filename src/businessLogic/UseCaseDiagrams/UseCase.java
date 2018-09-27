package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.jhotdraw.draw.EllipseFigure;

public class UseCase extends UseCaseDiagramElement implements Serializable {

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
        
        @Override
        public Object clone(){
            UseCase that= new UseCase();
            that.setCrtezID(this.crtezID);
            that.setElemId(this.ID);
            that.setNaziv(this.naziv);
            that.setElipsa(this.elipsa.clone());
            
            return that;
        }
        
         @Override
        public boolean equals(Object obj){
            //nisu istog tipa sigurno nisu jednaki
            if(!(obj instanceof UseCase))
                return false;
            UseCase object=(UseCase) obj;
            //poredi okvire, vrlo verovatno da ako se razlikuju da su oni
            Rectangle2D.Double thisBounds=this.elipsa.getBounds();
            Rectangle2D.Double objectBounds= object.elipsa.getBounds();
            
            //da li su sva polja jednaka, ukljucujuci i okvir
            if(thisBounds.x==objectBounds.x && thisBounds.y ==objectBounds.y
                    && thisBounds.width==objectBounds.width && thisBounds.height==objectBounds.height
                    && this.ID==object.ID && this.crtezID==object.crtezID && this.naziv.equals(object.naziv))
                return true;
            
            return false;
        }

    @Override
    public Rectangle2D.Double getPresentationBounds() {
       return this.elipsa.getBounds();
    }

    @Override
    public void recreatePresentationFigure(Rectangle2D.Double bounds) {
        this.elipsa=new EllipseFigure(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void setBoundsToNull() {
        this.elipsa=null;
    }


}