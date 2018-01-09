package businessLogic;

import org.hibernate.Query;
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
    //prvi je crtezId, pa klasaId, pa metodId i na kraju Id samog argumenta
    public void getByID(int[] idComponents, SessionFactory sessionFactory) {
        Session session=null;
        Transaction tx = null;
        ArgumentDb argIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from ArgumentDb arg where arg.id.crtezId = :crtezID and arg.id.klasaId = :klasaID and arg.id.metodId = :metodID and arg.id.id = :ID");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID", idComponents[1]);
            query.setParameter("metodID", idComponents[2]);
            query.setParameter("ID", idComponents[3]);
            
            argIzBaze=(ArgumentDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=argIzBaze.getId().getCrtezId();
	this.klasaID=argIzBaze.getId().getKlasaId();
	this.metodID=argIzBaze.getId().getMetodId();
	this.ID=argIzBaze.getId().getId();
        this.naziv=argIzBaze.getNaziv();
	this.tip=argIzBaze.getTip();
    }

}