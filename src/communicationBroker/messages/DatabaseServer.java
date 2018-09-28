/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import communicationBroker.messages.handleInterfaces.IHandleDiagramMessage;
import communicationBroker.messages.handleInterfaces.IHandleLoginMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class DatabaseServer {
    //potrebno za komunikaciju sa rabbitmq
    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    
    //tag koji se koristi da bi se zaustavio consumer
    String consumerTag=null;
    Consumer consumer;
    
    String databaseQueue;
    
    IHandleDiagramMessage messageHandler=null;
    
    public DatabaseServer(IHandleDiagramMessage handler){
        //ko ce da obradi dospele poruke
        this.messageHandler=handler;
        
        factory=new ConnectionFactory();
        factory.setHost(CommunicationConfig.BROKER_HOST);
        
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
            
            channel.queueDeclare(CommunicationConfig.LOGIN_QUEUE,true, false, false, null);
            
            //red na koji ce da se primaju poruke za cuvanje u bazu
            //za sve crteze
            databaseQueue=channel.queueDeclare().getQueue();
            
            //kreira consumer-a, ali ga ne binduje za queue
            consumer=createDatabaseConsumer();
            
            //odmah bindujemo consumer-a na red, ali red nije bindovan ni na jedan exchange, osim ofc na defautni
            consumerTag=channel.basicConsume(databaseQueue, true,consumer);

        }
        catch(Exception e){
            System.out.println("Error while opening LoginServer connection");
            System.out.println(e.getMessage());
        }
    }
    
    public void startListeningForDiagram(String diagramName){
        String diagramExchange=diagramName+"_receive_exchange";
        try {
            channel.exchangeDeclare(diagramExchange, BuiltinExchangeType.FANOUT,true,true,null);
            channel.queueBind(databaseQueue,diagramExchange,"");
        } catch (IOException ex) {
            Logger.getLogger(DatabaseServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Consumer createDatabaseConsumer(){
        Consumer consumer= new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, 
                    Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException 
            {
                DiagramMessage message=getMessage(body);
                messageHandler.HandleDiagramMessage(message);
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
