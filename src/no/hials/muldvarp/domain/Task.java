/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author kristoffer
 */
public class Task implements Serializable {
    Integer id;
    String name;
    Boolean done = false;
    String content_url;
    String contentType;
    List<Question> questions;
    
    public Task(String name) {
        this.name = name;
    }
    
    public Task() {
        
    }

    public Integer getId() {
        return id;
    }
    
    public void acceptTask() {
        done = true;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
}
