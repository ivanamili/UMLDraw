package businessLogic;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.CrtezDb;
import store.entity.KorisnikDb;

public class Korisnik implements IDatabaseStore {

	private String ime;
	private String sifra;
	private Crtez trenutniCrtez;

	

	public String getIme() {
		return this.ime;
	}

	/**
	 * 
	 * @param ime
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getSifra() {
		return this.sifra;
	}

	/**
	 * 
	 * @param sifra
	 */
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Crtez getTrenutniCrtez() {
		return this.trenutniCrtez;
	}

	/**
	 * 
	 * @param trenutniCrtez
	 */
	public void setTrenutniCrtez(Crtez trenutniCrtez) {
		this.trenutniCrtez = trenutniCrtez;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        
        KorisnikDb attrZaBazu= new KorisnikDb();
        attrZaBazu.setIme(this.ime);
        attrZaBazu.setSifra(this.sifra);
        attrZaBazu.setCrtezDb(new CrtezDb(this.trenutniCrtez.getID()));
		
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
        KorisnikDb attrZaBazu= new KorisnikDb();
        attrZaBazu.setIme(this.ime);
        attrZaBazu.setSifra(this.sifra);
        attrZaBazu.setCrtezDb(new CrtezDb(this.trenutniCrtez.getID()));
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
             Query q=session.createQuery("select k.id from KorisnikDb k where k.ime like :ime");
             q.setParameter("ime", this.ime);
             
             int id=(int)q.uniqueResult();
             attrZaBazu.setId(id);

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
        KorisnikDb attrZaBazu= new KorisnikDb();
        attrZaBazu.setIme(this.ime);
        attrZaBazu.setSifra(this.sifra);
        attrZaBazu.setCrtezDb(new CrtezDb(this.trenutniCrtez.getID()));
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query q=session.createQuery("select k.id from KorisnikDb k where k.ime like :ime");
             q.setParameter("ime", this.ime);
             
             int id=(int)q.uniqueResult();
             attrZaBazu.setId(id);

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
        KorisnikDb koIzBaze=null;
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from KorisnikDb ko where ko.id = :id");
            query.setParameter("id",idComponents[0]);
          
             
            
            koIzBaze=(KorisnikDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze        
        this.ime=koIzBaze.getIme();
        this.sifra=koIzBaze.getSifra();
	//FALI THIS.CRTEZ DA SE UCITA IZ BAZE
    }

}