/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import businessLogic.*;
import businessLogic.ClassDiagrams.*;
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
        attr.setCrtezID(0);
        attr.setKlasaID(0);
        attr.setID(0);
        attr.setNaziv("ovo zaboravljeno");
        attr.setIsStatic(true);
        attr.setTip("bar");
        attr.setVidljivost(VisibilityTypeEnum.PRIVATE);
        
        int[] ids={1,1};
        
        Argument arg=new Argument();
        arg.setCrtezID(0);
        arg.setKlasaID(1000);
        arg.setMetodID(1);
        arg.setID(69);
        arg.setNaziv("ATRIBBBBJUT");
        arg.setTip("enum");
//        
        Metod met=new Metod();
        met.setCrtezID(0);
        met.setKlasaIliInterfejsID(0);
        met.setID(50);
        met.setNaziv("METODA ZA BRISANJE");
        met.setVidljivost(VisibilityTypeEnum.PUBLIC);
        met.setPovratnaVrednost("void");
        met.setIsStatic(true);
        met.setIsAbstract(false);
        
        Klasa klas=new Klasa();
        klas.setCrtezID(0);
        klas.setID(999);
        klas.setIme("KLASA ZA BRISANJE");
        klas.setIsStatic(true);
        
        Interfejs interf=new Interfejs();
        interf.setCrtezID(0);
        interf.setID(1919);
        interf.setNaziv("PROMENJENO IME");
        
        ClassDiagramVeza vezzz=new ClassDiagramVeza();
        vezzz.setCrtezID(0);
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
        
        int[] one={0,1};
        int[] two={0,2};
        
        UseCase jedan,dva;
        jedan=(UseCase)manager.getById(one, RuntimeClassEnum.USE_CASE);
        dva=(UseCase)manager.getById(one, RuntimeClassEnum.USE_CASE);
        
        UseCaseVeza konekc=new UseCaseVeza();
        konekc.setCrtezID(0);
        konekc.setID(909090);
        konekc.setOdKoga(jedan);
        konekc.setDoKoga(dva);
        konekc.setTipVeze(UseCaseConnType.INCLUDE);
        
        //manager.save(konekc,RuntimeClassEnum.USE_CASE_VEZA);
        manager.delete(konekc, RuntimeClassEnum.USE_CASE_VEZA);
        
        int[] trazi={0,99};
        UseCaseVeza blatruc=(UseCaseVeza) manager.getById(trazi, RuntimeClassEnum.USE_CASE_VEZA);
        
        
        manager.close();
    }
    
}