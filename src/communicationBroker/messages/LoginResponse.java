/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class LoginResponse implements Serializable {
    private String responseType;
    private Object payload;

    /**
     * @return the messageType
     */
    public String getResponseType() {
        return responseType;
    }

    /**
     * @param responseType the messageType to set
     */
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @return the payload
     */
    public Object getPayload() {
        return payload;
    }
    
    public void setPayload(Object payload){
        this.payload=payload;
    }
    
     public byte[] serializeMessage() throws IOException{
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(this);
        
        return b.toByteArray();
    }
    
    public static LoginResponse deserializeMessage(byte[] bytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return (LoginResponse) o.readObject();
    } 
}
