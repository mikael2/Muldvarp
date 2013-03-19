package no.hials.forum.controllers;

import java.text.SimpleDateFormat;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import no.hials.forum.model.ForumUser;
import no.hials.forum.model.Message;

/**
 *
 * @author Mikael
 */
@Named
@RequestScoped
public class NewMessage {
    Message message;
    SimpleDateFormat format = new SimpleDateFormat("hh:mm");

    public NewMessage() {
    }
    
    public Message getMessage() {
        if(message == null) {
            message = new Message();
        }
        
        return message;
    }
    
    public String getText() {
        return getMessage().getMessage();
    }
    
    public String getUserName() {
        ForumUser user = getMessage().getUser();
        return user != null ? user.getName() : "";
    }
    
    public String getPostTime() {
        return format.format(getMessage().getPostTime());
    }
}
