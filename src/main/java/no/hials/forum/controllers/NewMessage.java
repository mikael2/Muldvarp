package no.hials.forum.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import no.hials.forum.model.Message;

/**
 *
 * @author Mikael
 */
@Named
@RequestScoped
public class NewMessage {
    Message message;

    public NewMessage() {
    }
    
    public Message getMessage() {
        if(message == null) {
            message = new Message();
        }
        
        return message;
    }
    
}
