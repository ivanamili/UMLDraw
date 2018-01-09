package businessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.*;

public class Interfejs extends ClassDiagramElement {

	private int crtezID;
	private int ID;
	private String naziv;
	private int metodCounter;
	private ArrayList<Metod> metode;
        
        public Interfejs()
        {
            this.metodCounter=0;
            this.metode=new ArrayList<Metod>();
        }

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

	public int getMetodCounter() {
		return this.metodCounter;
	}

	/**
	 * 
	 * @param metodCounter
	 */
	public void setMetodCounter(int metodCounter) {
		this.metodCounter = metodCounter;
	}

	public ArrayList<Metod> getMetode() {
		return this.metode;
	}

	/**
	 * 
	 * @param metode
	 */
	public void setMetode(ArrayList<Metod> metode) {
		this.metode = metode;
	}

	/**
	 * 
	 * @param met
	 */
	public void dodajMetodu(Metod met) {
		met.setCrtezID(this.crtezID);
                met.setKlasaID(this.ID);
                met.setID(this.metodCounter);
                this.metodCounter++;
                this.metode.add(met);
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiMetodu(int id) {
		// TODO - implement Interfejs.obrisiMetodu
		throw new UnsupportedOperationException();
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(this.crtezID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setMetodCounter(this.metodCounter);
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(zaBazu);
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
        //cuvanje metoda interfejsa  
        Iterator i=this.metode.iterator();
        while(i.hasNext())
        {
            Metod met= (Metod)i.next();
            met.save(sessionFactory);
        }
    }

    @Override
    public void update(SessionFactory sessionFactory) {
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(this.crtezID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setMetodCounter(this.metodCounter);
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.update(zaBazu);
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
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(this.crtezID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setMetodCounter(this.metodCounter);
        
        //prvo se brisu metode
        Iterator i=this.metode.iterator();
        while(i.hasNext())
        {
            Metod met= (Metod)i.next();
            met.delete(sessionFactory);
        }
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(zaBazu);
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
        InterfejsDb inIzBaze=null;
        List<Integer> metIdLista=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from InterfejsDb kl where kl.id.crtezId = :crtezID and kl.id.interfejsId = :klasaID");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID", idComponents[1]);
            
            inIzBaze=(InterfejsDb)query.uniqueResult();
            
            //id-evi svih metoda koje pripadaju toj klasi
            query=session.createQuery("select met.id.id from MetodDb met where met.id.crtezId = :crtezID and met.id.klasaIliInterfejsId = :klasaID ");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID",idComponents[1]);
            metIdLista=query.list();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=inIzBaze.getId().getCrtezId();
	this.ID=inIzBaze.getId().getInterfejsId();
        this.naziv=inIzBaze.getNaziv();
	this.metodCounter=inIzBaze.getMetodCounter();  
        
        Iterator i=metIdLista.iterator();
        int[] idComp= new int[3];
        idComp[0]=this.crtezID;
        idComp[1]=this.ID;
        while(i.hasNext())
        {
            Metod met=new Metod();
            idComp[2]=(int)i.next();
            met.getByID(idComp, sessionFactory);
            this.metode.add(met);
        }
    }

    @Override
    public int getElemId() {
        return this.ID;
    }

}