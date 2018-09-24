/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.figures;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.ClassDiagrams.Interfejs;
import businessLogic.ClassDiagrams.Klasa;
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
import draw.usecase.auxiliaryClasses.UseCaseVerticalLayouter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.Action;
import org.jhotdraw.draw.AbstractFigureListener;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import org.jhotdraw.draw.BoxHandleKit;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Insets2DDouble;
import org.jhotdraw.samples.pert.figures.SeparatorLineFigure;

/**
 *
 * @author Korisnik
 */
public class InterfaceFigure extends AbstractDiagramElementFigure implements IMethodContainer{

    private Interfejs interfejs;
    ListFigure methods = new ListFigure();
    
    Insets2DDouble defaultInsets = new Insets2DDouble(4,8,4,8);
    
    //adapter za ime klase
     private static class InterfaceNameAdapter extends AbstractFigureListener {
        private InterfaceFigure target;
        public InterfaceNameAdapter(InterfaceFigure target) {
            this.target = target;
        }
        public void figureAttributeChanged(FigureEvent e) {
            if(e.getNewValue() instanceof String)
                target.interfejs.setNaziv((String)e.getNewValue());
            // We could fire a property change event here, in case
            // some other object would like to observe us.
            //target.firePropertyChange("name", e.getOldValue(), e.getNewValue());
        }
    }
    
    @Override
    public AbstractDiagramElement getDataObject() {
        return this.interfejs;
    }
    
    public InterfaceFigure(){
        super(new RectangleFigure());
        interfejs= new Interfejs();
        interfejs.setNaziv("New Interface");
        interfejs.setOkvir((RectangleFigure)this.getPresentationFigure());
        initFigure();
    }
    
    private void initFigure() {
        setLayouter(new UseCaseVerticalLayouter());
        
        //labela
        TextFigure interfLabel= new TextFigure(){
            @Override
             public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
                double width=Math.abs(lead.x-anchor.x);
                Rectangle2D textbounds=this.getTextLayout().getBounds();
                double x= (width-textbounds.getWidth())/2.0;
                origin = new Point2D.Double(anchor.x+x, anchor.y);
            }
        };
        interfLabel.setText("<< Interface >>");
        interfLabel.setEditable(false);
        interfLabel.setAttribute(FONT_ITALIC,true );
        
        //ime interfejsa
        TextFigure name= new TextFigure(){
            @Override
             public void basicSetBounds(Point2D.Double anchor, Point2D.Double lead) {
                double width=Math.abs(lead.x-anchor.x);
                Rectangle2D textbounds=this.getTextLayout().getBounds();
                double x= (width-textbounds.getWidth())/2.0;
                origin = new Point2D.Double(anchor.x+x, anchor.y);
            }
        };
         name.setText(this.interfejs.getNaziv());
        //ovde treba za static i za abstract da se ubaci stil
        LAYOUT_INSETS.set(name, defaultInsets);
        LAYOUT_INSETS.set(interfLabel, defaultInsets);
        
        add(interfLabel);
        add(name);
        //menja ime interfejsa u bussiness objektu kad se promeni tekst
        name.addFigureListener(new InterfaceNameAdapter(this));
        
        add(new SeparatorLineFigure());
        
        LAYOUT_INSETS.set(methods, defaultInsets);
        add(methods);//child 5
        
       
       

        
    }
    
    @Override
    public Collection<Action> getActions(Point2D.Double p){
        
        Collection<Action> actions= new LinkedList<Action>();
        
        //u slucaju da je kliknuto na neki metod
        Figure possibleChild2= methods.findChild(p);
        if(possibleChild2!=null){
            actions.add(new ChangeMethodAction((MethodFigure)possibleChild2));
            actions.add(new DeleteMethodAction((MethodFigure)possibleChild2));            
        }
        
        //akcije na nivou interfejsa  
        actions.add(new AddMethodAction(this));
        
        return actions;
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
        InterfaceFigure that= new InterfaceFigure();
        that.interfejs= (this.interfejs==null)? null : (Interfejs)this.interfejs.clone();
        that.interfejs.setOkvir((RectangleFigure)this.getPresentationFigure());
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
     
      @Override
    public void DeleteMethod(MethodFigure methodToDelete) {
        this.interfejs.obrisiMetodu(methodToDelete.getMethod().getID());
        this.methods.remove(methodToDelete);
    }

    @Override
    public void AddNewMethod(MethodFigure newMethod) {
         //prvo dodavanje u model
         this.interfejs.dodajMetodu(newMethod.getMethod());
         
         //setovanje parenta zbog brisanja
         newMethod.setParent((IMethodContainer)this);
         
         //dodavanje same figure u crtez
         methods.add(newMethod);
    }

    
    
}
