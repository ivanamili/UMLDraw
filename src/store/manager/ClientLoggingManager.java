/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.manager;

import businessLogic.CommonClasses.Crtez;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import store.entity.KorisnikDb;
import store.entity.CrtezDb;
import store.entity.NewHibernateUtil;


/**
 *
 * @author Korisnik
 */
public class ClientLoggingManager implements IClientLoggingManager {
    
    private SessionFactory sessionFactory;
    
    public ClientLoggingManager()
    {
        this.configure();
    }
    
    public void configure()
    {
         sessionFactory = NewHibernateUtil.getSessionFactory();
    }

    @Override
    public boolean tryRegister(String username, String password) {
        Session session=null;
        Transaction tx = null;
        KorisnikDb koIzBaze;
        try{
            session = sessionFactory.openSession();
               //zapocinje se transakcija        
            tx = session.beginTransaction();
            Query q=session.createQuery("from KorisnikDb k where k.ime like :ime");
            q.setParameter("ime", username);

            koIzBaze=(KorisnikDb)q.uniqueResult();
            //ne postoji korisnik sa tim username-mom, dodaj korisnika u bazu
            if(koIzBaze==null)
            {
                KorisnikDb korisnik= new KorisnikDb();
                korisnik.setIme(username);
                korisnik.setSifra(password);
                session.save(korisnik);
                tx.commit();
                System.out.println("User "+username+" successfully registered!");
                return true;
            }
            //postoji korisnik sa tim imenom
            else
            {
                
                System.out.println("User "+username+" registration failed!");
                return false;
            }
             
           }
        catch (Exception e) 
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
         } finally {
            session.close(); 
         } 
    }

    @Override
    public boolean tryLogin(String username, String password) {
        Session session=null;
        Transaction tx = null;
        KorisnikDb koIzBaze;
        try{
            session = sessionFactory.openSession();
               //zapocinje se transakcija        
            tx = session.beginTransaction();
            Query q=session.createQuery("from KorisnikDb k where k.ime like :ime and k.sifra like :sifra");
            q.setParameter("ime", username);
            q.setParameter("sifra", password);

            koIzBaze=(KorisnikDb)q.uniqueResult();
            tx.commit();
            //ne postoji korisnik sa tim username-mom, dodaj korisnika u bazu
            if(koIzBaze==null)
            {
                return false;
            }
            //postoji korisnik sa tim imenom
            else
            {
                
                System.out.println("User "+username+" login successful!");
                return true;
            }
                
           }
        catch (Exception e) 
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
         } finally {
            session.close(); 
         } 
    }
    @Override
    public int tryCrtez(Crtez crtez)
    {
    Session session=null;
        Transaction tx = null;
        CrtezDb koIzBaze;
        try{
            session = sessionFactory.openSession();
               //zapocinje se transakcija        
            tx = session.beginTransaction();
            Query q=session.createQuery("from Crtez c where c.naslov like :naslov");
            q.setParameter("naslov", crtez.getNaslov());
            

            koIzBaze=(CrtezDb)q.uniqueResult();
            //ne postoji korisnik sa tim username-mom, dodaj korisnika u bazu
            if(koIzBaze==null)
            {
                CrtezDb crtez1= new CrtezDb();
                crtez1.setNaslov(crtez.getNaslov());
                crtez1.setTip(crtez.getTip().name());
                crtez1.setCounter1(crtez.getElemCounter());
                crtez1.setCounter2(crtez.getElemCounter());
                crtez1.setImeAutora(crtez.getImeAutora());
                session.save(crtez1);
                tx.commit();
                System.out.println("Crtez "+crtez.getNaslov()+" successfully add in database!");
                return crtez1.getId();
            }
            //postoji korisnik sa tim imenom
            else
            {
                
                System.out.println("Crtez "+crtez.getNaslov()+" write  failed!");
                return -1;
            }
             
           }
        catch (Exception e) 
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return 0;
         } finally {
            session.close(); 
         } 
    }
    
    


  
    
}
