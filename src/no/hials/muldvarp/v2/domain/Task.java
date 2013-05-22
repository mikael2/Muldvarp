/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class Task extends Domain {
    Boolean done = false;
    String content_url;
    String contentType;

    public Task(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.done = json.getBoolean("done");
        this.contentType = json.getString("contentType");
        this.content_url = json.getString("content_url");
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
}
