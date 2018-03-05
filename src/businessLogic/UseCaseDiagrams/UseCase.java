package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.Element;
import java.awt.geom.Rectangle2D;
import org.hibernate.*;
import org.jhotdraw.draw.EllipseFigure;
import store.entity.*;

public class UseCase extends Element {

	private int crtezID;
	private int ID;
	private String naziv;
	private EllipseFigure elipsa;

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

	public String getNaziv() {
		return this.naziv;
	}

	/**
	 * 
	 * @param naziv
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public EllipseFigure getElipsa() {
		return this.elipsa;
	}

	/**
	 * 
	 * @param elipsa
	 */
	public void setElipsa(EllipseFigure elipsa) {
		this.elipsa = elipsa;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
	Rectangle2D.Double bounds=this.elipsa.getBounds();
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
       UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
	Rectangle2D.Double bounds=this.elipsa.getBounds();
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
        UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(this.crtezID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
	Rectangle2D.Double bounds=this.elipsa.getBounds();
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
    public void getByID(int[] idComponents, SessionFactory sessionFactory) {
         Session session=null;
        Transaction tx = null;
        UseCaseDb izBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from UseCaseDb usecase where usecase.id.crtezId = :crtezID and usecase.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            izBaze=(UseCaseDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=izBaze.getId().getCrtezId();
        this.ID=izBaze.getId().getId();
        this.naziv=izBaze.getNaziv();
        this.elipsa= new EllipseFigure(izBaze.getPocetnaKoorX(),izBaze.getPocetnaKoorY(),izBaze.getSirina(),izBaze.getVisina());
    }

}