/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.usecase;

import businessLogic.CommonClasses.Crtez;
import draw.usecase.figures.AktorConnectionFigure;
import draw.usecase.figures.AktorFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.IncludeConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
public class UseCaseApplicationModel extends DefaultApplicationModel {
    private final static double[] scaleFactors = {5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10};
    private DefaultDrawingEditor sharedEditor;
    private HashMap<String,Action> actions;
    
    private String loggedUser;
    private Crtez createdCrtez;
    private String nextUser;
    
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
    
    public UseCaseApplicationModel(String loggedUser, Crtez crtez,String nextUser) {
        this.loggedUser=loggedUser;
        this.createdCrtez=crtez;
        this.nextUser=nextUser;
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
        ResourceBundleUtil useCaseLabels=ResourceBundleUtil.getLAFBundle("draw.usecase.UsecaseLabels");
        
        int orrientation=tb.getOrientation();
        
        ToolBarButtonFactory.addSelectionToolTo(tb, editor);
        tb.addSeparator();
       
//        attributes = new HashMap<AttributeKey,Object>();
//        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
//        attributes.put(AttributeKeys.STROKE_COLOR, Color.red);
//        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
//        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new TaskFigure(), attributes), "createTask", labels);
//
//        attributes = new HashMap<AttributeKey,Object>();
//        attributes.put(AttributeKeys.STROKE_COLOR, new Color(0x000099));
//        ToolBarButtonFactory.addToolTo(tb, editor, new ConnectionTool(new DependencyFigure(), attributes), "createDependency", labels);
//        tb.addSeparator();


        //USE CASE TOOL
        attributes = new HashMap<AttributeKey,Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new UseCaseFigure(), attributes), "createUseCase", useCaseLabels);
        
        //AKTOR TOOL
        attributes = new HashMap<AttributeKey,Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new AktorFigure(), attributes), "createAktor", useCaseLabels);
        
        ConnectionTool cnt;
        ConnectionFigure lc;
        
        //AKTOR KONEKCIJA TOOL
        ToolBarButtonFactory.addToolTo(tb, editor, new ConnectionTool(new AktorConnectionFigure()), "createAKonekcija", useCaseLabels);
        //INCLUDE KONEKCIJA TOOL
        ToolBarButtonFactory.addToolTo(tb, editor, cnt= new ConnectionTool(new IncludeConnectionFigure()), "createIncludeKonekcija", useCaseLabels);
        //EXTEND KONEKCIJA TOOL
        ToolBarButtonFactory.addToolTo(tb, editor, cnt= new ConnectionTool(new ExtendConnectionFigure()), "createExtendKonekcija", useCaseLabels);
        //ovo omogucava da se linija presavija na centru automatski kako se prevlace figure i omogucava bolje rasporedjivanje
        //lc =  cnt.getPrototype();
        //lc.setLiner(new ElbowLiner());

        
    }
    /**
     * Creates toolbars for the application.
     * This class always returns an empty list. Subclasses may return other
     * values.
     */
    public java.util.List<JToolBar> createToolBars(Application a, Project pr) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.samples.pert.Labels");
        UseCaseProject p = (UseCaseProject) pr;
        
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
        tb.setName("Use Case tools");
        list.add(tb);
        
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
        p.getUmlDrawing().setupUmlDrawing(loggedUser, createdCrtez,nextUser);
        
        return list;
    }
}
