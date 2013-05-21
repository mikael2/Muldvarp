/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kb
 */
public class BibSys extends Domain implements Serializable {
    String title;
    String author;
    
    public BibSys(JSONObject json) throws JSONException {
        this.title = json.getString("title");
        this.author = json.getString("author");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
