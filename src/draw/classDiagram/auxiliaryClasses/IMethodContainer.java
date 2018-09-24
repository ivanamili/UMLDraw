/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.classDiagram.auxiliaryClasses;

import draw.classDiagram.figures.method.MethodFigure;

/**
 *
 * @author Korisnik
 */
public interface IMethodContainer {
    public void DeleteMethod(MethodFigure methodToDelete);
    public void AddNewMethod(MethodFigure newMethod);
}
