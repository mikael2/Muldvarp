/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.entities;

import android.graphics.Bitmap;
import java.util.ArrayList;

/**
 * Temporary class for a course.
 * 
 * 
 * @author johan
 */
public class Course extends ListItem{
    
    String imageurl;
    
    Integer revision;
    ArrayList themes;
    
    public Course(String itemName, String smallDetail, String itemDescription, String itemType, Bitmap itemThumbnail) {
        
        super(itemName, smallDetail, itemDescription, itemType, itemThumbnail);
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ArrayList getThemes() {
        return themes;
    }

    public void setThemes(ArrayList themes) {
        this.themes = themes;
    }
    
    
    
}
