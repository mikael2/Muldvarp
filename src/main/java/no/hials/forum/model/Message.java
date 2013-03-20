package no.hials.forum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.eclipse.persistence.annotations.PrivateOwned;

/**
 *
 * @author Mikael
 */
@Entity
public class Message implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    String message;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date postTime;
    
    @ManyToOne
    ForumUser forumUser;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Message> replies = new ArrayList<>();
    
    @ManyToOne
    Message parent;
    
    
    public Message() {
    }

    public Message(ForumUser forumUser, String message) {
        this.message = message;
        this.forumUser = forumUser;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    public Date getPostTime() {
        return postTime;
    }

    public ForumUser getUser() {
        return forumUser;
    }

    protected void setForumUser(ForumUser forumUser) {
        this.forumUser = forumUser;
    }

    public Message getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }
    
    
    
    public void addReply(Message message) {
        message.setParent(this);
        getReplies().add(message);
    }
    
    public boolean removeMessage(Message message) {
        return getReplies().remove(message);
    }
    
    public List<Message> getReplies() {
        if(replies == null) {
            replies = new ArrayList<>();
        }
        
        return replies;
    }
}
