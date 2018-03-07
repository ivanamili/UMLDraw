package businessLogic.UseCaseDiagrams;

import businessLogic.AbstractClassHierarchy.*;
import enumerations.UseCaseConnType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.UseCaseKonekcijaDb;
import store.entity.UseCaseKonekcijaDbId;

public class UseCaseVeza extends UseCaseDiagramVeza {

	private int crtezID;
	private int ID;
	private UseCase odKoga;
	private UseCase doKoga;
	private UseCaseConnType tipVeze;

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

	public UseCase getOdKoga() {
		return this.odKoga;
	}

	/**
	 * 
	 * @param odKoga
	 */
	public void setOdKoga(UseCase odKoga) {
		this.odKoga = odKoga;
	}

	public UseCase getDoKoga() {
		return this.doKoga;
	}

	/**
	 * 
	 * @param doKoga
	 */
	public void setDoKoga(UseCase doKoga) {
		this.doKoga = doKoga;
	}

	public UseCaseConnType getTipVeze() {
		return this.tipVeze;
	}

	/**
	 * 
	 * @param tipVeze
	 */
	public void setTipVeze(UseCaseConnType tipVeze) {
		this.tipVeze = tipVeze;
	}
	

    @Override
    public void save(SessionFactory sessionFactory) {
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setOdKogaId(this.odKoga.getID());
        attrZaBazu.setDoKogaId(this.doKoga.getID());
        attrZaBazu.setTipVeze(this.tipVeze.name());
        
		
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
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setOdKogaId(this.odKoga.getID());
        attrZaBazu.setDoKogaId(this.doKoga.getID());
        attrZaBazu.setTipVeze(this.tipVeze.name());
        
		
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
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(this.ID,this.crtezID));
        attrZaBazu.setOdKogaId(this.odKoga.getID());
        attrZaBazu.setDoKogaId(this.doKoga.getID());
        attrZaBazu.setTipVeze(this.tipVeze.name());
        
		
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
        UseCaseKonekcijaDb ucIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from UseCaseKonekcijaDb uc where uc.id.crtezId = :crtezID and uc.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
             
            
	ucIzBaze=(UseCaseKonekcijaDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=ucIzBaze.getId().getCrtezId();
	this.ID=ucIzBaze.getId().getId();
        this.tipVeze=UseCaseConnType.valueOf(ucIzBaze.getTipVeze());
        //od koga useCase
	this.odKoga=new UseCase();
        int[] idComp={this.crtezID,ucIzBaze.getOdKogaId()};
        this.odKoga.getByID(idComp, sessionFactory);
        //do koga useCase
	this.doKoga=new UseCase();
        int[] idComp2={this.crtezID,ucIzBaze.getDoKogaId()};
        this.doKoga.getByID(idComp2, sessionFactory);
    }

}