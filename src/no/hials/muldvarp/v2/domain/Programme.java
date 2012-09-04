/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a programme.
 * 
 * @author johan
 */

public class Programme {
    private Integer id;
    String name;
    String detail;
    List<Course> courses;
    String imageurl;
    
    /**
     * Empty constructor for the Programme JPA class.
     */
    public Programme(){
        
    }
    
    /**
     * This is the constructor for the Programme JPA class.
     * 
     * @param name name of the Programme.
     * @param detail details about the Programme.
     */
    public Programme(String name, String detail) {
        this.name = name;
        this.detail = detail;   
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

//    @Override
//    public String toString() {
//        return name;
//    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
