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
 * @author kristoffer
 */
public class Article extends Domain implements Serializable {
    String content;

    public Article() {
    }

    public Article(JSONObject json) throws JSONException {
        super(json);
        this.content = json.getString("");
    }

    public Article(String name) {
        super(name);
    }


}
