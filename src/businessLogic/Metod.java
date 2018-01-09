package businessLogic;

import enumerations.VisibilityTypeEnum;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.MetodDb;
import store.entity.MetodDbId;
import store.entity.ArgumentDb;
import store.entity.ArgumentDbId;
import store.entity.NewHibernateUtil;

public class Metod implements IDatabaseStore {

	private int crtezID;
	private int klasaIliInterjfejsID;
	private int ID;
	private String naziv;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;
	private boolean isAbstract;
	private String povratnaVrednost;
	private ArrayList<Argument> argumenti;
	private int argumentiCounter;
        
        public Metod()
        {
            this.argumenti= new ArrayList<Argument>();
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

	public int getKlasaIliInterjfejsID() {
		return this.klasaIliInterjfejsID;
	}

	/**
	 * 
	 * @param klasaID
	 */
	public void setKlasaID(int klasaID) {
		this.klasaIliInterjfejsID=klasaID;
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

	public String getPovratnaVrednost() {
		return this.povratnaVrednost;
	}

	/**
	 * 
	 * @param povratnaVrednost
	 */
	public void setPovratnaVrednost(String povratnaVrednost) {
		this.povratnaVrednost = povratnaVrednost;
	}

	public ArrayList<Argument> getArgumenti() {
		return this.argumenti;
	}

	/**
	 * 
	 * @param argumenti
	 */
	public void setArgumenti(ArrayList<Argument> argumenti) {
		this.argumenti = argumenti;
	}

	public int getArgumentiCounter() {
		return this.argumentiCounter;
	}

	/**
	 * 
	 * @param argumentiCounter
	 */
	public void setArgumentiCounter(int argumentiCounter) {
		this.argumentiCounter = argumentiCounter;
	}

	public void getAttribute() {
		// TODO - implement Metod.getAttribute
		throw new UnsupportedOperationException();
	}	
        /**
	 * 
	 * @param id
	 */
	public void obrisiArgument(int id) {
		// TODO - implement Metod.obrisiArgument
		throw new UnsupportedOperationException();
	}
        
        public void dodajArgument(Argument arg)
        {
            arg.setCrtezID(this.crtezID);
            arg.setKlasaID(this.klasaIliInterjfejsID);
            arg.setMetodID(this.ID);
            arg.setID(this.argumentiCounter);
            this.argumentiCounter++;
            this.argumenti.add(arg);
        }

    @Override
    public void save(SessionFactory sessionFactory) {
        //popunjavanje osnovnih podataka objekta za bazu
        MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(this.crtezID,this.klasaIliInterjfejsID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setVidljivost( this.vidljivost.name());
        zaBazu.setIsStatic((byte)(this.isStatic?1:0));
        zaBazu.setIsAbstract((byte)(this.isStatic?1:0));
        zaBazu.setPovratnaVrednost(this.povratnaVrednost);
        zaBazu.setAtributCounter(this.argumentiCounter);
        
        //prosledjivanje poziva metode child elementima
        
        
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
        Iterator i= this.argumenti.iterator();
        while(i.hasNext())
        {
            Argument arg=(Argument)i.next();
            arg.save(sessionFactory);
        }
    }

    @Override
    public void update(SessionFactory sessionFactory) {
        //popunjavanje osnovnih podataka objekta za bazu
        MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(this.crtezID,this.klasaIliInterjfejsID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setVidljivost( this.vidljivost.name());
        zaBazu.setIsStatic((byte)(this.isStatic?1:0));
        zaBazu.setIsAbstract((byte)(this.isStatic?1:0));
        zaBazu.setPovratnaVrednost(this.povratnaVrednost);
        zaBazu.setAtributCounter(this.argumentiCounter);
        
//        MOZDA NE TREBA
//        Iterator i= this.argumenti.iterator();
//        while(i.hasNext())
//        {
//            Argument arg=(Argument)i.next();
//            arg.update(sessionFactory);
//        }
        
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
    }

    @Override
    public void delete(SessionFactory sessionFactory) {
         MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(this.crtezID,this.klasaIliInterjfejsID,this.ID));
        zaBazu.setNaziv(this.naziv);
        zaBazu.setVidljivost( this.vidljivost.name());
        zaBazu.setIsStatic((byte)(this.isStatic?1:0));
        zaBazu.setIsAbstract((byte)(this.isStatic?1:0));
        zaBazu.setPovratnaVrednost(this.povratnaVrednost);
        zaBazu.setAtributCounter(this.argumentiCounter);
        
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
    //prvi je crtezId pa id klase/interfejsa i na kraju id same metode
    public void getByID(int[] idComponents, SessionFactory sessionFactory) {
        
        Session session=null;
        Transaction tx = null;
        MetodDb meIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from MetodDb me where me.id.crtezId = :crtezID and me.id.klasaIliInterfejsId = :klasaIliInterfejsID and me.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("klasaIliInterfejsID", idComponents[1]);
            query.setParameter("id", idComponents[2]);
            
            meIzBaze=(MetodDb)query.uniqueResult();
            
            this.argumenti= new ArrayList<Argument>();
        
            Iterator i= meIzBaze.getArgumentDbs().iterator();
            while(i.hasNext())
            {
                ArgumentDb arg= (ArgumentDb)i.next();
                Argument argum= new Argument();
                argum.setCrtezID(arg.getId().getCrtezId());
                argum.setKlasaID(arg.getId().getKlasaId());
                argum.setMetodID(arg.getId().getMetodId());
                argum.setID(arg.getId().getId());
                argum.setNaziv(arg.getNaziv());
                argum.setTip(arg.getTip());
                this.argumenti.add(argum);
            }

         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        this.crtezID=meIzBaze.getId().getCrtezId();
	this.klasaIliInterjfejsID=meIzBaze.getId().getKlasaIliInterfejsId();
	this.ID=meIzBaze.getId().getId();
        this.naziv=meIzBaze.getNaziv();
	this.vidljivost=VisibilityTypeEnum.valueOf(meIzBaze.getVidljivost());
	this.isStatic= meIzBaze.getIsStatic()!=0;
	this.isAbstract=meIzBaze.getIsAbstract()!=0;
	this.povratnaVrednost=meIzBaze.getPovratnaVrednost();
	this.argumentiCounter=meIzBaze.getAtributCounter();
	
       
    }

	
}