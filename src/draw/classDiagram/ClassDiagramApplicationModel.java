/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram;

import businessLogic.CommonClasses.Crtez;
import draw.classDiagram.figures.AgregationConnectionFigure;
import draw.classDiagram.figures.ClassFigure;
import draw.classDiagram.figures.CompositionConnectionFigure;
import draw.classDiagram.figures.GeneralisationConnectionFigure;
import draw.classDiagram.figures.ImplementationConnectionFigure;
import draw.classDiagram.figures.InterfaceFigure;
import draw.commonClasses.DisableAllInContainer;
import draw.usecase.*;
import draw.usecase.figures.AktorConnectionFigure;
import draw.usecase.figures.AktorFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.IncludeConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;
import org.jhotdraw.app.Project;
import org.jhotdraw.app.action.CloseAction;
import org.jhotdraw.app.action.ExportAction;
import org.jhotdraw.app.action.ProjectPropertyAction;
import org.jhotdraw.app.action.ToggleProjectPropertyAction;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.ConnectionTool;
import org.jhotdraw.draw.CreationTool;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.ElbowLiner;
import org.jhotdraw.draw.LineConnectionFigure;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.draw.TextAreaTool;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.draw.action.ApplyAttributesAction;
import org.jhotdraw.draw.action.PickAttributesAction;
import org.jhotdraw.draw.action.ToolBarButtonFactory;

import org.jhotdraw.samples.pert.PertProject;
import org.jhotdraw.samples.pert.figures.DependencyFigure;
import org.jhotdraw.samples.pert.figures.TaskFigure;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Korisnik
 */
public class ClassDiagramApplicationModel extends DefaultApplicationModel {
    private final static double[] scaleFactors = {5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10};
    private DefaultDrawingEditor sharedEditor;
    private HashMap<String,Action> actions;
    
    private String loggedUser;
    private Crtez createdCrtez;
    
    private static class ToolButtonListener implements ItemListener {
        private Tool tool;
        private DrawingEditor editor;
        public ToolButtonListener(Tool t, DrawingEditor editor) {
            this.tool = t;
            this.editor = editor;
        }
        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                editor.setTool(tool);
            }
        }
    }
    
    public ClassDiagramApplicationModel(String loggedUser, Crtez crtez) {
        this.loggedUser=loggedUser;
        this.createdCrtez=crtez;
    }
    
    public void initApplication(Application a) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.pert.Labels");
        AbstractAction aa;
        
        putAction(ExportAction.ID, new ExportAction(a));
        putAction("toggleGrid", aa = new ToggleProjectPropertyAction(a, "gridVisible"));
        drawLabels.configureAction(aa, "alignGrid");
        for (double sf : scaleFactors) {
            putAction((int) (sf*100)+"%",
                    aa = new ProjectPropertyAction(a, "scaleFactor", Double.TYPE, new Double(sf))
                    );
            aa.putValue(Action.NAME, (int) (sf*100)+" %");
            
        }
    }
    
    public DefaultDrawingEditor getSharedEditor() {
        if (sharedEditor == null) {
            sharedEditor = new DefaultDrawingEditor();
        }
        return sharedEditor;
    }
    
    private void addCreationButtonsTo(JToolBar tb, final DrawingEditor editor) {
        // AttributeKeys for the entitie sets
        HashMap<AttributeKey,Object> attributes;        
        
        
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.pert.Labels");
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        //za nase nazive kontrola, i ostale potrepstine
        ResourceBundleUtil classDiagramLabels=ResourceBundleUtil.getLAFBundle("draw.classDiagram.ClassDiagramLabels");
        
        int orrientation=tb.getOrientation();
        
        ToolBarButtonFactory.addSelectionToolTo(tb, editor);
        tb.addSeparator();
        
        //klase i interfjesi
        attributes = new HashMap<AttributeKey,Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new ClassFigure(), attributes), "createClass", classDiagramLabels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new InterfaceFigure(), attributes), "createInterface", classDiagramLabels);
        
        ConnectionTool cnt;
        ConnectionFigure lc;
        ToolBarButtonFactory.addToolTo(tb, editor, cnt= new ConnectionTool(new GeneralisationConnectionFigure()), "createGeneralisation", classDiagramLabels);
        //ovo omogucava da se linija presavija na centru automatski kako se prevlace figure i omogucava bolje rasporedjivanje
        lc =  cnt.getPrototype();
        lc.setLiner(new ElbowLiner());
        
        ConnectionTool cnt2;
        ConnectionFigure lc2;
        ToolBarButtonFactory.addToolTo(tb, editor,cnt2=new ConnectionTool(new ImplementationConnectionFigure()), "createImplementation", classDiagramLabels);
        //ovo omogucava da se linija presavija na centru automatski kako se prevlace figure i omogucava bolje rasporedjivanje
        lc2 =  cnt2.getPrototype();
        lc2.setLiner(new ElbowLiner());
        
        ConnectionTool cnt3;
        ConnectionFigure lc3;
        ToolBarButtonFactory.addToolTo(tb, editor,cnt3=new ConnectionTool(new AgregationConnectionFigure()), "createAgregation", classDiagramLabels);
        //ovo omogucava da se linija presavija na centru automatski kako se prevlace figure i omogucava bolje rasporedjivanje
//        lc3 =  cnt3.getPrototype();
//        lc3.setLiner();

        ToolBarButtonFactory.addToolTo(tb, editor,new ConnectionTool(new CompositionConnectionFigure()), "createComposition", classDiagramLabels);

        
    }
    /**
     * Creates toolbars for the application.
     * This class always returns an empty list. Subclasses may return other
     * values.
     */
    public java.util.List<JToolBar> createToolBars(Application a, Project pr) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.pert.Labels");
        ClassDiagramProject p = (ClassDiagramProject) pr;
        
        
        
        //OVDE IMAMO PRISTUP PROJEKTU
        
        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getDrawingEditor();
        }
        
        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        JToolBar tb;
        tb = new JToolBar(JToolBar.VERTICAL);
        addCreationButtonsTo(tb, editor);
        tb.setName("Class Diagram Tools");
        
        //add button for finishing drawing and letting others write.
        tb.addSeparator();
        JButton finished= new JButton("      Done       ");
        finished.setFont(new Font("Arial", Font.BOLD, 20));
        //zakaci listener na funkciju iz UMLDrawing
        finished.addActionListener((ActionEvent e) -> {
            //kazi crtezu da je gotovo sa crtanjem
            p.getUmlDrawing().handleDoneDrawing();
        });        
        tb.add(finished);
        //da bi is drawinga mogli da disablujemo toolbar kad se crtanje zavrsi
        //i enablujemo kada dodje red da opet crta
        p.getUmlDrawing().setToolBar(tb);
        p.getUmlDrawing().setupUmlDrawing(loggedUser, createdCrtez);
        
        list.add(tb);
        
        return list;
    }
}
