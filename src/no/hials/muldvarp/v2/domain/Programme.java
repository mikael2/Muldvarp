/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.List;

public class Programme extends ListItem {
    List<Course> courses;
    String imageurl;
    
    /**
     * This is the constructor for the Programme JPA class.
     * 
     * @param name name of the Programme.
     * @param detail details about the Programme.
     */
    public Programme(String name) {
        super(name);
    }
    
    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        super.detail = detail;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        super.name = name;
    }
    
    public void addCourse(Course c) {
        getCourses().add(c);
    }

    public List<Course> getCourses() {
        if(courses == null) {
            courses = new ArrayList<Course>();
        }
        
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void removeCourse(Course c) {
        getCourses().remove(c);
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
