/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents an artcle in the Muldvarp application domain.
 * @author johan
 */
public class Article extends Domain implements Serializable {
    
    String author;
    String category;
    String content;
    String date;

    public Article() {
    }

    public Article(JSONObject json) throws JSONException {
        super(json);
        this.content = json.getString("");
    }

    public Article(String name) {
        super(name);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
        

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    

}