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
public final class MessageType {
    public static final String LOGIN="login";
    public static final String REGISTER="register";
    public static final String LOGIN_RESPONSE="login_response";
    public static final String REGISTER_RESPONSE="register_response";
    public static final String DIAGRAM_CREATE="diagram_create";
    public static final String DIAGRAM_RESPONSE="diagram_response";
    public static final String DIAGRAM_JOIN_REQUEST="diagram_join_request";
    public static final String DIAGRAM_JOIN_RESPONSE="diagram_join_response";
    public static final String ADMIN_MESSAGE_JOINED="joined_to_diagram";
    public static final String ADMIN_DRAWING_STARTED="drawing_started";
    public static final String START_DRAWING="start_drawing";
    public static final String EMPTY_RESPONSE="empty_response";
    public static final String ADMIN_DRAWING_USER_CHANGED="drawing_user_changed";
    
    
    private MessageType(){}
    
}
