/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import store.manager.ClientLoggingManager;
import store.manager.IClientLoggingManager;
import store.manager.IPersistenceManager;
import store.manager.PersistenceManager;

/**
 *
 * @author Korisnik
 */
public class ServerApp {
    private static final String EXIT="exit";
    
    static AtomicBoolean exitCommandIssued = new AtomicBoolean(false);
    
    //manager za komunikaciju sa bazom sto se tice logovanja i ostalog oko kreiranja crteza
    private IClientLoggingManager loggingManager;
    //manager za komunikaciju sa bazom sto se tice crud operacija
    private IPersistenceManager persistenceManager;
    
    public ServerApp(){
        loggingManager= new ClientLoggingManager();
        persistenceManager= new PersistenceManager();
    }
    
    //metoda koja treba da startuje server
     public static void main(String[] args) {
         
         ServerApp server= new ServerApp();
         server.run();
         
     }

     //treba da odradi sve sta inicijalno treba u serveru, kreiranje exchage-a, redova i ostalo
    private void run() {
        
        //startuje novi thread koji ce da osluskuje za komande, kako bi glavni ostao slobodan
        //za stampanje u konzoli
        waitForCommand();
        
        //petlja koja ceka da se unese exit komanda da bi se server zatvorio
        System.out.println("SERVER SUCESSFULLY STARTED");
        while(exitCommandIssued.get())
        {
            
        }
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
}
