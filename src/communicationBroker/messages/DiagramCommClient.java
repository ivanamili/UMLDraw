/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

import com.rabbitmq.client.*;
import communicationBroker.messages.handleInterfaces.IHandleDiagramMessage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class DiagramCommClient {//potrebno za komunikaciju sa rabbitmq
    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    
    //ime reda preko koga ce da se primaju response-ovi;
    String receiveQueue;
    String receiveExchange;
    String messageConsumerTag=null;
    Consumer messageConsumer;
    
    String adminExchange;
    
    String logUser;
    
    
    //klasa koja ce da handluje prispele odgovore sa servera
    IHandleDiagramMessage  diagramHandler;
    
    public DiagramCommClient(String exchangeName,
            IHandleDiagramMessage  diagramHandler, String logUser,String adminExchange){
        this.diagramHandler=diagramHandler;
        this.receiveExchange=exchangeName;
        this.adminExchange=adminExchange;
        this.logUser=logUser;
        //creating connection
        factory=new ConnectionFactory();
        factory.setHost(CommunicationConfig.BROKER_HOST);
        
        //declaring exchange and queue
        
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
           
            
            //kreiranje reda za odgovore sa servera
            channel.exchangeDeclare(receiveExchange, BuiltinExchangeType.FANOUT);
            receiveQueue = channel.queueDeclare().getQueue();
            channel.queueBind(receiveQueue,receiveExchange,"");
            
            //kreira reply consumer objekat ali ga ne startuje
            messageConsumer= createConsumer();
        }
        catch(Exception e){
            System.out.println("Error while opening LoginClient connection");
            System.out.println(e.getMessage());
        }
    }
    public void sendAdminMessage(LoginResponse message){
        try {
            channel.basicPublish(adminExchange,
                    CommunicationConfig.FANOUT_NO_KEY, null, message.serializeMessage());
        } catch (IOException ex) {
            Logger.getLogger(DiagramCommClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //salje poruku i vraca true ako je poruka poslata uspesno, u suprotnom false
    public boolean sendDiagramMessage(DiagramMessage message){        
        
   
        try {
            message.setSender(logUser);
            
            channel.basicPublish(receiveExchange,
            "", null, message.serializeMessage());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DiagramCommClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean startConsumer(){
        //vec je startovan
        if(messageConsumerTag!=null)
            return true;
        
        try {
            messageConsumerTag=channel.basicConsume(receiveQueue, true,messageConsumer);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DiagramCommClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean stopConsumer(){
        //vec je stopiran
        if(messageConsumerTag==null)
            return true;
        
        try {
            channel.basicCancel(messageConsumerTag);
            messageConsumerTag=null;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DiagramCommClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void closeClient() throws IOException, TimeoutException{
        stopConsumer();
        channel.close();
        connection.close();
    }
    
    private Consumer createConsumer(){
        Consumer consumer= new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, 
                    Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException 
            {
                DiagramMessage message=getMessage(body);
                if(logUser.equals(message.getSender()))
                    return;
                diagramHandler.HandleDiagramMessage(message);
            }      
        };
        
        return consumer;
    }
    
    private DiagramMessage getMessage(byte[] body){
        
        DiagramMessage message;
        try {
            message = DiagramMessage.deserializeMessage(body);
            return message;
        } catch (IOException ex) {
            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
}
