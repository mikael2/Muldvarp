/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

/**
 *
 * @author kristoffer
 */
public class Task {
    String name;
    public enum content {EXTERNAL,VIDEO,PDF}
    Boolean done = false;
    String content_url;
    
    public Task(String name) {
        this.name = name;
    }
    
    public Task(String name, Boolean done) {
        this.name = name;
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }
    
}
