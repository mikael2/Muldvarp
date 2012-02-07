/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import java.util.ArrayList;

/**
 *
 * @author kristoffer
 */
public class Course {
    
    String name;
    String detail;
    String imageurl;
    
    Integer revision;
    ArrayList themes;
    
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
