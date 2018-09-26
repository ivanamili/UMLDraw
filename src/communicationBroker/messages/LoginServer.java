/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

import com.rabbitmq.client.*;
import communicationBroker.messages.handleInterfaces.IHandleLoginMessage;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class LoginServer {
    
    //potrebno za komunikaciju sa rabbitmq
    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    
    //tag koji se koristi da bi se zaustavio consumer
    String consumerTag=null;
    Consumer consumer;
    
    //ko treba da obradi prispele poruke
    IHandleLoginMessage loginHandler;
    
    public LoginServer(IHandleLoginMessage handler){
        //ko ce da obradi dospele poruke
        this.loginHandler=handler;
        
        factory=new ConnectionFactory();
        factory.setHost(CommunicationConfig.BROKER_HOST);
        
        try{
            connection=factory.newConnection();
            channel=connection.createChannel();
            
            channel.exchangeDeclare(CommunicationConfig.LOGIN_EXCHANGE, BuiltinExchangeType.DIRECT);
            //prvi parametar je ime reda
            //drugi je da li red prezivljava restart servera, treci i cetvrti nam ne trebaju pa false i peti su argumenti
            //treci je da li se red kreira samo za ovu konekciju
            //cetvrti da li je red autodelete tj da li ce da bude izbrisan kada vise se ne koristi
            channel.queueDeclare(CommunicationConfig.LOGIN_QUEUE,true, false, false, null);
            
            //bind queue za exchange
            channel.queueBind(CommunicationConfig.LOGIN_QUEUE,
                               CommunicationConfig.LOGIN_EXCHANGE,
                               CommunicationConfig.LOGIN_KEY);
            
            //kreira consumer-a, ali ga ne binduje za queue
            consumer=createConsumer();
        }
        catch(Exception e){
            System.out.println("Error while opening LoginServer connection");
            System.out.println(e.getMessage());
        }
    }
    
    private Consumer createConsumer()
    {
         Consumer consumer = new DefaultConsumer(channel) {
              @Override
                public void handleDelivery(String consumerTag, 
                        Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
                {
                    LoginMessage message= getMessage(body);
                    if(message==null){
                        System.out.println("Message deserialisation failed in server handle delivery function");
                        return;
                    }
                    
                    //handler obradjue poruku, u zavisnosti od tipa i vraca odgovor
                    //koji treba da se vrati nazad
                    //predstavlja pokusaj RPC implementacije
                    LoginResponse response=loginHandler.HandleLoginMessage(message);
                    
                    //send response to callback queue
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(properties.getCorrelationId())
                    .build();
                    
                    //salje response preko callback queue. on je bindovan za defaultni exchange, zato "" na pocetku
                     channel.basicPublish( "", properties.getReplyTo(), replyProps, response.serializeMessage());
                     //ovo mozda ne treba, rucno slanje potvrde
                     channel.basicAck(envelope.getDeliveryTag(), false);
                }
         };
        return consumer;
    }
    
    //povezuje consumer-a za red, cuva cancelation tag i vraca true ako uspe, inace false
    public boolean startConsumer()
    {
        //vec je startovan
        if(consumerTag!=null)
            return true;
        
        try {
            consumerTag= channel.basicConsume(CommunicationConfig.LOGIN_QUEUE,false, consumer);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean stopConsumer(){
        //ako je consumerTag null, consumer nije ni startovan
        if(consumerTag==null)
            return true;
        try {
            channel.basicCancel(consumerTag);
            consumerTag=null;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public void closeServer() throws IOException, TimeoutException{
        stopConsumer();
        channel.close();
        connection.close();
    }
    
    private LoginMessage getMessage(byte[] body){
        
        LoginMessage message;
        try {
            message = LoginMessage.deserializeMessage(body);
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
