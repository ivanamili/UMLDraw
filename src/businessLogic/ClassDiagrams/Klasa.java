package businessLogic.ClassDiagrams;

import businessLogic.AbstractClassHierarchy.ClassDiagramElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.KlasaDb;
import store.entity.KlasaDbId;

public class Klasa extends ClassDiagramElement {

	private int crtezID;
	private int ID;
        private String ime;
	private ArrayList<Atribut> atributi;
	private ArrayList<Metod> metode;
	private boolean isAbstract;
	private boolean isStatic;
	private int atributCounter;
	private int metodCounter;
        
        public Klasa()
        {
            this.atributCounter=0;
            this.metodCounter=0;
            this.atributi=new ArrayList<Atribut>();
            this.metode= new ArrayList<Metod>();
            this.isAbstract=false;
            this.isStatic=false;
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

	public ArrayList<Atribut> getAtributi() {
		return this.atributi;
	}

	/**
	 * 
	 * @param atributi
	 */
	public void setAtributi(ArrayList<Atribut> atributi) {
		this.atributi = atributi;
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

	public boolean getIsAbstract() {
		return this.isAbstract;
	}

	/**
	 * 
	 * @param isAbstract
	 */
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
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

	
	public int getAtributCounter() {
		return this.atributCounter;
	}

	/**
	 * 
	 * @param atributCounter
	 */
	public void setAtributCounter(int atributCounter) {
		this.atributCounter = atributCounter;
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

	/**
	 * 
	 * @param attr
	 */
	public void dodajAtribut(Atribut attr) {
		attr.setCrtezID(this.crtezID);
                attr.setKlasaID(this.ID);
                attr.setID(this.atributCounter);
                this.atributi.add(attr);
                this.atributCounter++;
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiAtribut(int id) {
		// TODO - implement Klasa.obrisiAtribut
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param metoda
	 */
	public void dodajMetodu(Metod metoda) {
		metoda.setCrtezID(this.crtezID);
                metoda.setKlasaID(this.ID);
                metoda.setID(this.metodCounter);
                this.metode.add(metoda);
                this.metodCounter++;
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiMetodu(int id) {
		// TODO - implement Klasa.obrisiMetodu
		throw new UnsupportedOperationException();
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(this.crtezID,this.ID));
        zaBazu.setIme(this.ime);
        zaBazu.setIsAbstract(this.isAbstract);
        zaBazu.setIsStatic(this.isStatic);
        zaBazu.setMetodCounter(this.metodCounter);
        zaBazu.setAtributCounter(this.atributCounter);
        
        //tek ovde pocinje cuvanje 
                
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
        
        //cuvanje atributa i metoda
        Iterator i= this.atributi.iterator();
        while(i.hasNext())
        {
            Atribut atr= (Atribut)i.next();
            atr.save(sessionFactory);
        }
        
        i=this.metode.iterator();
        while(i.hasNext())
        {
            Metod met= (Metod)i.next();
            met.save(sessionFactory);
        }
    }

    @Override
    public void update(SessionFactory sessionFactory) {
        //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(this.crtezID,this.ID));
        zaBazu.setIme(this.ime);
        zaBazu.setIsAbstract(this.isAbstract);
        zaBazu.setIsStatic(this.isStatic);
        zaBazu.setMetodCounter(this.metodCounter);
        zaBazu.setAtributCounter(this.atributCounter);
        
        //tek ovde pocinje cuvanje 
                
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
        //MOZDA NE TREBA
//        //cuvanje atributa i metoda
//        Iterator i= this.atributi.iterator();
//        while(i.hasNext())
//        {
//            Atribut atr= (Atribut)i.next();
//            atr.update(sessionFactory);
//        }
//        
//        i=this.metode.iterator();
//        while(i.hasNext())
//        {
//            Metod met= (Metod)i.next();
//            met.update(sessionFactory);
//        }
    }

    @Override
    public void delete(SessionFactory sessionFactory) {
       //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(this.crtezID,this.ID));
        zaBazu.setIme(this.ime);
        zaBazu.setIsAbstract(this.isAbstract);
        zaBazu.setIsStatic(this.isStatic);
        zaBazu.setMetodCounter(this.metodCounter);
        zaBazu.setAtributCounter(this.atributCounter);
        
         //prvo brisanje metoda i atributa, zbog foreign key constraint ogranicenja
        Iterator i= this.atributi.iterator();
        while(i.hasNext())
        {
            Atribut atr= (Atribut)i.next();
            atr.delete(sessionFactory);
        }
        
        i=this.metode.iterator();
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
        KlasaDb klIzBaze=null;
        List<Integer> atrIdLista=null;
        List<Integer> metIdLista=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from KlasaDb kl where kl.id.crtezId = :crtezID and kl.id.klasaId = :klasaID");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID", idComponents[1]);
            
            klIzBaze=(KlasaDb)query.uniqueResult();
            
            //id-evi svih atributa koji pripadaju toj klasi
            query=session.createQuery("select atr.id.attributId from AtributDb atr where atr.id.crtezId = :crtezID and atr.id.klasaId = :klasaID ");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaID",idComponents[1]);
            atrIdLista=query.list();
            
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
        this.crtezID=klIzBaze.getId().getCrtezId();
	this.ID=klIzBaze.getId().getKlasaId();
        this.ime=klIzBaze.getIme();
        this.atributCounter=klIzBaze.getAtributCounter();
	this.metodCounter=klIzBaze.getMetodCounter();
	this.isAbstract=klIzBaze.getIsAbstract();
	this.isStatic=klIzBaze.getIsStatic();
        
        //ucitavanje atributa
        Iterator i=atrIdLista.iterator();
        int[] idComp= new int[3];
        idComp[0]=this.crtezID;
        idComp[1]=this.ID;
        while(i.hasNext())
        {
            Atribut atr= new Atribut();
            idComp[2]=(int)i.next();
            atr.getByID(idComp, sessionFactory);
            this.atributi.add(atr);
        }
        
        //ucitavanje metoda
        i=metIdLista.iterator();
        while(i.hasNext())
        {
            Metod met=new Metod();
            idComp[2]=(int)i.next();
            met.getByID(idComp, sessionFactory);
            this.metode.add(met);
        }
	
    }

    /**
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public int getElemId() {
        return this.ID;
    }

}