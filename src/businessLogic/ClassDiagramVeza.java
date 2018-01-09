package businessLogic;

import enumerations.ClassConnTypeEnum;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.DijagramKonekcijaDb;
import store.entity.DijagramKonekcijaDbId;

public class ClassDiagramVeza extends Veza {

	private int crtezID;
	private int ID;
	private ClassDiagramElement odKoga;
	private ClassDiagramElement doKoga;
        private ClassConnTypeEnum tip;

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

	public void getAttribute() {
		// TODO - implement ClassDiagramVeza.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement ClassDiagramVeza.setAttribute
		throw new UnsupportedOperationException();
	}

	public ClassDiagramElement getOdKoga() {
		// TODO - implement ClassDiagramVeza.getOdKoga
		return this.odKoga;
	}

	/**
	 * 
	 * @param odKoga
	 */
	public void setOdKoga(ClassDiagramElement odKoga) {
		this.odKoga=odKoga;
	}

	public ClassDiagramElement getDoKoga() {
		return this.doKoga;
	}

	/**
	 * 
	 * @param doKoga
	 */
	public void setDoKoga(ClassDiagramElement doKoga) {
		this.doKoga = doKoga;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(this.crtezID, this.ID));
        attrZaBazu.setTipVeze(this.tip.name());
        attrZaBazu.setOdKogaId(this.odKoga.getElemId());
        attrZaBazu.setDoKogaId(this.doKoga.getElemId());
        
        if(this.odKoga instanceof Klasa)
            attrZaBazu.setOdKogaTip("klasa");
        else
            attrZaBazu.setOdKogaTip("interfejs");
        
        if(this.doKoga instanceof Klasa)
            attrZaBazu.setDoKogaTip("klasa");
        else
            attrZaBazu.setDoKogaTip("interfejs");
		
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
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(this.crtezID, this.ID));
        attrZaBazu.setTipVeze(this.tip.name());
        attrZaBazu.setOdKogaId(this.odKoga.getElemId());
        attrZaBazu.setDoKogaId(this.doKoga.getElemId());
        
        if(this.odKoga instanceof Klasa)
            attrZaBazu.setOdKogaTip("klasa");
        else
            attrZaBazu.setOdKogaTip("interfejs");
        
        if(this.doKoga instanceof Klasa)
            attrZaBazu.setDoKogaTip("klasa");
        else
            attrZaBazu.setDoKogaTip("interfejs");
		
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
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(this.crtezID, this.ID));
        attrZaBazu.setTipVeze(this.tip.name());
        attrZaBazu.setOdKogaId(this.odKoga.getElemId());
        attrZaBazu.setDoKogaId(this.doKoga.getElemId());
        
        if(this.odKoga instanceof Klasa)
            attrZaBazu.setOdKogaTip("klasa");
        else
            attrZaBazu.setOdKogaTip("interfejs");
        
        if(this.doKoga instanceof Klasa)
            attrZaBazu.setDoKogaTip("klasa");
        else
            attrZaBazu.setDoKogaTip("interfejs");
		
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
        DijagramKonekcijaDb dkIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from DijagramKonekcijaDb dk where dk.id.crtezId = :crtezID and dk.id.vezeId = :vezeID");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("vezeID", idComponents[1]);
             
            
            dkIzBaze=(DijagramKonekcijaDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=dkIzBaze.getId().getCrtezId();
	this.ID=dkIzBaze.getId().getVezeId();
        this.tip=ClassConnTypeEnum.valueOf(dkIzBaze.getTipVeze());
	int[] idComp=new int[2];
        idComp[0]=this.crtezID;
        
        if(dkIzBaze.getOdKogaTip().equals("klasa"))
            this.odKoga=new Klasa();
        else
            this.odKoga=new Interfejs();
        
        idComp[1]=dkIzBaze.getOdKogaId();
        this.odKoga.getByID(idComp, sessionFactory);
        
        if(dkIzBaze.getDoKogaTip().equals("klasa"))
            this.doKoga=new Klasa();
        else
            this.doKoga=new Interfejs();
        
        idComp[1]=dkIzBaze.getDoKogaId();
        this.doKoga.getByID(idComp, sessionFactory);
    }

    /**
     * @return the tip
     */
    public ClassConnTypeEnum getTip() {
        return tip;
    }

    /**
     * @param tip the tip to set
     */
    public void setTip(ClassConnTypeEnum tip) {
        this.tip = tip;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

}