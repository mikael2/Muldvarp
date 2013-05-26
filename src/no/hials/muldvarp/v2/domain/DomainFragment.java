/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.util.Log;
import java.io.Serializable;
import java.util.List;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.utility.JSONUtilities;
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
        FRONTPAGE, PROGRAMME, COURSE, NEWS, ARTICLE, QUIZ, DOCUMENT, VIDEO, BIBSYS, FRONTER
    }
    Type fragmentType;
    
    long articleID;
    String category;
    List<Domain> items;

    public DomainFragment(JSONObject json) throws JSONException {
        this.name = json.getString("name");
        this.fragmentType = getBackEnum(json.getString("fragmentType"));
        try {
            switch(fragmentType) {
                case QUIZ:
                    this.items = JSONUtilities.JSONtoList(json.getJSONArray("quizzes").toString(), MuldvarpService.DataTypes.QUIZ);
                    break;
                case COURSE:
                    this.items = JSONUtilities.JSONtoList(json.getJSONArray("courses").toString(), MuldvarpService.DataTypes.COURSES);
                    break;
                case DOCUMENT:
                    this.items = JSONUtilities.JSONtoList(json.getJSONArray("documents").toString(), MuldvarpService.DataTypes.DOCUMENTS);
                    break;
                case NEWS:
                    try {
                        this.category = json.getString("category");
                    } catch(JSONException ex) {
                        Log.e("JSON", "No category?", ex);
                    }
//                    this.items = JSONUtilities.JSONtoList(json.getJSONArray("news").toString(), MuldvarpService.DataTypes.NEWS);
                    break;
                case VIDEO:
                    this.items = JSONUtilities.JSONtoList(json.getJSONArray("videos").toString(), MuldvarpService.DataTypes.VIDEOS);
                    break;
                case ARTICLE:
                    try {
                        this.articleID = json.getJSONObject("article").getLong("id");
                    } catch(JSONException ex) {
                        Log.e("JSON", "No Article?", ex);
                    }
                    break;
            }
        } catch(JSONException ex) {
            Log.e("JSON", "No fragments?", ex);
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
        } else if(s.equals("DOCUMENT")) {
            return Type.DOCUMENT;
        } else if(s.equals("VIDEO")) {
            return Type.VIDEO;
        }  else if(s.equals("BIBSYS")) {
            return Type.BIBSYS;
        }  else if(s.equals("FRONTER")) {
            return Type.FRONTER;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
