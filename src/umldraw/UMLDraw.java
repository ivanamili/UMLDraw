/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import store.entity.*;

import org.jhotdraw.draw.EllipseFigure;

/**
 *
 * @author Korisnik
 */
public class UMLDraw {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EllipseFigure ef= new EllipseFigure(8,8,10,15); 
        SessionFactory factory;       
        
        
        Crtez c= new Crtez();
        c.setId(0);
        c.setNaslov("crtez1");        
        
        Aktor a=new Aktor();
        a.setId(new AktorId(c.getId(),11));
        a.setCrtez(c);
        
        Aktor b=new Aktor();
        b.setId(new AktorId(c.getId(),22));
        b.setCrtez(c);
       
        Session session=null;
        Transaction tx = null;
        
        try {
            factory = NewHibernateUtil.getSessionFactory();
            session = factory.openSession();
            
        
         tx = session.beginTransaction();
//                session.save(c);
//                session.save(a);
//                session.save(b);
         Query query;
          query = session.createQuery("FROM Crtez");
          List<Crtez> l=query.list();
          Crtez bla=l.get(0);
          int size=bla.getAktors().size();   
          int size2= bla.getKlasas().size();
          
         tx.commit();
      } catch (Exception e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }      
    }
    
}
