/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.Atribut;
import businessLogic.ClassDiagrams.Klasa;
import businessLogic.ClassDiagrams.Metod;
import businessLogic.UseCaseDiagrams.UseCase;
import draw.classDiagram.auxiliaryClasses.IMethodContainer;
import draw.classDiagram.figures.attribute.AddAttributeAction;
import draw.classDiagram.figures.attribute.AttributeFigure;
import draw.classDiagram.figures.attribute.ChangeAttributeAction;
import draw.classDiagram.figures.attribute.DeleteAttributeAction;
import draw.classDiagram.figures.method.AddMethodAction;
import draw.classDiagram.figures.method.ChangeMethodAction;
import draw.classDiagram.figures.method.DeleteMethodAction;
import draw.classDiagram.figures.method.MethodFigure;
import draw.commonClasses.AbstractDiagramElementFigure;
import draw.commonClasses.IUpdatableFigure;
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
public class ClassFigure extends AbstractDiagramElementFigure implements IMethodContainer,IUpdatableFigure {
    
    private Klasa klasa;
    ListFigure attributes= new ListFigure();
    ListFigure methods = new ListFigure();
    TextFigure name;

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
    
    public ClassFigure(Klasa klasa) {
        super(klasa.getOkvir());
        this.klasa=klasa;
        initFigure();
    }
    
      @Override
    public void updateDiagramFigure(AbstractDiagramElement newElement) {
         if(!(newElement instanceof Klasa))
            return;
         
         Klasa element=(Klasa) newElement;
         
         this.klasa.getOkvir().setBounds(element.getOkvir().getBounds());
         this.klasa.setIsAbstract(element.getIsAbstract());
         this.klasa.setIsStatic(element.getIsStatic());
         this.getNameFigure().setText(element.getIme());
         
         //izbrisi sve atribute prvo, pa ih dodaj jedan po jedan
         this.klasa.setAtributi(element.getAtributi());
         this.klasa.setAtributCounter(element.getAtributCounter());
         
         this.getAttributesContainer().removeAllChildren();
         for(Atribut attrib : this.klasa.getAtributi()){
             AttributeFigure attribFigure= new AttributeFigure(attrib);
             this.AddAtributeToDiagramOnly(attribFigure);
         }
         
         //metode
         this.klasa.setMetode(element.getMetode());
         this.klasa.setMetodCounter(element.getMetodCounter());
         
         this.getMethodsContainer().removeAllChildren();
         for(Metod met : this.klasa.getMetode()){
             MethodFigure metFigure= new MethodFigure(met);
             this.AddMethodToDiagramOnly(metFigure);
         }
         
         this.changed();
         
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
        
        
        //proveri da slucajno nije klinuto na neki atribut
        Figure possibleChild= this.getAttributesContainer().findChild(p);
        if(possibleChild!=null )
        {
            actions.add(new ChangeAttributeAction((AttributeFigure)possibleChild));
            actions.add(new DeleteAttributeAction((AttributeFigure)possibleChild));
        }
        
        //u slucaju da je kliknuto na neki metod
        Figure possibleChild2= this.getMethodsContainer().findChild(p);
        if(possibleChild2!=null){
            actions.add(new ChangeMethodAction((MethodFigure)possibleChild2));
            actions.add(new DeleteMethodAction((MethodFigure)possibleChild2));            
        }
        
        //akcije na nivou klase        
        actions.add(new AddAttributeAction(this));
        actions.add(new AddMethodAction(this));
        
        return actions;
    }
    
    private void initFigure(){
        setLayouter(new UseCaseVerticalLayouter());
        
        name= new TextFigure(){
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
        LAYOUT_INSETS.set(name, defaultInsets);
        add(name);//child(0)
        //menja ime klase u bussiness objektu kad se promeni tekst
        name.addFigureListener(new ClassNameAdapter(this));
        
        add(new SeparatorLineFigure());
        
        SeparatorLineFigure sep=new SeparatorLineFigure();
        Insets2DDouble insets = new Insets2DDouble(4,0,4,0);
        LAYOUT_INSETS.set(sep, insets);
        
        LAYOUT_INSETS.set(attributes, defaultInsets);
        LAYOUT_INSETS.set(methods, defaultInsets);
        
        add(attributes);//child(2)
        add(sep);
        add(methods);//child (4)
    }
    
    private TextFigure getNameFigure(){
        return (TextFigure) getChild(0);
    }
    
    public ListFigure getAttributesContainer(){
        return (ListFigure) getChild(2);
    }
    
    public ListFigure getMethodsContainer(){
        return (ListFigure) getChild(4);
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
        ClassFigure that= (ClassFigure) super.clone();
        that.klasa= (this.klasa==null)? null : (Klasa)this.klasa.clone();
        that.klasa.setOkvir((RectangleFigure)that.getPresentationFigure());
        that.getNameFigure().addFigureListener(new ClassNameAdapter(that));
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
             this.getAttributesContainer().add(newAttribute);
         }
         catch(Exception e)
         {
             System.out.println(e.getMessage());
         }
         
     }
     
     //ova se zove iz updateFigure metode
     public void AddAtributeToDiagramOnly(AttributeFigure newAttribute){
          newAttribute.setParent(this);
          this.getAttributesContainer().add(newAttribute);
     }
     
     public void DeleteAttribute(AttributeFigure attributeToDelete){
         //obrisi atribut iz business objekta
         this.klasa.obrisiAtribut(attributeToDelete.getAtribut().getID());
         
         this.getAttributesContainer().remove(attributeToDelete);
     }
     
    @Override
    public void AddNewMethod(MethodFigure newMethod) {
        //prvo dodavanje u model
         this.klasa.dodajMetodu(newMethod.getMethod());
         
         //setovanje parenta zbog brisanja
         newMethod.setParent((IMethodContainer)this);
         
         //dodavanje same figure u crtez
         this.getMethodsContainer().add(newMethod);
    }
    
    public void AddMethodToDiagramOnly(MethodFigure newMethod){
        newMethod.setParent((IMethodContainer)this);
         this.getMethodsContainer().add(newMethod);
    }
     
    @Override
    public void DeleteMethod(MethodFigure methodToDelete) {
        this.klasa.obrisiMetodu(methodToDelete.getMethod().getID());
        this.getMethodsContainer().remove(methodToDelete);
    }
     
}
