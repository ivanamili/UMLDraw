/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import businessLogic.ClassDiagrams.Klasa;
import businessLogic.ClassDiagrams.ClassDiagramVeza;
import businessLogic.*;
import enumerations.ClassConnTypeEnum;
import enumerations.UseCaseConnType;
import enumerations.VisibilityTypeEnum;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jhotdraw.draw.AttributeKey;
import store.entity.*;

import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.RectangleFigure;

/**
 *
 * @author Korisnik
 */
public class UMLDraw {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EllipseFigure rec= new EllipseFigure(999,10,30,20);
        Rectangle2D.Double x= rec.getBounds();
        
       
        SessionFactory factory;
        Session session=null;
        Transaction tx = null;
        factory = NewHibernateUtil.getSessionFactory();
        
        int[] idComp={0,1919};
        
//          Interfejs it= new Interfejs();
//          it.setNaziv("IIIIIIII");
//          it.setCrtezID(0);
//          it.setID(1000);
//          
//          Metod met= new Metod();
//          met.setNaziv("metodaIme");
//          met.setPovratnaVrednost("void");
//          met.setIsAbstract(true);
//          met.setIsStatic(false);
//          met.setVidljivost(VisibilityTypeEnum.PRIVATE);
//          met.setArgumentiCounter(0);
//          it.dodajMetodu(met);
//          
//          met= new Metod();
//          met.setNaziv("METMETMETODA");
//          met.setPovratnaVrednost("INT");
//          met.setIsAbstract(true);
//          met.setIsStatic(false);
//          met.setVidljivost(VisibilityTypeEnum.PUBLIC);
//          met.setArgumentiCounter(0);
//          
//          Argument arg= new Argument();
//          arg.setNaziv("testARgument");
//          arg.setTip("string");
//          
//          it.dodajMetodu(met);
//          met.dodajArgument(arg);
          
          //it.save(factory);
          //klas.save(factory);
          
          ClassDiagramVeza veza= new ClassDiagramVeza();
          veza.setCrtezID(0);
          veza.setID(1919);
          veza.setTip(ClassConnTypeEnum.COMPOSITION);
          Klasa k= new Klasa();
          k.setID(90);
          k.setCrtezID(0);
          veza.setOdKoga(k);
          
         Klasa k1= new Klasa();
          k1.setID(55);
          k1.setCrtezID(0);
          veza.setDoKoga(k1);
          
          //veza.save(factory);
          
          ClassDiagramVeza vez2=new ClassDiagramVeza();
          vez2.getByID(idComp, factory);
        System.out.println("Success!");
        
    }
    
}