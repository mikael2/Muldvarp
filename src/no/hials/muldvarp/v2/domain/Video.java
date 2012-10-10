/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Video extends Domain {

    public Video() {

    }

    public Video(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
    }

    public Video(String name, String detail) {
        super(name);
        super.detail = detail;
    }
}
