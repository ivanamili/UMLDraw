package businessLogic.UseCaseDiagrams;
import businessLogic.AbstractClassHierarchy.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jhotdraw.draw.*;
import store.entity.AktorDb;
import store.entity.AktorDbId;
import java.awt.geom.Rectangle2D;
import org.hibernate.Query;

public class Aktor extends UseCaseDiagramElement {

	private int crtezID;
	private int ID;
	private RectangleFigure okvir;
        private String naziv;

	public int getCrtezID() {
		return this.crtezID;
	}

	/**
	 * 
	 * @param crtezID
	 */
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}

	public int getID() {
		return this.ID;
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		this.ID=ID;
	}

	public RectangleFigure getOkvir() {
		return this.okvir;
	}

	/**
	 * 
	 * @param okvir
	 */
	public void setOkvir(RectangleFigure okvir) {
		this.okvir = okvir;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        Rectangle2D.Double bounds=this.okvir.getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }

    @Override
    public void update(SessionFactory sessionFactory) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        Rectangle2D.Double bounds=this.okvir.getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.update(attrZaBazu);
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }

    @Override
    public void delete(SessionFactory sessionFactory) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        Rectangle2D.Double bounds=this.okvir.getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(attrZaBazu);
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }

    @Override
    //prvi id je idCrteza a drugi id je id samog aktora
    //nacin koriscenja je prvo kreirati Aktor objekat defaultnim konstruktorom (prazan aktor objekat)
    //a zatim ga "popuniti" pozivom ove metode
    public void getByID(int[] idComponents, SessionFactory sessionFactory) {
        
        Session session=null;
        Transaction tx = null;
        AktorDb aktIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from AktorDb akt where akt.id.crtezId = :crtezID and akt.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            aktIzBaze=(AktorDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=aktIzBaze.getId().getCrtezId();
        this.ID=aktIzBaze.getId().getId();
        this.naziv=aktIzBaze.getNaziv();
        this.okvir= new RectangleFigure(aktIzBaze.getPocetnaKoorX(),aktIzBaze.getPocetnaKoorY(),aktIzBaze.getSirina(),aktIzBaze.getVisina());
    }

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}