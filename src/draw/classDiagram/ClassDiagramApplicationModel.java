/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram;

import draw.classDiagram.figures.ClassFigure;
import draw.usecase.*;
import draw.usecase.figures.AktorConnectionFigure;
import draw.usecase.figures.AktorFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.IncludeConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import java.awt.Color;
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
    
    public ClassDiagramApplicationModel() {
        this.putAction(CloseAction.ID, null);
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
       



        //USE CASE TOOL
        attributes = new HashMap<AttributeKey,Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new ClassFigure(), attributes), "createClass", classDiagramLabels);
        
        
        ConnectionTool cnt;
        ConnectionFigure lc;
        
//        ///EXTEND KONEKCIJA TOOL
//        ToolBarButtonFactory.addToolTo(tb, editor, cnt= new ConnectionTool(new ExtendConnectionFigure()), "createExtendKonekcija", useCaseLabels);
//        //ovo omogucava da se linija presavija na centru automatski kako se prevlace figure i omogucava bolje rasporedjivanje
//        //lc =  cnt.getPrototype();
//        //lc.setLiner(new ElbowLiner());

        
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
        list.add(tb);
        
        return list;
    }
}
