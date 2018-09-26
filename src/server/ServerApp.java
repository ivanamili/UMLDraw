/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import communicationBroker.messages.LoginMessage;
import communicationBroker.messages.LoginResponse;
import communicationBroker.messages.LoginServer;
import communicationBroker.messages.MessageType;
import communicationBroker.messages.handleInterfaces.IHandleLoginMessage;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import store.manager.ClientLoggingManager;
import store.manager.IClientLoggingManager;
import store.manager.IPersistenceManager;
import store.manager.PersistenceManager;

/**
 *
 * @author Korisnik
 */
public class ServerApp implements IHandleLoginMessage {
    private static final String EXIT="exit";
    static AtomicBoolean exitCommandIssued = new AtomicBoolean(false);
    
    //communication klase
    private LoginServer loginServer;
    
    //manager za komunikaciju sa bazom sto se tice logovanja i ostalog oko kreiranja crteza
    private IClientLoggingManager loginManager;
    //manager za komunikaciju sa bazom sto se tice crud operacija
    private IPersistenceManager persistenceManager;
    
    public ServerApp(){
        loginManager= new ClientLoggingManager();
        persistenceManager= new PersistenceManager();
        
        loginServer= new LoginServer(this);
        loginServer.startConsumer();
    }
    
    //metoda koja treba da startuje server
     public static void main(String[] args) {
         
        ServerApp server= new ServerApp();
        try {
            
            server.run();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }

     //treba da odradi sve sta inicijalno treba u serveru, kreiranje exchage-a, redova i ostalo
    private void run() throws IOException, TimeoutException {
        
        //startuje novi thread koji ce da osluskuje za komande, kako bi glavni ostao slobodan
        //za stampanje u konzoli
        waitForCommand();
        
        //petlja koja ceka da se unese exit komanda da bi se server zatvorio
        System.out.println("SERVER SUCESSFULLY STARTED");
        while(!exitCommandIssued.get())
        {
            
        }
        loginServer.closeServer();
    }
    
    private void waitForCommand()
    {
        Thread t = new Thread(() -> {
            Scanner scan = new Scanner(System.in);
            String command="started";
            
            while(!command.equals(EXIT)){
                //listen for commands
                command= scan.nextLine();

                switch(command){
                    case EXIT: 
                    {
                        //server should stop
                        exitCommandIssued.set(true);
                        scan.close();
                        break;
                    }
                }
            }            
            
            
        });
        t.start();
    }

    @Override
    public LoginResponse HandleLoginMessage(LoginMessage message) {
        LoginResponse response= new LoginResponse();
        switch(message.getMessageType()){
            case MessageType.LOGIN:
            {
                String[] credentials= (String[])message.getPayload();
                boolean success= loginManager.tryLogin(credentials[0],credentials[1]);
                response.setResponseType(MessageType.LOGIN_RESPONSE);
                response.setPayload(success);
                break;
            }
            case MessageType.REGISTER:
            {
                String[] credentials= (String[])message.getPayload();
                boolean success= loginManager.tryRegister(credentials[0],credentials[1]);
                response.setResponseType(MessageType.REGISTER_RESPONSE);
                response.setPayload(success);
                
                return response;
                
            }
        }
        return response;
    }
}
