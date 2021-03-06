/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.WebzViewFragment;
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
        super.id = json.getInt("id");
        super.name = json.getString("title");
        if(json.getString("ingress") != null) {
            super.detail = json.getString("ingress");
        }
        if(json.getString("text") != null) {
            this.content = json.getString("text");
        }
        if(json.getString("category") != null) {
            this.category = json.getString("category");
        }
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

    @Override
    public void constructList(List<MuldvarpFragment> fragmentList, List<DomainFragment> fragments) {
        fragmentList.add(new WebzViewFragment(" ", R.drawable.stolen_smsalt, id));
    }
}
