/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.commonClasses;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.AbstractClassHierarchy.*;
import businessLogic.ClassDiagrams.*;
import businessLogic.CommonClasses.Crtez;
import businessLogic.UseCaseDiagrams.Aktor;
import businessLogic.UseCaseDiagrams.AktorVeza;
import businessLogic.UseCaseDiagrams.UseCase;
import businessLogic.UseCaseDiagrams.UseCaseVeza;
import com.rabbitmq.client.ConnectionFactory;
import communicationBroker.messages.DiagramCommClient;
import communicationBroker.messages.DiagramMessage;
import communicationBroker.messages.DiagramMessageType;
import communicationBroker.messages.handleInterfaces.IHandleDiagramMessage;
import draw.classDiagram.figures.AgregationConnectionFigure;
import draw.classDiagram.figures.ClassFigure;
import draw.classDiagram.figures.CompositionConnectionFigure;
import draw.classDiagram.figures.GeneralisationConnectionFigure;
import draw.classDiagram.figures.ImplementationConnectionFigure;
import draw.classDiagram.figures.InterfaceFigure;
import draw.usecase.figures.AktorConnectionFigure;
import draw.usecase.figures.AktorFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.IncludeConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import enumerations.ClassConnTypeEnum;
import enumerations.RuntimeClassEnum;
import enumerations.UseCaseConnType;
import javax.swing.JToolBar;
import org.jboss.logging.Message;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DrawingEvent;
import org.jhotdraw.draw.DrawingListener;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.LineConnectionFigure;



/**
*Drawing koji sadrzi u sebi crtez.
*kada se doda nova figura, REFERENCA na business objekat pridruzen figuri se dodaje u crtez objekat
*ovo je neophodno kako bi, kada treba da posaljemo ceo crtez na server radi cuvanja u bazu, mi imamo
*sve objekte crteza koje bazi trebaju na jednom mestu.
*kako sam Crtez sadrzi reference business objekata, kako se oni budu menjali (a za njihovo azuriranje su zaduzene figure
*koje ih sadrze) promene ce automatsi biti vidljive i u crtezu
* */
public class UmlDrawing extends DefaultDrawing implements DrawingListener, IHandleDiagramMessage{
    
    private Crtez UmlCrtez;
    //sluzi da bi se znalo da li je korisnik taj koji crta i treba objekti da se salju
    //ili su objekti vec stigli kroz mrezu i ne treba ponovo da se salju.
    private boolean isDrawing;
    private String loggedUser;
     
    
    //treba nam da bi odavde mogli da se disablujemo i enablujemo kad treba
    private JToolBar drawingToolbar;
    private DiagramCommClient diagramComm=null;
    private DiagramMessage message;
    
    public UmlDrawing()
    {
        super();        
        UmlCrtez=null;
        isDrawing=false;
        this.addDrawingListener(this);
    }
    
    public void setupUmlDrawing(String logUser,Crtez startCrtez){
        loggedUser=logUser;
        UmlCrtez=startCrtez;
        
        String receiveExchange= UmlCrtez.getNaslov()+"_receive_exchange";
        diagramComm=new DiagramCommClient(receiveExchange,this,logUser);
        diagramComm.startConsumer();
        
        if(loggedUser.equals(UmlCrtez.getImeAutora()))
            isDrawing=true;
        else
            isDrawing=false;
            
         DisableAllInContainer.enableComponents(drawingToolbar, isDrawing);
    }
    
    public void setIsDrawing(boolean isDrawing){
        this.isDrawing=isDrawing;
    }
    
    public void setToolBar(JToolBar toolBar){
        this.drawingToolbar=toolBar;
    }
   
    public Crtez getUmlCrtez() {
        return UmlCrtez;
    }

    public void setUmlCrtez(Crtez useCaseDrawing) {
        this.UmlCrtez = useCaseDrawing;
    }
    
    //OVERRIDE metode
    
    @Override
    public void basicAdd(int index, Figure figure) {
        super.basicAdd(index,figure);
        AbstractDiagramElement elemToAdd=((IDataFigure)figure).getDataObject();
        this.UmlCrtez.dodaj(elemToAdd);
    }
    
    @Override
    public void basicRemove(Figure figure) {
        //da bi se uklonile reference u povezanim objektima
        // memory leak jer bi te reference na povezane objekte
        //ostajale i time sprecavale da garbage collector obrise objekte koji se inace vise nigde ne koriste
        //a i dodavanje veze izmedju istih objekata nakon predhodnog brisanja ne bi bio moguc
        if(figure instanceof IRemovableConnection)
            ((IRemovableConnection)figure).fireHandleDisconnect();;
            
        super.basicRemove(figure);
        AbstractDiagramElement elemToRemove=((IDataFigure)figure).getDataObject();
        this.UmlCrtez.obrisi(elemToRemove);
    }
    

    //METODE IZ DrawingListener interfejsa
    @Override
    public void areaInvalidated(DrawingEvent de) {
        //NE ZNAM DA LI CE DA TREBA, NEKA STOJI
    }

    @Override
    public void figureAdded(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        System.out.println("Dodat element: ID "+elem.getElemId()+ " Klasa:"+ elem.getClass().getName());
        
               message=new DiagramMessage();
               message.setMessageType(DiagramMessageType.ADDED);
               message.setBussinesObjectFigure(elem);
               message.setObjectType(getTypeEnumFromObject(elem));
               diagramComm.sendLoginMessage(message);
    }

    @Override
    public void figureRemoved(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        System.out.println("Uklonjen element: ID "+elem.getElemId()+ " Klasa:"+ elem.getClass().getName());
               
               message=new DiagramMessage();
               message.setMessageType(DiagramMessageType.DELETED);
               message.setBussinesObjectFigure(elem);
               message.setObjectType(getTypeEnumFromObject(elem));
               diagramComm.sendLoginMessage(message);
    }
    
    //metode iz figure listener
    //PREVISE KOMPLIKOVANO, PREVISE PUTA SE BACA EVENT. MOGUCE RESENJE DA DEFINISEMO NAS TIP EXCEPTION-A I DA NJEGA BACAMO
    @Override
    public void figureChanged(FigureEvent e){
        
        Figure fig= e.getFigure();
        //mora provera, jer jhotdraw baca figureChanged i za figure decu nase figure, a nama treba
        //samo nasa figura da je uhvatimo i prosledimo dalje
        if(fig instanceof IDataFigure)
        {
            AbstractDiagramElement elem= ((IDataFigure)e.getFigure()).getDataObject();
            System.out.println("Figure changed: ID "+elem.getElemId()+ " Klasa:"+ elem.getClass().getName());
                 
               message=new DiagramMessage();
               message.setMessageType(DiagramMessageType.CHANGED);
               message.setBussinesObjectFigure(elem);
               message.setObjectType(getTypeEnumFromObject(elem));
               diagramComm.sendLoginMessage(message);
        }
        
        this.UmlCrtez.getID();
    }
    
    //  metode neophodne za interakciju sa editorom usled slanja i primanja objekata kroz mrezu
    
    public void handleDoneDrawing(){
        if(isDrawing){
            String tamoneksto="";
        }
    }
    RuntimeClassEnum getTypeEnumFromObject(Object elem)
    {
        if(elem instanceof Argument)
            return RuntimeClassEnum.ARGUMENT;
        else if(elem instanceof Atribut)
            return RuntimeClassEnum.ATRIBUT;
        else if(elem instanceof ClassDiagramVeza)
            return RuntimeClassEnum.CLASS_DIAGRAM_VEZA;
        else if(elem instanceof Interfejs)
            return RuntimeClassEnum.INTERFEJS;
        else if(elem instanceof Klasa)
            return RuntimeClassEnum.KLASA;
        else if(elem instanceof Metod)
            return RuntimeClassEnum.METOD;
        else if(elem instanceof Aktor)
            return RuntimeClassEnum.AKTOR;
        else if(elem instanceof AktorVeza)
            return RuntimeClassEnum.AKTOR_VEZA;
        else if(elem instanceof UseCase)
            return RuntimeClassEnum.USE_CASE;
        else if(elem instanceof UseCaseVeza)
            return RuntimeClassEnum.USE_CASE_VEZA;

        return null;
    }

    @Override
    public void HandleDiagramMessage(DiagramMessage message) {
        switch(message.getMessageType())
        {
        
            case DiagramMessageType.ADDED:
                
                    HandleADD(message);
                
            case DiagramMessageType.DELETED:
                
                    HandleDELETED(message);
                
            case DiagramMessageType.CHANGED:
                
                    HandleCHANGED(message);
                
        
    }
       
}
    public void HandleADD(DiagramMessage message)
    {
        
        
    }
    
      public void HandleDELETED(DiagramMessage message)
    {
        
        
    }
        public void HandleCHANGED(DiagramMessage message)
    {
        
        
    }
        
     Figure CreateFigureFromBussinesObject(Object bussinesObject, RuntimeClassEnum type)
     {
        switch(type)
        {
            case KLASA:{
                return new ClassFigure((Klasa)bussinesObject);
            }
            case AKTOR:{
                return new AktorFigure((Aktor)bussinesObject);
            }
            case AKTOR_VEZA:{
                return new AktorConnectionFigure((AktorVeza)bussinesObject);
            }
            case USE_CASE:{
                return new UseCaseFigure((UseCase)bussinesObject);
            }
            case USE_CASE_VEZA:{
                return CreateUseCaseFromBussinesObject((UseCaseVeza) bussinesObject);
            }
            case INTERFEJS:{
                return new InterfaceFigure((Interfejs)bussinesObject);
            }
            case CLASS_DIAGRAM_VEZA:{
                return CreateClassDiagramConnectionFromBussinesObject((ClassDiagramVeza)bussinesObject);
            }
         }
        return null;
     }
     
     Figure CreateUseCaseFromBussinesObject(UseCaseVeza bussinesObject)
     {
       if(bussinesObject.getTipVeze()==UseCaseConnType.INCLUDE)       
           return new IncludeConnectionFigure(bussinesObject);           
       else if(bussinesObject.getTipVeze()==UseCaseConnType.EXTEND)
           return new ExtendConnectionFigure(bussinesObject);
       return null;               
     }
     
      Figure CreateClassDiagramConnectionFromBussinesObject(ClassDiagramVeza bussinesObject)
     {
       if(bussinesObject.getTip()==ClassConnTypeEnum.AGREGATION)
       
           return new AgregationConnectionFigure(bussinesObject);
           
           else if(bussinesObject.getTip()==ClassConnTypeEnum.COMPOSITION)
               return new CompositionConnectionFigure(bussinesObject);
       
       else if(bussinesObject.getTip()==ClassConnTypeEnum.GENERALISATION)
               return new GeneralisationConnectionFigure(bussinesObject);
       
       else if(bussinesObject.getTip()==ClassConnTypeEnum.IMPLEMENTATION)
               return new ImplementationConnectionFigure(bussinesObject);
       
       return null;               
     }
}