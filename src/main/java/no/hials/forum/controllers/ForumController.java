package no.hials.forum.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import no.hials.forum.model.ForumUser;

/**
 *
 * @author Mikael
 */
@Named
@SessionScoped
public class ForumController implements Serializable {
    ForumUser user;
    
    public ForumController() {
    }

    public ForumUser getUser() {
        if(user == null) {
            user = new ForumUser();
        }
        
        return user;
    }

    public void setUser(ForumUser user) {
        this.user = user;
    }
    
    public void sayHello() {
        System.out.println("Hello World");
    }
    
    public void addMessage() {
        System.out.println("Add Message");
    }
}
