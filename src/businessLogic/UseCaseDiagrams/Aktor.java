package businessLogic.UseCaseDiagrams;
import businessLogic.AbstractClassHierarchy.*;
import java.awt.geom.Rectangle2D;
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
        
        @Override
        public boolean equals(Object obj){
            //nisu istog tipa sigurno nisu jednaki
            if(!(obj instanceof Aktor))
                return false;
            Aktor object=(Aktor) obj;
            //poredi okvire, vrlo verovatno da ako se razlikuju da su oni
            Rectangle2D.Double thisBounds=this.okvir.getBounds();
            Rectangle2D.Double objectBounds= object.okvir.getBounds();
            
            //da li su sva polja jednaka, ukljucujuci i okvir
            if(thisBounds.x==objectBounds.x && thisBounds.y ==objectBounds.y
                    && thisBounds.width==objectBounds.width && thisBounds.height==objectBounds.height
                    && this.ID==object.ID && this.crtezID==object.crtezID && this.naziv.equals(object.naziv))
                return true;
            
            return false;
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