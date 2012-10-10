/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines a user in the Muldvarp application.
 * 
 * @author johan
 */
public class User extends Domain implements Serializable {
    private int userId;
    private String password;
    private ArrayList<Course> userCourses;

    public User(){
        
    }
    
    public User(String name, String detail, String description, int icon, int id, String password) {
        super(name, detail, description, icon);
        this.userId = id;
        this.password = password;
    }

    public User(String name, String password) {
        super(name);
        this.password = password;
        userCourses = new ArrayList();              //List of courses favourited by the user.
    }
    
    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getUserCourses() {
        return userCourses;
    }

    public void addCourse(Course course){
        userCourses.add(course);
    }
    
    public void removeCourse(Course course){
        userCourses.remove(course);
    }
}
