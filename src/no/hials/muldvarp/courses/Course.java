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
    Bitmap image;
    String imageurl;
    
    public Course() {
        
    }
    
    public Course(String name, String detail, String url) {
        this.name = name;
        this.detail = detail;
        this.imageurl = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Course(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
