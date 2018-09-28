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
import communicationBroker.messages.LoginResponse;
import communicationBroker.messages.MessageType;
import communicationBroker.messages.handleInterfaces.IHandleDiagramMessage;
import draw.classDiagram.figures.AgregationConnectionFigure;
import draw.classDiagram.figures.ClassFigure;
import draw.classDiagram.figures.CompositionConnectionFigure;
import draw.classDiagram.figures.GeneralisationConnectionFigure;
import draw.classDiagram.figures.ImplementationConnectionFigure;
import draw.classDiagram.figures.InterfaceFigure;
import draw.classDiagram.figures.attribute.AttributeFigure;
import draw.classDiagram.figures.method.MethodFigure;
import draw.usecase.figures.AktorConnectionFigure;
import draw.usecase.figures.AktorFigure;
import draw.usecase.figures.ExtendConnectionFigure;
import draw.usecase.figures.IncludeConnectionFigure;
import draw.usecase.figures.UseCaseFigure;
import enumerations.ClassConnTypeEnum;
import enumerations.RuntimeClassEnum;
import enumerations.UseCaseConnType;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JToolBar;
import org.jboss.logging.Message;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DrawingEvent;
import org.jhotdraw.draw.DrawingListener;
import org.jhotdraw.draw.ElbowLiner;
import org.jhotdraw.draw.EllipseFigure;
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
    
    //korisnik koji treba da crta sledeci kada ovaj zavrsi
    private String nextUser;
    
    //posto se figureChanged fire-uje previse puta, ovde ce da se cuva poslednji objekat
    //koji je poslat kroz mrezu i pre svakog slanja se proverava da li je slucajno
    //objekat koji zelimo da saljemo isti kao vec poslati
    //i ako jeste ne slati objekat kroz mrezu jer nema promena
    //samo jhotdraw fantazira nesto i bombarduje eventima
    private AbstractDiagramElement lastSentElement;
     
    
    //treba nam da bi odavde mogli da se disablujemo i enablujemo kad treba
    private JToolBar drawingToolbar;
    private DiagramCommClient diagramComm=null;
    
    public UmlDrawing()
    {
        super();        
        UmlCrtez=null;
        isDrawing=false;
        this.addDrawingListener(this);
    }
    
    public void setupUmlDrawing(String logUser,Crtez startCrtez,String nextUser){
        loggedUser=logUser;
        UmlCrtez=startCrtez;
        this.nextUser=nextUser;
        
        String receiveExchange= UmlCrtez.getNaslov()+"_receive_exchange";
        String adminExchange=UmlCrtez.getNaslov()+"_adminExchange";
        diagramComm=new DiagramCommClient(receiveExchange,this,logUser,adminExchange);
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
    //ipak ne treba
    @Override
    public void areaInvalidated(DrawingEvent de) {
        //NE ZNAM DA LI CE DA TREBA, NEKA STOJI
    }

    @Override
    public void figureAdded(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        
        if(isDrawing){
           sendDiagramMessage(DiagramMessageType.ADDED, elem);
        }       
              
    }
    
    @Override
    public void figureRemoved(DrawingEvent de) {
        AbstractDiagramElement elem= ((IDataFigure)de.getFigure()).getDataObject();
        
        if(isDrawing){
           sendDiagramMessage(DiagramMessageType.DELETED, elem);
        }
               
    }
    
    @Override
    public void figureChanged(FigureEvent e){
        
        Figure fig= e.getFigure();
        //mora provera, jer jhotdraw baca figureChanged i za figure decu nase figure, a nama treba
        //samo nasa figura da je uhvatimo i prosledimo dalje
        if(fig instanceof IDataFigure)
        {
            AbstractDiagramElement elem= ((IDataFigure)e.getFigure()).getDataObject();
            
            if(isDrawing){
                sendDiagramMessage(DiagramMessageType.CHANGED, elem);
            }
              
        }
        
        this.UmlCrtez.getID();
    }
    
    private boolean isElementSameAsLastSent(AbstractDiagramElement element){
        
        if(lastSentElement==null)
            return false;
        
        return lastSentElement.equals(element);
    }
    
    private void sendDiagramMessage(String type, AbstractDiagramElement element){
        
        //takav element je vec poslat, duplikat
        if(type.equals(DiagramMessageType.CHANGED) && isElementSameAsLastSent(element))
            return;
        
        //ipak treba da se salje element, on je sad poslednji poslati
        lastSentElement=(AbstractDiagramElement)element.clone();
        
        DiagramMessage message= new DiagramMessage();
        message.setMessageType(type);
        message.setFigureBounds(element.getPresentationBounds());
        message.setObjectType(getTypeEnumFromObject(element));
         
        //napravi kopiju objekta, obrisi presentation figure
        AbstractDiagramElement elemToSend= (AbstractDiagramElement)element.clone();
        elemToSend.setBoundsToNull();
        
        message.setBussinesObjectFigure(elemToSend);
        
        diagramComm.sendDiagramMessage(message);
        
    }
    
    //  metode neophodne za interakciju sa editorom usled slanja i primanja objekata kroz mrezu
    
    public void handleDoneDrawing(){
        if(isDrawing){
            isDrawing=false;
            //disable toolbar za crtanje
            DisableAllInContainer.enableComponents(drawingToolbar, false);
            
            //posalji poruku ostalima da sledeci moze da krene sa crtanjem
            
            //poruku na response exchange
            DiagramMessage nextToDrawMessage= new DiagramMessage();
            nextToDrawMessage.setBussinesObjectFigure(nextUser);
            nextToDrawMessage.setMessageType(DiagramMessageType.NEXT_USER_DRAWS);
            nextToDrawMessage.setFigureBounds(null);
            nextToDrawMessage.setObjectType(null);
            
            diagramComm.sendDiagramMessage(nextToDrawMessage);
            
            //poruku na admin exchange za admin konzolu
            LoginResponse adminMessage= new LoginResponse();
            adminMessage.setResponseType(MessageType.ADMIN_DRAWING_USER_CHANGED);
            adminMessage.setPayload(nextUser);
            diagramComm.sendAdminMessage(adminMessage);
        }
    }
    
    private RuntimeClassEnum getTypeEnumFromObject(Object elem){
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
        //menja se onaj ko crta, proveri da li ti treba da nastavis da crtas
        if(message.getMessageType().equals(DiagramMessageType.NEXT_USER_DRAWS))
        {
            String drawingUser= (String) message.getBussinesObjectFigure();
            //da ti crtas sledeci, enable toolbar
            if(drawingUser.equals(loggedUser)){
                 isDrawing=true;
                //enable toolbar za crtanje
                DisableAllInContainer.enableComponents(drawingToolbar, isDrawing);
               
            }
            return;
        }
        
        //rekreiraj figuru koja predstavlja okvir
        AbstractDiagramElement elem=(AbstractDiagramElement)message.getBussinesObjectFigure();
        //ovo bi trebalo da rekreira presetation figuru u objektu
        elem.recreatePresentationFigure(message.getFigureBounds());
        
        switch(message.getMessageType())
        {
            case DiagramMessageType.ADDED:{                
                    HandleADD(message); 
                    break;
            }
            case DiagramMessageType.DELETED:{             
                    HandleDELETED(message); 
                    break;
            }
            case DiagramMessageType.CHANGED:{             
                    HandleCHANGED(message);
                    break;
            }
                
        
    }
       
}
    public void HandleADD(DiagramMessage message){
        Figure newFigure= CreateFigureFromBussinesObject(message.getBussinesObjectFigure(),
                                                         message.getObjectType());
        
        int figureCount=super.getFigures().size();
        this.basicAdd(figureCount, newFigure);
        
    }
    public void HandleDELETED(DiagramMessage message){
        AbstractDiagramElement elem= (AbstractDiagramElement)message.getBussinesObjectFigure();
        
        boolean isObjectConnection=false;
        if((elem instanceof UseCaseDiagramVeza) || (elem instanceof Veza))
            isObjectConnection=true;
        
        int deleteId= elem.getElemId();
        Figure figToRemove= getFigureByBusinessId(deleteId,isObjectConnection);
        
        if(figToRemove==null)
            return;
        
        basicRemove(figToRemove);
        
    }
    public void HandleCHANGED(DiagramMessage message){
        AbstractDiagramElement elem= (AbstractDiagramElement)message.getBussinesObjectFigure();
        Figure figToChange= getFigureByBusinessId(elem.getElemId(),false);
        
        if(figToChange==null)
            return;
        
        ((IUpdatableFigure)figToChange).updateDiagramFigure(elem);
        
    }
    
    private Figure getFigureByBusinessId(int id, boolean connection){
        Object[] allFigures= this.getFigures().toArray();
        
        for(int i=0;i< allFigures.length;i++){
            Figure currentFigure=(Figure)allFigures[i];
            int figureId= ((IDataFigure)currentFigure).getDataObject().getElemId();
            if(figureId==id && (currentFigure instanceof ConnectionFigure)==connection)
                return currentFigure;
        }
        return null;
    }
        
    private Figure CreateFigureFromBussinesObject(Object bussinesObject, RuntimeClassEnum type){
        switch(type)
        {
            case AKTOR:{
                return new AktorFigure((Aktor)bussinesObject);
            }
            case AKTOR_VEZA:{
                AbstractDiagramConnectionFigure connFig=new AktorConnectionFigure((AktorVeza)bussinesObject);
                Figure startFigure= getFigureByBusinessId(((AktorVeza)bussinesObject).getAktor(),false);
                Figure endFigure= getFigureByBusinessId(((AktorVeza)bussinesObject).getUseCase(),false);
         
                connFig.setConnectionEndpoints(startFigure, endFigure);
                
                return connFig;
            }
            case USE_CASE:{
                return new UseCaseFigure((UseCase)bussinesObject);
            }
            case USE_CASE_VEZA:{
                return CreateUseCaseFromBussinesObject((UseCaseVeza) bussinesObject);
            }
            case KLASA:{
                return new ClassFigure((Klasa)bussinesObject);
            }
            case INTERFEJS:{
                return new InterfaceFigure((Interfejs)bussinesObject);
            }
            case CLASS_DIAGRAM_VEZA:{
                return CreateClassDiagramConnectionFromBussinesObject((ClassDiagramVeza)bussinesObject);
            }
            case ATRIBUT:{
                return new AttributeFigure((Atribut)bussinesObject);
            }
            case METOD:{
                return new MethodFigure((Metod)bussinesObject);
            }
         }
        return null;
     }
     
    private Figure CreateUseCaseFromBussinesObject(UseCaseVeza bussinesObject){
         AbstractDiagramConnectionFigure connFig=null;
         
         if(bussinesObject.getTipVeze()==UseCaseConnType.INCLUDE)       
             connFig= new IncludeConnectionFigure(bussinesObject); 
         
         else if(bussinesObject.getTipVeze()==UseCaseConnType.EXTEND)
             connFig= new ExtendConnectionFigure(bussinesObject);
         
         Figure startFigure= getFigureByBusinessId(bussinesObject.getOdKoga(),false);
         Figure endFigure= getFigureByBusinessId(bussinesObject.getDoKoga(),false);
         
         connFig.setConnectionEndpoints(startFigure, endFigure);
         
         return connFig;        
     }
     
     //treba da se dopuni kodom za dodavanje connectora, ako taj kod proradi u usecas
    private Figure CreateClassDiagramConnectionFromBussinesObject(ClassDiagramVeza bussinesObject)
     {
        AbstractDiagramConnectionFigure connFig=null;
         
        if(bussinesObject.getTip()==ClassConnTypeEnum.AGREGATION)
           connFig= new AgregationConnectionFigure(bussinesObject);
           
        else if(bussinesObject.getTip()==ClassConnTypeEnum.COMPOSITION)
           connFig= new CompositionConnectionFigure(bussinesObject);
       
        else if(bussinesObject.getTip()==ClassConnTypeEnum.GENERALISATION){
           connFig= new GeneralisationConnectionFigure(bussinesObject);
           connFig.setLiner(new ElbowLiner());
        }
       
        else if(bussinesObject.getTip()==ClassConnTypeEnum.IMPLEMENTATION){
           connFig=new ImplementationConnectionFigure(bussinesObject);
           connFig.setLiner(new ElbowLiner());
        }
       
         Figure startFigure= getFigureByBusinessId(bussinesObject.getOdKoga(),false);
         Figure endFigure= getFigureByBusinessId(bussinesObject.getDoKoga(),false);
         
         connFig.setConnectionEndpoints(startFigure, endFigure);
         
         return connFig;                  
     }
}