/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationBroker.messages.handleInterfaces;

import communicationBroker.messages.LoginMessage;
import communicationBroker.messages.LoginResponse;

/**
 *
 * @author Korisnik
 */
public interface IHandleLoginMessage {
    public LoginResponse HandleLoginMessage(LoginMessage message);
}
