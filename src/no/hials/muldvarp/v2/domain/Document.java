/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.DetailActivity;
import no.hials.muldvarp.v2.fragments.DetailFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Document extends Domain {
    String documentId;
    String URI;

    public Document() {

    }

    public Document(JSONObject json) throws JSONException {
        this.name = json.getString("title");
        this.id = json.getInt("id");
        this.URI = json.getString("url");
        this.description = json.getString("summary");
        super.setActivity(DetailActivity.class);
    }

    public Document(String name, String detail) {
        super(name);
        super.detail = detail;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        fragmentList.add(new DetailFragment("Detail", R.drawable.stolen_smsalt));
    }
}
