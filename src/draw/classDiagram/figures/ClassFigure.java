/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.Klasa;
import businessLogic.UseCaseDiagrams.UseCase;
import draw.classDiagram.figures.attribute.AddAttributeAction;
import draw.classDiagram.figures.attribute.AttributeFigure;
import draw.classDiagram.figures.attribute.ChangeAttributeAction;
import draw.classDiagram.figures.attribute.DeleteAttributeAction;
import draw.commonClasses.AbstractDiagramElementFigure;
import draw.usecase.auxiliaryClasses.UseCaseVerticalLayouter;
import draw.usecase.figures.UseCaseFigure;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.Action;
import org.jhotdraw.app.action.DuplicateAction;
import org.jhotdraw.draw.AbstractFigureListener;
import org.jhotdraw.draw.BoxHandleKit;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.VerticalLayouter;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Insets2DDouble;
import org.jhotdraw.samples.pert.figures.SeparatorLineFigure;

/**
 *
 * @author Korisnik
 */
public class ClassFigure extends AbstractDiagramElementFigure {
    
    private Klasa klasa;
    ListFigure attributes= new ListFigure();
    ListFigure methods = new ListFigure();
    
    //adapter za ime klase
     private static class ClassNameAdapter extends AbstractFigureListener {
        private ClassFigure target;
        public ClassNameAdapter(ClassFigure target) {
            this.target = target;
        }
        public void figureAttributeChanged(FigureEvent e) {
            if(e.getNewValue() instanceof String)
                target.klasa.setIme((String)e.getNewValue());
            // We could fire a property change event here, in case
            // some other object would like to observe us.
            //target.firePropertyChange("name", e.getOldValue(), e.getNewValue());
        }
    }
    
    Insets2DDouble defaultInsets = new Insets2DDouble(4,8,4,8);

    @Override
    public AbstractDiagramElement getDataObject() {
        return this.klasa;
    }
    
    public ClassFigure(){
        super(new RectangleFigure());
        klasa= new Klasa();
        klasa.setIme("New Class");
        klasa.setOkvir((RectangleFigure)this.getPresentationFigure());
        initFigure();
        
    }
    
    //ovaj metod treba da vrati akcije koje su specificne za konkretnu figuru
    //njih ce da pokupi DelegationSelectionTool kad kreira popup menu i stavice ih na pocetak
    //konkretno, treba da budu stavljene akcije za dodavanje atributa i metoda
    //posto postoji tacka p na koju je kliknuto, trebalo bi da se proveri pa ako je 
    //kliknuto na neki atribut da se doda po jedna akcija za izmeni atribut i za obrisi atribut
    //!!!!!!!!!!!IMPORTANT!!!!!!!!!!!!!!!1
    @Override
    public Collection<Action> getActions(Point2D.Double p){
        
        Collection<Action> actions= new LinkedList<Action>();
        actions.add(new AddAttributeAction(this));
        
        //proveri da slucajno nije klinuto na neki atribut
        Figure possibleChild= attributes.findChild(p);
        if(possibleChild!=null)
        {
            actions.add(new ChangeAttributeAction((AttributeFigure)possibleChild));
            actions.add(new DeleteAttributeAction((AttributeFigure)possibleChild));
        }
        
        return actions;
    }
    
    private void initFigure(){
        setLayouter(new UseCaseVerticalLayouter());
        
        TextFigure name= new TextFigure(){
            @Override
             public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
            double width=Math.abs(lead.x-anchor.x);
            Rectangle2D textbounds=this.getTextLayout().getBounds();
            double x= (width-textbounds.getWidth())/2.0;
            origin = new Point2D.Double(anchor.x+x, anchor.y);
    }
        };
        name.setText(this.klasa.getIme());
        //ovde treba za static i za abstract da se ubaci stil
        add(name);
        //menja ime klase u bussiness objektu kad se promeni tekst
        name.addFigureListener(new ClassNameAdapter(this));
        
        add(new SeparatorLineFigure());
        
        SeparatorLineFigure sep;
        
        add(attributes);//child 3
        add(sep=new SeparatorLineFigure());
        add(methods);//child 5
        
        LAYOUT_INSETS.set(attributes, defaultInsets);
        LAYOUT_INSETS.set(methods, defaultInsets);
        LAYOUT_INSETS.set(name, defaultInsets);
        
        Insets2DDouble insets = new Insets2DDouble(4,0,4,0);
        LAYOUT_INSETS.set(sep, insets);
    }
    
    public ListFigure getAttributesContainer(){
        return (ListFigure) getChild(3);
    }
    
    public ListFigure getMethodsContainer(){
        return (ListFigure) getChild(5);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        Rectangle2D.Double bounds = (Rectangle2D.Double) this.getPresentationFigure().getBounds().clone();
        return bounds;
    }
    
    @Override
    public Dimension2DDouble getPreferredSize() {
        Rectangle2D.Double r = getBounds();
        double w,h;
        w=r.width;
        h=r.height;
        return new Dimension2DDouble (w,h);
    }
    
    @Override
    public GraphicalCompositeFigure clone(){
        ClassFigure that= new ClassFigure();
        that.klasa= (this.klasa==null)? null : (Klasa)this.klasa.clone();
        that.klasa.setOkvir((RectangleFigure)this.getPresentationFigure());
        //dodati eventuelne listenere
        return that;
    }
    
     public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        if (detailLevel == 0) {
            BoxHandleKit.addBoxHandles(this, handles);
        }
        return handles;
    }
     
     //Metodi za rad sa atributima
     //dodaje atribut u klasu i na dijagram
     public void AddAttribute(AttributeFigure newAttribute){
         
         //prvo dodati atribut u klasu, pa onda dodati u dijagram
         //za svaki slucaj da budemo sigurni da ce atribut biti u klasi
         //kada se, nadam se, aktivira listener onFigurechanged u UmlDrawing klasi
         this.klasa.dodajAtribut(newAttribute.getAtribut());
         
         //setuj ovu klasu kao parent-a, treba zbog brisanja atributa
         newAttribute.setParent(this);
         
         //dodaj komponentu na dijagram
         //ovo BI TREBALO da aktivira listener u UMLDrawing da je nesto promenjeno
         //i to bi trebalo da se uhvati na nivou cele klase.
         //posto atribut ne implementira IDataFigure pa nece on direktno da se uhvati tamo
         try
         {
             attributes.add(newAttribute);
         }
         catch(Exception e)
         {
             System.out.println(e.getMessage());
         }
         
     }
     
     public void DeleteAttribute(AttributeFigure attributeToDelete){
         //obrisi atribut iz business objekta
         this.klasa.obrisiAtribut(attributeToDelete.getAtribut().getID());
         
         this.attributes.remove(attributeToDelete);
     }
    
   
    
 
    
}
