/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class ScheduleCourse extends Domain implements Serializable{
    String courseName;
    String courseID;

    public ScheduleCourse(JSONObject json) throws JSONException {
        this.courseName = json.getString("courseName");
        this.courseID = json.getString("courseID");
    }

    private ScheduleCourse(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
