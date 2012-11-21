/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.io.Serializable;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.WebzViewFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
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
        System.out.println(json.getString("title"));
        super.id = json.getInt("id");
        super.name = json.getString("title");
        super.detail = json.getString("ingress");
        this.content = json.getString("text");
        this.category = json.getString("category");
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
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        System.out.println("ID IS: " + id);
        fragmentList.add(new WebzViewFragment(" ", R.drawable.stolen_smsalt, id));
    }
}
