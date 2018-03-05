package businessLogic.ClassDiagrams;

import businessLogic.IDatabaseStore;
import enumerations.VisibilityTypeEnum;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.AtributDb;
import store.entity.AtributDbId;

public class Atribut implements IDatabaseStore {

	private int crtezID;
	private int klasaID;
	private int ID;
	private String naziv;
	private String tip;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;

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

	public VisibilityTypeEnum getVidljivost() {
		return this.vidljivost;
	}

	/**
	 * 
	 * @param vidljivost
	 */
	public void setVidljivost(VisibilityTypeEnum vidljivost) {
		this.vidljivost = vidljivost;
	}

	public boolean getIsStatic() {
		return this.isStatic;
	}

	/**
	 * 
	 * @param isStatic
	 */
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(this.crtezID,this.klasaID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        attrZaBazu.setTip(this.tip);
        attrZaBazu.setVidljivost(this.vidljivost.name());
        attrZaBazu.setIsStatic((byte)(this.isStatic?1:0));
		
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
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(this.crtezID,this.klasaID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        attrZaBazu.setTip(this.tip);
        attrZaBazu.setVidljivost(this.vidljivost.name());
        attrZaBazu.setIsStatic((byte)(this.isStatic?1:0));
		
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
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(this.crtezID,this.klasaID,this.ID));
        attrZaBazu.setNaziv(this.getNaziv());
        attrZaBazu.setTip(this.tip);
        attrZaBazu.setVidljivost(this.vidljivost.name());
        attrZaBazu.setIsStatic((byte)(this.isStatic?1:0));
		
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
        AtributDb atrIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from AtributDb atr where atr.id.crtezId = :crtezID and atr.id.klasaId = :klasaID and atr.id.attributId = :attributID");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID", idComponents[1]);
            query.setParameter("attributID", idComponents[2]);
            
            atrIzBaze=(AtributDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=atrIzBaze.getId().getCrtezId();
	this.klasaID=atrIzBaze.getId().getKlasaId();
	this.ID=atrIzBaze.getId().getAttributId();
        this.naziv=atrIzBaze.getNaziv();
	this.tip=atrIzBaze.getTip();
	this.vidljivost=VisibilityTypeEnum.valueOf(atrIzBaze.getVidljivost());
	this.isStatic=atrIzBaze.getIsStatic()!=0;
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