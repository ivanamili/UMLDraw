/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram;

import draw.usecase.*;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultOSXApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.draw.DrawApplicationModel;

/**
 *
 * @author Korisnik
 */
public class ClassMainDraw {
    public static void main(String[] args) {
        Application app;
        String os = System.getProperty("os.name").toLowerCase();
        app = new DefaultSDIApplication();
        
        
        
        ClassDiagramApplicationModel model = new ClassDiagramApplicationModel();
        
        model.setName("UMLDraw u pokusaju");
        model.setVersion("0.5");
        model.setCopyright("Copyright IvanaMilivojevic");
        model.setProjectClassName("draw.classDiagram.ClassDiagramProject");
        app.setModel(model);
        app.launch(args);
    }
}
