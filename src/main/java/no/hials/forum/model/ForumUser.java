package no.hials.forum.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Mikael
 */
@Entity
public class ForumUser implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    String name;

    public ForumUser() {
        name = "";
    }

    public ForumUser(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
