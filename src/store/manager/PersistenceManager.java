/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.manager;

import businessLogic.ClassDiagrams.*;
import businessLogic.UseCaseDiagrams.*;
import enumerations.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import org.hibernate.*;
import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.RectangleFigure;
import store.entity.*;

/**
 *
 * @author Korisnik
 */
/*Ova klasa enkapsulira sve funkcije neophodne za prezistenciju podataka u bazu.
//namenjena je da se, kada je u aplikaciji neophodna perzistencija instancira objekat ove klase
//i nadalje pozivi njegovih metoda koriste za cuvanje podataka.
//U slucaju potrebe da se promeni API potreban za perzistenciju, jedino gde treba uneti izmene jeste u ovoj klasi.
//Za svaki od objekata koji se mogu perzistirati postoje 4 osnovne metode:
    -save
    -update
    -delete
    -getByID
*/
public class PersistenceManager {
    
    //za hibernate je neophodan sessionFactory objekat za kreiranje sesije
    //ovo je heavyweight objekat i kreira se samo jednom, pri kreiranju samog objekta
    //session i transaction su lightweight objekti i kreiraju se i zatvaraju pri svakoj pojedinacnoj transakciji
   
    private SessionFactory sessionFactory;
    
    public PersistenceManager()
    {
        this.configure();
    }
    
    //ostvaruje sve sto je neophodno za povezivanje sa bazom
    private void configure()
    {
        sessionFactory = NewHibernateUtil.getSessionFactory();
    }
    
    public void close()
    {
        sessionFactory.close();
    }
    //******************************************************
    //JAVNE METODE KOJE CE SE POZIVATI IZ SPOLJNEG SVETA
    //Njihov zadatak jeste da na osnovu tipa objekta koji je definisan RuntimeClassEnum vrednoscu enumeracije
    //proslede objekat odgovarajucoj private metodi na dalju obradu
    public void save(Object obj, RuntimeClassEnum objectClass)
    {
        switch(objectClass)
        {
            case ATRIBUT: save((Atribut)obj); break;
            case ARGUMENT: save((Argument)obj); break;
            case METOD: save((Metod)obj);break;
            case KLASA: save((Klasa)obj);break;
            case INTERFEJS: save((Interfejs)obj);break;
            case CLASS_DIAGRAM_VEZA: save((ClassDiagramVeza)obj);break;
            case AKTOR: save((Aktor)obj);break;
            case AKTOR_VEZA:save((AktorVeza)obj);break;
            case USE_CASE: save((UseCase)obj);break;
            case USE_CASE_VEZA: save((UseCaseVeza)obj);break;
        }
    }
    
    public void update(Object obj,RuntimeClassEnum objectClass)
    {
        switch(objectClass)
        {
            case ATRIBUT: update((Atribut)obj); break;
            case ARGUMENT: update((Argument)obj); break;
            case METOD: update ((Metod)obj);break;
            case KLASA: update((Klasa)obj);break;
            case INTERFEJS: update((Interfejs)obj);break;
            case CLASS_DIAGRAM_VEZA: update((ClassDiagramVeza)obj);break;
            case AKTOR: update((Aktor)obj);break;
            case AKTOR_VEZA:update((AktorVeza)obj);break;
            case USE_CASE: update((UseCase)obj);break;
            case USE_CASE_VEZA: update((UseCaseVeza)obj);break;
        }
    }
    public void delete(Object obj, RuntimeClassEnum objectClass)
    {
        switch(objectClass)
        {
            case ATRIBUT: delete((Atribut)obj); break;
            case ARGUMENT: delete((Argument)obj); break;
            case METOD: delete((Metod)obj);break;
            case KLASA: delete((Klasa)obj);break;
            case INTERFEJS: delete ((Interfejs)obj);break;
            case CLASS_DIAGRAM_VEZA: delete((ClassDiagramVeza)obj);break;
            case AKTOR: delete((Aktor)obj);break;
            case AKTOR_VEZA:delete((AktorVeza)obj);break;
            case USE_CASE: delete((UseCase)obj);break;
            case USE_CASE_VEZA: delete((UseCaseVeza)obj);break;
        }
    }
    
    public Object getById(int[] idComponents,RuntimeClassEnum desiredClass)
    {
        switch(desiredClass)
        {
            case ATRIBUT: return getAtributByID(idComponents);
            case ARGUMENT: return getArgumentByID(idComponents);
            case METOD: return getMetodByID(idComponents);
            case KLASA: return getKlasaByID(idComponents);
            case INTERFEJS: return getInterfejsByID(idComponents);
            case CLASS_DIAGRAM_VEZA: return getClassVezaByID(idComponents);
            case AKTOR: return getAktorByID(idComponents);
            case AKTOR_VEZA: return getAktorVezaByID(idComponents);
            case USE_CASE: return getUseCaseByID(idComponents);
            case USE_CASE_VEZA: return getUseCaseVezaByID(idComponents);
        }
        return null;
    }
    //******************************************
    
    //!!!!!!!!!!NAPOMENE!!!!!!!!!!!!!!!!!!!!
    /*
        Atributi koji se zovu Counter pa nesto NISU BROJACI KOLIKO NECEGA IMA 
        (jos jedna posledica lose definisane baze)
        njihova vrednost se koristi pri dodeljivanju ID-a odgovarajuceg elementa
        tako da uvek imaju vrednost POSLEDNJE dodatog elementa (bez obzira koliko je njih izbrisano)
        to znaci da prilikom SAVE treba POVECAVATI ZA 1
        ALI kod DELETE NE TREBA SMANJIVATI!!!!!!!!
    
        Ovo je da bi, nakon cuvanja u bazi i zatvaranja aplikacije, korisnig mogao ponovo da ucita celokupni crtez i da nastavi
        sa crtanjem gde je stao.
    */
    
    //*********************************************
    //PRIVATNE METODE, ZA SVAKI TIP OBJEKTA PO 4
    
    //ATRIBUT
    private void save(Atribut objToSave) {
        
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(objToSave.getCrtezID(),objToSave.getKlasaID(),objToSave.getID()));
        attrZaBazu.setNaziv(objToSave.getNaziv());
        attrZaBazu.setTip(objToSave.getTip());
        attrZaBazu.setVidljivost(objToSave.getVidljivost().name());
        attrZaBazu.setIsStatic((byte)(objToSave.getIsStatic()?1:0));
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

             //cuvanje atributa
            session.save(attrZaBazu);
            
            //update broja atributa u klasi
            Query query= session.createQuery("update KlasaDb k set k.atributCounter = k.atributCounter +1 where k.id.crtezId= :crtezID and k.id.klasaId= :klasaID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.setParameter("klasaID", objToSave.getKlasaID());
            query.executeUpdate();
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(Atribut objToUpdate) {
        
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(objToUpdate.getCrtezID(),objToUpdate.getKlasaID(),objToUpdate.getID()));
        attrZaBazu.setNaziv(objToUpdate.getNaziv());
        attrZaBazu.setTip(objToUpdate.getTip());
        attrZaBazu.setVidljivost(objToUpdate.getVidljivost().name());
        attrZaBazu.setIsStatic((byte)(objToUpdate.getIsStatic()?1:0));
		
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
    private void delete(Atribut objToDelete) {
        
        AtributDb attrZaBazu= new AtributDb();
        attrZaBazu.setId(new AtributDbId(objToDelete.getCrtezID(),objToDelete.getKlasaID(),objToDelete.getID()));
        		
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
    private Atribut getAtributByID(int[] idComponents) {
        Session session=null;
        Transaction tx = null;
        
        //ovde se cuva vrednost koja se dobije iz baze
        AtributDb atrIzBaze=null;
        //ovde se premapira objekat iz baze i ovaj objekat se vraca
        Atribut returnObject=new Atribut();
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
        returnObject.setCrtezID(atrIzBaze.getId().getCrtezId());
	returnObject.setKlasaID(atrIzBaze.getId().getKlasaId());
	returnObject.setID(atrIzBaze.getId().getAttributId());
        returnObject.setNaziv(atrIzBaze.getNaziv());
        returnObject.setTip(atrIzBaze.getTip());
	returnObject.setVidljivost(VisibilityTypeEnum.valueOf(atrIzBaze.getVidljivost()));
	returnObject.setIsStatic(atrIzBaze.getIsStatic()!=0);
        
        return returnObject;
    }
    
    //ARGUMENT
    private void save(Argument objToSave) {
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(objToSave.getCrtezID(),objToSave.getKlasaID(),objToSave.getMetodID(),objToSave.getID()));
        attrZaBazu.setNaziv(objToSave.getNaziv());
        attrZaBazu.setTip(objToSave.getTip());
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
            Query query= session.createQuery("update MetodDb k set k.atributCounter = k.atributCounter +1 where k.id.crtezId= :crtezID and k.id.klasaIliInterfejsId= :klasaID and k.id.id = :id ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.setParameter("klasaID", objToSave.getKlasaID());
            query.setParameter("id",objToSave.getMetodID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(Argument objToUpdate) {
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(objToUpdate.getCrtezID(),objToUpdate.getKlasaID(),objToUpdate.getMetodID(),objToUpdate.getID()));
        attrZaBazu.setNaziv(objToUpdate.getNaziv());
        attrZaBazu.setTip(objToUpdate.getTip());
        
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
    private void delete(Argument objToDelete) {
        ArgumentDb attrZaBazu= new ArgumentDb();
        attrZaBazu.setId(new ArgumentDbId(objToDelete.getCrtezID(),objToDelete.getKlasaID(),objToDelete.getMetodID(),objToDelete.getID()));
        
        
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
    private Argument getArgumentByID(int[] idComponents) {
        Session session=null;
        Transaction tx = null;
        ArgumentDb argIzBaze=null;
        Argument arg=new Argument();
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
        arg.setCrtezID(argIzBaze.getId().getCrtezId());
	arg.setKlasaID(argIzBaze.getId().getKlasaId());
	arg.setMetodID(argIzBaze.getId().getMetodId());
	arg.setID(argIzBaze.getId().getId());
        arg.setNaziv(argIzBaze.getNaziv());
	arg.setTip(argIzBaze.getTip());
        
        return arg;
    }
    
    //METOD
    private void save(Metod objToSave) {
        //popunjavanje osnovnih podataka objekta za bazu
        MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(objToSave.getCrtezID(),objToSave.getKlasaIliInterjfejsID(),objToSave.getID()));
        zaBazu.setNaziv(objToSave.getNaziv());
        zaBazu.setVidljivost( objToSave.getVidljivost().name());
        zaBazu.setIsStatic((byte)(objToSave.getIsStatic()?1:0));
        zaBazu.setIsAbstract((byte)(objToSave.getIsAbstract()?1:0));
        zaBazu.setPovratnaVrednost(objToSave.getPovratnaVrednost());
        zaBazu.setAtributCounter(objToSave.getArgumentIDCounter());
        
        //tek ovde pocinje cuvanje 
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(zaBazu);
            //ne treba proslediti poziv child elementima, jer se elementi uvek cuvaju i salju od najnizeg moguceg
            //tj kad se kreira argument, on ce biti poslat i on ce se sacuvati, tako da kad se cuva metoda potrebno
            //je sacuvati samo atribute same te metode, bez argumenata. Oni se naknadno dodaju
            
            //ALI TREBA POVECATI BROJAC U KLASI ILI INTERFEJSU KOME METODA PRIPADA!
            //ako je metod clanica KLASE
            Query query= session.createQuery("update KlasaDb k set k.metodCounter = k.metodCounter +1 where k.id.crtezId= :crtezID and k.id.klasaId= :klasaID");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.setParameter("klasaID", objToSave.getKlasaIliInterjfejsID());
            int result=query.executeUpdate();
            
            if(result==0)
            {
                query= session.createQuery("update InterfejsDb k set k.metodCounter = k.metodCounter +1 where k.id.crtezId= :crtezID and k.id.interfejsId= :klasaID");
                query.setParameter("crtezID",objToSave.getCrtezID());
                query.setParameter("klasaID", objToSave.getKlasaIliInterjfejsID());
                query.executeUpdate();
            }
            
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      } 
    }
    private void update(Metod objToUpdate) {
        //popunjavanje osnovnih podataka objekta za bazu
        MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(objToUpdate.getCrtezID(),objToUpdate.getKlasaIliInterjfejsID(),objToUpdate.getID()));
        zaBazu.setNaziv(objToUpdate.getNaziv());
        zaBazu.setVidljivost(objToUpdate.getVidljivost().name());
        zaBazu.setIsStatic((byte)(objToUpdate.getIsStatic()?1:0));
        zaBazu.setIsAbstract((byte)(objToUpdate.getIsAbstract()?1:0));
        zaBazu.setPovratnaVrednost(objToUpdate.getPovratnaVrednost());
        zaBazu.setAtributCounter(objToUpdate.getArgumentIDCounter());
        
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
    private void delete(Metod objToDelete) {
        //popunjavanje osnovnih podataka objekta za bazu
        MetodDb zaBazu=new MetodDb();
        zaBazu.setId(new MetodDbId(objToDelete.getCrtezID(),objToDelete.getKlasaIliInterjfejsID(),objToDelete.getID()));
        zaBazu.setNaziv(objToDelete.getNaziv());
        zaBazu.setVidljivost(objToDelete.getVidljivost().name());
        zaBazu.setIsStatic((byte)(objToDelete.getIsStatic()?1:0));
        zaBazu.setIsAbstract((byte)(objToDelete.getIsAbstract()?1:0));
        zaBazu.setPovratnaVrednost(objToDelete.getPovratnaVrednost());
        zaBazu.setAtributCounter(objToDelete.getArgumentIDCounter());
        
        //tek ovde pocinje cuvanje 
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(zaBazu);
                        
            //zbog on delete cascade on sam brise sve referenciranje argumente!
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      } 
    }
    private Metod getMetodByID(int[] idComponents) {
        
        Session session=null;
        Transaction tx = null;
        MetodDb meIzBaze=null;
        Metod returnObject=new Metod();
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
            
            //pribavljanje svih argumenata jedan po jedan i dodavanje u novokreirani objekat metod
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
                returnObject.getArgumenti().add(argum);
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
        returnObject.setCrtezID(meIzBaze.getId().getCrtezId());
	returnObject.setKlasaIliInterfejsID(meIzBaze.getId().getKlasaIliInterfejsId());
	returnObject.setID(meIzBaze.getId().getId());
        returnObject.setNaziv(meIzBaze.getNaziv());
	returnObject.setVidljivost(VisibilityTypeEnum.valueOf(meIzBaze.getVidljivost()));
	returnObject.setIsStatic(meIzBaze.getIsStatic()!=0);
	returnObject.setIsAbstract(meIzBaze.getIsAbstract()!=0);
	returnObject.setPovratnaVrednost(meIzBaze.getPovratnaVrednost());
	returnObject.setArgumentIDCounter(meIzBaze.getAtributCounter());
	
        return returnObject;
       
    }
    
    //KLASA
    private void save(Klasa objToSave) {
        //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(objToSave.getCrtezID(),objToSave.getID()));
        zaBazu.setIme(objToSave.getIme());
        zaBazu.setIsAbstract(objToSave.getIsAbstract());
        zaBazu.setIsStatic(objToSave.getIsStatic());
        zaBazu.setMetodCounter(objToSave.getMetodCounter());
        zaBazu.setAtributCounter(objToSave.getAtributCounter());
        
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
             
             //treba updateovati brojac klasa i interfejsa u crtezu, jer je dodata nova klasa
             //brojac COUNTER1
            Query query= session.createQuery("update CrtezDb k set k.counter1 = k.counter1 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
             
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //nije potrebno cuvati metode i atribute (nece ni biti prosledjeni preko mreze)
        //ukoliko je to neophodno moguce je dodati ovde kod ili poziv metode koja ce to da radi
    }
    private void update(Klasa objToUpdate) {
        //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(objToUpdate.getCrtezID(),objToUpdate.getID()));
        zaBazu.setIme(objToUpdate.getIme());
        zaBazu.setIsAbstract(objToUpdate.getIsAbstract());
        zaBazu.setIsStatic(objToUpdate.getIsStatic());
        zaBazu.setMetodCounter(objToUpdate.getMetodCounter());
        zaBazu.setAtributCounter(objToUpdate.getAtributCounter());
        
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
    private void delete(Klasa objToDelete) {
        //popunjavanje osnovnih podataka objekta za bazu
        KlasaDb zaBazu=new KlasaDb();
        zaBazu.setId(new KlasaDbId(objToDelete.getCrtezID(),objToDelete.getID()));

        
        //tek ovde pocinje cuvanje 
                
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(zaBazu);
             //zavrsava se transakcija
           
            //atributi ce zbog on delete cascade sami da se izbrisu
            //ali metode nece
            //RUCNO BRISANJE METODA
            Query query= session.createQuery("delete MetodDb m where m.id.crtezId = :crtezID and m.id.klasaIliInterfejsId=:klasaID");
            query.setParameter("crtezID",objToDelete.getCrtezID());
            query.setParameter("klasaID",objToDelete.getID());
            query.executeUpdate();
            
            //rucno brisanje svih veza sa tom klasom
            query= session.createQuery("delete DijagramKonekcijaDb m where m.id.crtezId = :crtezID and ( m.odKogaId=:klasaID or m.doKogaId=:klasaID)");
            query.setParameter("crtezID",objToDelete.getCrtezID());
            query.setParameter("klasaID",objToDelete.getID());
            query.executeUpdate();

             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        
    }
    private Klasa getKlasaByID(int[] idComponents) {
        Session session=null;
        Transaction tx = null;
        KlasaDb klIzBaze=null;
        Klasa returnObject=new Klasa();
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
        returnObject.setCrtezID(klIzBaze.getId().getCrtezId());
	returnObject.setID(klIzBaze.getId().getKlasaId());
        returnObject.setIme(klIzBaze.getIme());
        returnObject.setAtributCounter(klIzBaze.getAtributCounter());
	returnObject.setMetodCounter(klIzBaze.getMetodCounter());
	returnObject.setIsAbstract(klIzBaze.getIsAbstract());
	returnObject.setIsStatic(klIzBaze.getIsStatic());
        
        //ucitavanje atributa
        Iterator i=atrIdLista.iterator();
        int[] idComp= new int[3];
        idComp[0]=returnObject.getCrtezID();
        idComp[1]=returnObject.getID();
        while(i.hasNext())
        {
            Atribut atr;
            idComp[2]=(int)i.next();
            atr=getAtributByID(idComp);
            returnObject.getAtributi().add(atr);
        }
        
        //ucitavanje metoda
        i=metIdLista.iterator();
        while(i.hasNext())
        {
            Metod met;
            idComp[2]=(int)i.next();
            met=getMetodByID(idComp);
            returnObject.getMetode().add(met);
        }
        
        return returnObject;
	
    }
    
    //INTERFEJS
    private void save(Interfejs objToSave) {
        
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(objToSave.getCrtezID(),objToSave.getID()));
        zaBazu.setNaziv(objToSave.getNaziv());
        zaBazu.setMetodCounter(objToSave.getMetodCounter());
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(zaBazu);
            
            //treba updateovati brojac klasa i interfejsa u crtezu, jer je dodat novi interfejs
             //brojac COUNTER1
            Query query= session.createQuery("update CrtezDb k set k.counter1 = k.counter1 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
            
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      } 
        //metode se ne cuvaju.
        //ako je neophodno ovde dodati kod ili poziv metode za to
    }
    private void update(Interfejs objToUpdate) {
        
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(objToUpdate.getCrtezID(),objToUpdate.getID()));
        zaBazu.setNaziv(objToUpdate.getNaziv());
        zaBazu.setMetodCounter(objToUpdate.getMetodCounter());
        
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
        //metode se ne cuvaju.
        //ako je neophodno ovde dodati kod ili poziv metode za to
    }
    private void delete(Interfejs objToDelete) {
        
        InterfejsDb zaBazu= new InterfejsDb();
        zaBazu.setId(new InterfejsDbId(objToDelete.getCrtezID(),objToDelete.getID()));
       
        
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(zaBazu);
            
                        
            //takodje treba obrisati i metode koje pripadaju interfejsu
            //RUCNO BRISANJE METODA
            Query query= session.createQuery("delete MetodDb m where m.id.crtezId = :crtezID and m.id.klasaIliInterfejsId=:klasaID");
            query.setParameter("crtezID",objToDelete.getCrtezID());
            query.setParameter("klasaID",objToDelete.getID());
            query.executeUpdate();
            
             //rucno brisanje svih veza sa tim interfejsom
            query= session.createQuery("delete DijagramKonekcijaDb m where m.id.crtezId = :crtezID and ( m.odKogaId=:klasaID or m.doKogaId=:klasaID)");
            query.setParameter("crtezID",objToDelete.getCrtezID());
            query.setParameter("klasaID",objToDelete.getID());
            query.executeUpdate();
            
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      } 
        //metode se ne cuvaju.
        //ako je neophodno ovde dodati kod ili poziv metode za to
    }
    private Interfejs getInterfejsByID(int[] idComponents) {
        
        Session session=null;
        Transaction tx = null;
        InterfejsDb inIzBaze=null;
        Interfejs returnObject=new Interfejs();
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
        returnObject.setCrtezID(inIzBaze.getId().getCrtezId());
	returnObject.setID(inIzBaze.getId().getInterfejsId());
        returnObject.setNaziv(inIzBaze.getNaziv());
	returnObject.setMetodCounter(inIzBaze.getMetodCounter());  
        
        Iterator i=metIdLista.iterator();
        int[] idComp= new int[3];
        idComp[0]=returnObject.getCrtezID();
        idComp[1]=returnObject.getID();
        while(i.hasNext())
        {
            Metod met;
            idComp[2]=(int)i.next();
            met=getMetodByID(idComp);
            returnObject.getMetode().add(met);
        }
        
        return returnObject;
    }
    
    //CLASS DIAGRAM VEZA
    private void save(ClassDiagramVeza objToSave) {
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(objToSave.getCrtezID(), objToSave.getID()));
        attrZaBazu.setTipVeze(objToSave.getTip().name());
        attrZaBazu.setOdKogaId(objToSave.getOdKoga().getElemId());
        attrZaBazu.setDoKogaId(objToSave.getDoKoga().getElemId());
        
        if(objToSave.getOdKoga() instanceof Klasa)
            attrZaBazu.setOdKogaTip("klasa");
        else
            attrZaBazu.setOdKogaTip("interfejs");
        
        if(objToSave.getDoKoga() instanceof Klasa)
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
            
            //treba updateovati brojac veza u crtezu, jer je dodata nova veza
             //brojac COUNTER2
            Query query= session.createQuery("update CrtezDb k set k.counter2 = k.counter2 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(ClassDiagramVeza objToUpdate) {
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(objToUpdate.getCrtezID(), objToUpdate.getID()));
        attrZaBazu.setTipVeze(objToUpdate.getTip().name());
        attrZaBazu.setOdKogaId(objToUpdate.getOdKoga().getElemId());
        attrZaBazu.setDoKogaId(objToUpdate.getDoKoga().getElemId());
        
        if(objToUpdate.getOdKoga() instanceof Klasa)
            attrZaBazu.setOdKogaTip("klasa");
        else
            attrZaBazu.setOdKogaTip("interfejs");
        
        if(objToUpdate.getDoKoga() instanceof Klasa)
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
    private void delete(ClassDiagramVeza objToDelete) {
        DijagramKonekcijaDb attrZaBazu= new DijagramKonekcijaDb();
        attrZaBazu.setId(new DijagramKonekcijaDbId(objToDelete.getCrtezID(), objToDelete.getID()));
        
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
    private ClassDiagramVeza getClassVezaByID(int[] idComponents) {
        Session session=null;
        Transaction tx = null;
        DijagramKonekcijaDb dkIzBaze=null;
        ClassDiagramVeza returnObject=new ClassDiagramVeza();
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
        returnObject.setCrtezID(dkIzBaze.getId().getCrtezId());
	returnObject.setID(dkIzBaze.getId().getVezeId());
        returnObject.setTip(ClassConnTypeEnum.valueOf(dkIzBaze.getTipVeze()));
	
        
        //u polja od koga i do koga se kreiraju samo objekti sa id-evima, ne prave se novi objekti
        if(dkIzBaze.getOdKogaTip().equals("klasa"))
            returnObject.setOdKoga(new Klasa());
        else
            returnObject.setOdKoga(new Interfejs());        
        returnObject.getOdKoga().setElemId(dkIzBaze.getOdKogaId());
        
        if(dkIzBaze.getDoKogaTip().equals("klasa"))
            returnObject.setDoKoga(new Klasa());
        else
            returnObject.setDoKoga(new Interfejs());        
       returnObject.getDoKoga().setElemId(dkIzBaze.getDoKogaId());
       
       return returnObject;
    }
    
    //**************************************
    
    //AKTOR
    private void save(Aktor objToSave) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(objToSave.getCrtezID(),objToSave.getID()));
        attrZaBazu.setNaziv(objToSave.getNaziv());
        Rectangle2D.Double bounds=objToSave.getOkvir().getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
            
            //treba updateovati brojac aktera i useCase-ova u crtezu, jer je dodat novu akter
             //brojac COUNTER1
            Query query= session.createQuery("update CrtezDb k set k.counter1 = k.counter1 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(Aktor objToUpdate) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(objToUpdate.getCrtezID(),objToUpdate.getID()));
        attrZaBazu.setNaziv(objToUpdate.getNaziv());
        Rectangle2D.Double bounds=objToUpdate.getOkvir().getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);
        
		
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
    private void delete(Aktor objToDelete) {
        
        AktorDb attrZaBazu= new AktorDb();
        attrZaBazu.setId(new AktorDbId(objToDelete.getCrtezID(),objToDelete.getID()));
        	
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.delete(attrZaBazu);
            //zbog ON DELETE CASCADE u bazi, on automatcki brise AktorVeza objekte koji ga referenciraju
            //tj brisanjem aktera brisu se i sve veze koje vode do njega
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private Aktor getAktorByID(int[] idComponents) {
        
        Session session=null;
        Transaction tx = null;
        AktorDb aktIzBaze=null;
        Aktor returnObject=new Aktor();
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from AktorDb akt where akt.id.crtezId = :crtezID and akt.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            aktIzBaze=(AktorDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        returnObject.setCrtezID(aktIzBaze.getId().getCrtezId());
        returnObject.setID(aktIzBaze.getId().getId());
        returnObject.setNaziv(aktIzBaze.getNaziv());
        returnObject.setOkvir(new RectangleFigure(aktIzBaze.getPocetnaKoorX(),aktIzBaze.getPocetnaKoorY(),aktIzBaze.getSirina(),aktIzBaze.getVisina()));
        
        return returnObject;
    }
    
    //AKTOR VEZA 
    private void save(AktorVeza objToSave ) {
        
        AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(objToSave.getID(),objToSave.getCrtezID()));
        attrZaBazu.setAktorId(objToSave.getAktor().getID());
        attrZaBazu.setUceCaseId(objToSave.getUseCase().getID());
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
            
             //treba updateovati brojac veza u crtezu, jer je dodata nova veza
             //brojac COUNTER2
            Query query= session.createQuery("update CrtezDb k set k.counter2 = k.counter2 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(AktorVeza objToUpdate ) {
        
        AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(objToUpdate.getID(),objToUpdate.getCrtezID()));
        attrZaBazu.setAktorId(objToUpdate.getAktor().getID());
        attrZaBazu.setUceCaseId(objToUpdate.getUseCase().getID());
		
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
    private void delete(AktorVeza objToDelete ) {
        
        AktorKonekcijaDb attrZaBazu= new AktorKonekcijaDb();
        attrZaBazu.setId(new AktorKonekcijaDbId(objToDelete.getID(),objToDelete.getCrtezID()));
       	
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
    private AktorVeza getAktorVezaByID(int[] idComponents) {
        
        Session session=null;
        Transaction tx = null;
        AktorKonekcijaDb aktKIzBaze=null;
        AktorVeza returnObject= new AktorVeza();
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from AktorKonekcijaDb aktk where aktk.id.crtezId = :crtezID and aktk.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            aktKIzBaze=(AktorKonekcijaDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        returnObject.setCrtezID(aktKIzBaze.getId().getCrtezId());
        returnObject.setID(aktKIzBaze.getId().getId());
        
        //Aktor-upisuju se samo id-jevi. pretpostavljam da ce jhotDraw zahtevati reference, pa zato
        returnObject.setAktor(new Aktor(returnObject.getCrtezID(),aktKIzBaze.getAktorId()));
        
        //UseCase - ista filozofija kao gore
        returnObject.setUseCase(new UseCase(returnObject.getCrtezID(),aktKIzBaze.getUceCaseId()));
        
        return returnObject;
        
    }
    
    //USE CASE
    private void save(UseCase objToSave) {
        
        UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(objToSave.getCrtezID(),objToSave.getID()));
        attrZaBazu.setNaziv(objToSave.getNaziv());
	Rectangle2D.Double bounds=objToSave.getElipsa().getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);	
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
            
            //treba updateovati brojac aktera i useCase-ova u crtezu, jer je dodat novi use case
             //brojac COUNTER1
            Query query= session.createQuery("update CrtezDb k set k.counter1 = k.counter1 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(UseCase objToUpdate) {
        
        UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(objToUpdate.getCrtezID(),objToUpdate.getID()));
        attrZaBazu.setNaziv(objToUpdate.getNaziv());
	Rectangle2D.Double bounds=objToUpdate.getElipsa().getBounds();
        attrZaBazu.setPocetnaKoorX(bounds.x);
        attrZaBazu.setPocetnaKoorY(bounds.y);
        attrZaBazu.setVisina(bounds.height);
        attrZaBazu.setSirina(bounds.width);	
        
		
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
    private void delete(UseCase objToDelete) {
        
        UseCaseDb attrZaBazu= new UseCaseDb();
        attrZaBazu.setId(new UseCaseDbId(objToDelete.getCrtezID(),objToDelete.getID()));
        
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
    private UseCase getUseCaseByID(int[] idComponents) {
         Session session=null;
        Transaction tx = null;
        UseCaseDb izBaze=null;
        UseCase returnObject=new UseCase();
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();
             
            Query query=session.createQuery("from UseCaseDb usecase where usecase.id.crtezId = :crtezID and usecase.id.id = :id");
            query.setParameter("crtezID",idComponents[0]);
            query.setParameter("id", idComponents[1]);
            
            izBaze=(UseCaseDb)query.uniqueResult();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }  
        
        //upisivanje vrednosti iz objekta iz baze
        returnObject.setCrtezID(izBaze.getId().getCrtezId());
        returnObject.setID(izBaze.getId().getId());
        returnObject.setNaziv(izBaze.getNaziv());
        returnObject.setElipsa( new EllipseFigure(izBaze.getPocetnaKoorX(),izBaze.getPocetnaKoorY(),izBaze.getSirina(),izBaze.getVisina()));
        
        return returnObject;
    }
    
    //USE CASE VEZA
    private void save(UseCaseVeza objToSave) {
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(objToSave.getID(),objToSave.getCrtezID()));
        attrZaBazu.setOdKogaId(objToSave.getOdKoga().getID());
        attrZaBazu.setDoKogaId(objToSave.getDoKoga().getID());
        attrZaBazu.setTipVeze(objToSave.getTipVeze().name());
        
		
        Session session=null;
        Transaction tx = null;        
        try {
            //session factory se dobija preko parametra, pa se otvara sesija
            session = sessionFactory.openSession();
            //zapocinje se transakcija        
             tx = session.beginTransaction();

            session.save(attrZaBazu);
            //treba updateovati brojac veza u crtezu, jer je dodata nova veza
             //brojac COUNTER2
            Query query= session.createQuery("update CrtezDb k set k.counter2 = k.counter2 +1 where k.id = :crtezID ");
            query.setParameter("crtezID",objToSave.getCrtezID());
            query.executeUpdate();
         
             //zavrsava se transakcija
             tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    private void update(UseCaseVeza objToUpdate) {
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(objToUpdate.getID(),objToUpdate.getCrtezID()));
        attrZaBazu.setOdKogaId(objToUpdate.getOdKoga().getID());
        attrZaBazu.setDoKogaId(objToUpdate.getDoKoga().getID());
        attrZaBazu.setTipVeze(objToUpdate.getTipVeze().name());
        
		
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
    private void delete(UseCaseVeza objToDelete) {
        UseCaseKonekcijaDb attrZaBazu= new UseCaseKonekcijaDb();
        attrZaBazu.setId(new UseCaseKonekcijaDbId(objToDelete.getID(),objToDelete.getCrtezID()));
      
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
    private UseCaseVeza getUseCaseVezaByID(int[] idComponents) {
        Session session=null;
        Transaction tx = null;
        UseCaseKonekcijaDb ucIzBaze=null;
        UseCaseVeza returnObject=new UseCaseVeza();
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
        returnObject.setCrtezID(ucIzBaze.getId().getCrtezId());
	returnObject.setID(ucIzBaze.getId().getId());
        returnObject.setTipVeze(UseCaseConnType.valueOf(ucIzBaze.getTipVeze()));
        
        //od koga useCase
	returnObject.setOdKoga(new UseCase(returnObject.getCrtezID(),ucIzBaze.getOdKogaId()));
      
        //do koga useCase
	returnObject.setDoKoga(new UseCase(returnObject.getCrtezID(),ucIzBaze.getDoKogaId()));
        
        return returnObject;
    }


}
