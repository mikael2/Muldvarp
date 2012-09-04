/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.List;

public class Programme extends ListItem {
    private Integer id;
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
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
