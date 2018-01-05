/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import businessLogic.*;
import enumerations.VisibilityTypeEnum;
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
       
        SessionFactory factory;
        Session session=null;
        Transaction tx = null;
        factory = NewHibernateUtil.getSessionFactory();
        
        Metod m= new Metod();
        m.setCrtezID(0);
        m.setKlasaID(111);
        m.setID(666);
        m.setIsAbstract(true);
        m.setIsStatic(false);
        m.setNaziv("UpdateMetoda");
        m.setVidljivost(VisibilityTypeEnum.PRIVATE);
        m.setPovratnaVrednost("void");
        
        Argument arg1=new Argument();
        arg1.setCrtezID(0);
        arg1.setKlasaID(111);
        arg1.setMetodID(666);
        arg1.setID(0);
        arg1.setNaziv("pomArg");
        arg1.setTip("bool");
        m.dodajArgument(arg1);
        
        Argument arg2=new Argument();
        arg2.setCrtezID(0);
        arg2.setKlasaID(111);
        arg2.setMetodID(666);
        arg2.setID(31);
        arg2.setNaziv("FUCKFUCK");
        arg2.setTip("bla");
        m.dodajArgument(arg2);
        
        arg2.delete(factory);
       // m.save(factory);
//        try 
//        {
//            factory = NewHibernateUtil.getSessionFactory();
//            session = factory.openSession();
//            tx = session.beginTransaction();
//
//            tx.commit();
//      } catch (Exception e) {
//         if (tx!=null) tx.rollback();
//         e.printStackTrace(); 
//      } finally {
//         session.close(); 
//      }      
    }
    
}
