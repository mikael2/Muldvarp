/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.util.Log;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class DomainFragment implements Serializable {
    String name;
    int parentID;
    public enum Type {
        FRONTPAGE, PROGRAMME, COURSE, NEWS, ARTICLE, QUIZ
    }
    Type fragmentType;
    
    long articleID;
    int programmeID;
    String category;

    public DomainFragment(JSONObject json) throws JSONException {
        this.name = json.getString("name");
        this.parentID = json.getInt("parentID");
        this.fragmentType = getBackEnum(json.getString("fragmentType"));
        try {
            this.articleID = json.getLong("articleID");
        } catch(JSONException ex) {
            Log.e("JSON", "Nullpointer?", ex);
        }
        try {
            this.programmeID = json.getInt("programmeID");
        } catch(JSONException ex) {
            Log.e("JSON", "Nullpointer?", ex);
        }
        try {
            this.category = json.getString("category");
        } catch(JSONException ex) {
            Log.e("JSON", "Nullpointer?", ex);
        }
    }
    
    private Type getBackEnum(String s) {
        if(s.equals("FRONTPAGE")) {
            return Type.FRONTPAGE;
        } else if(s.equals("PROGRAMME")) {
            return Type.PROGRAMME;
        } else if(s.equals("COURSE")) {
            return Type.COURSE;
        } else if(s.equals("NEWS")) {
            return Type.NEWS;
        } else if(s.equals("ARTICLE")) {
            return Type.ARTICLE;
        } else if(s.equals("QUIZ")) {
            return Type.QUIZ;
        }
        return null;
    }

    public DomainFragment(String name, int parentID, Type fragmentType) {
        this.name = name;
        this.parentID = parentID;
        this.fragmentType = fragmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public Type getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(Type fragmentType) {
        this.fragmentType = fragmentType;
    }

    public long getArticleID() {
        return articleID;
    }

    public void setArticleID(long articleID) {
        this.articleID = articleID;
    }

    public int getProgrammeID() {
        return programmeID;
    }

    public void setProgrammeID(int programmeID) {
        this.programmeID = programmeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
