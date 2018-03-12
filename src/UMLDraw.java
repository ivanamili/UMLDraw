/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import businessLogic.*;
import businessLogic.ClassDiagrams.*;
import businessLogic.CommonClasses.*;
import businessLogic.UseCaseDiagrams.*;
import enumerations.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.*;
import org.jhotdraw.draw.AttributeKey;
import store.entity.*;

import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.RectangleFigure;
import store.manager.PersistenceManager;

/**
 *
 * @author Korisnik
 */
public class UMLDraw {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException, ClassNotFoundException {
        
//       Object obj = new ClassDiagramVeza();
//       Class cla= obj.getClass();
//       
//       ByteArrayOutputStream bos= new ByteArrayOutputStream();
//       ObjectOutputStream out= new ObjectOutputStream(bos);
//       out.writeObject(obj);
//       
//       ByteArrayInputStream bis= new ByteArrayInputStream(bos.toByteArray());
//       ObjectInputStream in= new ObjectInputStream(bis);
//       Object obj2= in.readObject();
//       
//        System.out.println(obj2 instanceof ClassDiagramVeza);
        
        PersistenceManager manager= new PersistenceManager();
        Atribut attr=new Atribut();
        attr.setCrtezID(8);
        attr.setKlasaID(0);
        attr.setID(0);
        attr.setNaziv("ovo zaboravljeno");
        attr.setIsStatic(true);
        attr.setTip("bar");
        attr.setVidljivost(VisibilityTypeEnum.PRIVATE);
        
        
        
        Argument arg=new Argument();
        arg.setCrtezID(8);
        arg.setKlasaID(0);
        arg.setMetodID(0);
        arg.setID(69);
        arg.setNaziv("ATRIBBBBJUT");
        arg.setTip("enum");
//        
        Metod met=new Metod();
        met.setCrtezID(8);
        met.setKlasaIliInterfejsID(0);
        met.setID(0);
        met.setNaziv("METODA ZA BRISANJE");
        met.setVidljivost(VisibilityTypeEnum.PUBLIC);
        met.setPovratnaVrednost("void");
        met.setIsStatic(true);
        met.setIsAbstract(false);
        
        met.dodajArgument(arg);
        
        Klasa klas=new Klasa();
        klas.setCrtezID(8);
        klas.setID(0);
        klas.setIme("KLASA ZA BRISANJE");
        klas.setIsStatic(true);
        
        klas.dodajAtribut(attr);
        klas.dodajMetodu(met);
        
        Interfejs interf=new Interfejs();
        interf.setCrtezID(8);
        interf.setID(1919);
        interf.setNaziv("PROMENJENO IME");
        
        ClassDiagramVeza vezzz=new ClassDiagramVeza();
        vezzz.setCrtezID(8);
        vezzz.setID(11);
        vezzz.setTip(ClassConnTypeEnum.IMPLEMENTATION);
        vezzz.setOdKoga(klas);
        vezzz.setDoKoga(interf);
        
        Aktor akt=new Aktor();
        akt.setCrtezID(1);
        akt.setID(9);
        akt.setNaziv("GHOST");
        akt.setOkvir(new RectangleFigure(10,10,10,10));
        
        
        
        UseCase use=new UseCase();
        use.setCrtezID(1);
        use.setID(10);
        use.setNaziv("PROBA USE CASE");
        use.setElipsa(new EllipseFigure(2,2,2,2));
        
        
        AktorVeza veza=new AktorVeza();
        veza.setCrtezID(1);
        veza.setID(1111);
        veza.setAktor(akt);
        veza.setUseCase(use);
        
        UseCaseVeza vezica=new UseCaseVeza();
        vezica.setOdKoga(use);
        vezica.setDoKoga(use);
        vezica.setTipVeze(UseCaseConnType.INCLUDE);
        
        int[] one={2};
        int[] two={0,2};
        
        
        
        Korisnik koris=new Korisnik();
        koris.setIme("Bla bla truc");
        koris.setSifra("2djdjdjdjd");
        
        Crtez crt= new Crtez();
        crt.setID(8);
        crt.setImeAutora("Ivana");
        crt.setNaslov("Drugi crtez");
        crt.setTip(DiagramTypeEnum.USECASE);
//        crt.dodajElement(akt);
//        crt.dodajElement(use);
//        crt.dodajVezu(veza);
        crt.dodajVezu(vezica);
        
        manager.saveAll(crt);
        
        
        
        
        manager.close();
    }
    
}