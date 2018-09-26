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
    String exchangeName;
    Consumer messageConsumer;
    
    //klasa koja ce da handluje prispele odgovore sa servera
    IHandleDiagramMessage  diagramHandler;
    
    public DiagramCommClient(String receiveQueue, String exchangeName,IHandleDiagramMessage  diagramHandler){
        this.diagramHandler=diagramHandler;
;
        
        //creating connection
        factory=new ConnectionFactory();
        factory.setHost(CommunicationConfig.BROKER_HOST);
        
        //declaring exchange and queue
        
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
           
            
            //kreiranje reda za odgovore sa servera
            receiveQueue = channel.queueDeclare().getQueue();
            
            //kreira reply consumer objekat ali ga ne startuje
            messageConsumer= createConsumer();
        }
        catch(Exception e){
            System.out.println("Error while opening LoginClient connection");
            System.out.println(e.getMessage());
        }
    }
    
    //salje poruku i vraca true ako je poruka poslata uspesno, u suprotnom false
    public boolean sendLoginMessage(DiagramMessage message){
        String corrId = UUID.randomUUID().toString();
        
        AMQP.BasicProperties props = new AMQP.BasicProperties
            .Builder()
            .correlationId(corrId)
            .replyTo(receiveQueue)
            .build();
        
        try {
            channel.basicPublish( String exchangeName,
            "", props, message.serializeMessage());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean startConsumer(){
        //vec je startovan
        if(messageConsumerTag!=null)
            return true;
        
        try {
            messageConsumerTag=channel.basicConsume(receiveQueue, true, );
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DiagramCommClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean stopConsumer(){
        //vec je stopiran
        if(replyConsumerTag==null)
            return true;
        
        try {
            channel.basicCancel(replyConsumerTag);
            replyConsumerTag=null;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
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
                diagramHandler.HandleDiagramMessage(message);
            }      
        };
        
        return consumer;
    }
    
    private LoginResponse getMessage(byte[] body){
        
        LoginResponse message;
        try {
            message = LoginResponse.deserializeMessage(body);
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
