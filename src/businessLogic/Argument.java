package businessLogic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.ArgumentDb;
import store.entity.ArgumentDbId;


public class Argument implements IDatabaseStore {

	private int crtezID;
	private int klasaID;
	private int metodID;
	private int ID;
	private String naziv;
	private String tip;

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

	public int getKlasaID() {
		return this.klasaID;
	}

	/**
	 * 
	 * @param klasaID
	 */
	public void setKlasaID(int klasaID) {
		this.klasaID = klasaID;
	}

	public int getMetodID() {
		return this.metodID;
	}

	/**
	 * 
	 * @param metodID
	 */
	public void setMetodID(int metodID) {
		this.metodID = metodID;
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

	public String getTip() {
		return this.tip;
	}

	/**
	 * 
	 * @param tip
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	public void getAttribute() {
		// TODO - implement Argument.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement Argument.setAttribute
		throw new UnsupportedOperationException();
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(this.crtezID,this.klasaID,this.metodID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
        attrZaBazu.setTip(this.tip);
        
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
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(this.crtezID,this.klasaID,this.metodID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
        attrZaBazu.setTip(this.tip);
        
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
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(this.crtezID,this.klasaID,this.metodID,this.ID));
        attrZaBazu.setNaziv(this.naziv);
        attrZaBazu.setTip(this.tip);
        
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
    public void getByID(int[] idComponents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}