package no.hials.forum.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

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
    
    ForumUser user;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getPostTime() {
        return postTime;
    }

    public ForumUser getUser() {
        return user;
    }
}
