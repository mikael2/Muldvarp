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
public class ObligatoryTask extends Task {
    String dueDate;
    String acceptedDate;

    public ObligatoryTask(JSONObject json) throws JSONException {
        super(json);
        this.dueDate = json.getString("dueDate");
        this.acceptedDate = json.getString("acceptedDate");
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }
}