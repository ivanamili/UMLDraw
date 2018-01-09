package businessLogic;

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

public class AktorVeza extends Veza {

	private int crtezID;
	private int ID;
	private Aktor aktor;
	private UseCase useCase;

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

	public Aktor getAktor() {
		return this.aktor;
	}

	/**
	 * 
	 * @param aktor
	 */
	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	public UseCase getUseCase() {
		return this.useCase;
	}

	/**
	 * 
	 * @param useCase
	 */
	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}

	public void getAttribute() {
		// TODO - implement AktorVeza.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement AktorVeza.setAttribute
		throw new UnsupportedOperationException();
	}

	public void getAttribute2() {
		// TODO - implement AktorVeza.getAttribute2
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute2
	 */
	public void setAttribute2(int attribute2) {
		// TODO - implement AktorVeza.setAttribute2
		throw new UnsupportedOperationException();
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setAktorId(this.aktor.getID());
        attrZaBazu.setUceCaseId(this.useCase.getID());
		
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
        
         AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setAktorId(this.aktor.getID());
        attrZaBazu.setUceCaseId(this.useCase.getID());
		
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
        
        AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setAktorId(this.aktor.getID());
        attrZaBazu.setUceCaseId(this.useCase.getID());
		
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
        AktorKonekcijaDb aktKIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from AktorKonekcijaDb aktk where aktk.id.crtezId = :crtezID and aktk.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            aktKIzBaze=(AktorKonekcijaDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=aktKIzBaze.getId().getCrtezId();
        this.ID=aktKIzBaze.getId().getId();
        
        //Aktor
        this.aktor=new Aktor();
        int[] idComp={this.crtezID,aktKIzBaze.getAktorId()};
        this.aktor.getByID(idComp, sessionFactory);
        
        //UseCase
        this.useCase=new UseCase();
        int[] idComp2={this.crtezID,aktKIzBaze.getUceCaseId()};
        this.useCase.getByID(idComp2, sessionFactory);
    }

}