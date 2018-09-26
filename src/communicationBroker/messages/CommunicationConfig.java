/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

/**
 *
 * @author Korisnik
 */
public final class CommunicationConfig {
    
    public static final String BROKER_HOST="localhost";
    public static final String LOGIN_EXCHANGE="login_exchange";
    public static final String LOGIN_QUEUE="login_queue";
    public static final String LOGIN_KEY="login_key";
    
    private CommunicationConfig(){}
}
