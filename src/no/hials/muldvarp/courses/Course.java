/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.graphics.Bitmap;

/**
 *
 * @author kristoffer
 */
public class Course {
    
    String name;
    String detail;
    Bitmap thumb;

    public Bitmap getThumb() {
        return thumb;
    }

    public Course(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
