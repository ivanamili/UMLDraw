/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

import com.rabbitmq.client.*;
import communicationBroker.messages.handleInterfaces.IHandleLoginResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class LoginClient {//potrebno za komunikaciju sa rabbitmq
    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    
    //ime reda preko koga ce da se primaju response-ovi;
    String replyQueueName;
    String replyConsumerTag=null;
    Consumer replyQueueConsumer;
    
    //klasa koja ce da handluje prispele odgovore sa servera
    IHandleLoginResponse responseHandler;
    
    public LoginClient(IHandleLoginResponse responseHandler){
        this.responseHandler=responseHandler;
        
        //creating connection
        factory=new ConnectionFactory();
        factory.setHost(CommunicationConfig.BROKER_HOST);
        
        //declaring exchange and queue
        
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
            
            //pribavlja exchange na koji salje, da budemo sigurni da ce da postoji
            channel.exchangeDeclare(CommunicationConfig.LOGIN_EXCHANGE, BuiltinExchangeType.DIRECT);
            
            //kreiranje reda za odgovore sa servera
            replyQueueName = channel.queueDeclare().getQueue();
            
            //kreira reply consumer objekat ali ga ne startuje
            replyQueueConsumer= createConsumer();
        }
        catch(Exception e){
            System.out.println("Error while opening LoginClient connection");
            System.out.println(e.getMessage());
        }
    }
    
    //salje poruku i vraca true ako je poruka poslata uspesno, u suprotnom false
    public boolean sendLoginMessage(LoginMessage message){
        String corrId = UUID.randomUUID().toString();
        
        AMQP.BasicProperties props = new AMQP.BasicProperties
            .Builder()
            .correlationId(corrId)
            .replyTo(replyQueueName)
            .build();
        
        try {
            channel.basicPublish(CommunicationConfig.LOGIN_EXCHANGE,
                    CommunicationConfig.LOGIN_KEY, props, message.serializeMessage());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean startConsumer(){
        //vec je startovan
        if(replyConsumerTag!=null)
            return true;
        
        try {
            replyConsumerTag=channel.basicConsume(replyQueueName, true, replyQueueConsumer);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
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
                LoginResponse response=getMessage(body);
                responseHandler.HandleLoginResponse(response);
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
