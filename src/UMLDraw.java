/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import businessLogic.*;
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
        
        int[] idComp={0,111,0};
        
//        Atribut attr= new Atribut();
//        attr.setCrtezID(0);
//        attr.setKlasaID(111);
//        attr.setID(0);
//        attr.setNaziv("RADIANS");
//        attr.setTip("float");
//        attr.setVidljivost(VisibilityTypeEnum.PUBLIC);
//        attr.setIsStatic(false);
//        
//        attr.save(factory);

          Korisnik k= new Korisnik();
          
          k.setIme("Ivana");
          k.setSifra("bla2222");
          Crtez c= new Crtez();
          c.setID(1);
          k.setTrenutniCrtez(c);
          
          k.delete(factory);

        System.out.println("Success!");
        
    }
    
}