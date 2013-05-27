/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
/**
     * Inner class ScheduleLecture
     */
public class ScheduleLecture extends Domain implements Serializable{
    String lectureStart;
    String lectureEnd;
    String type;
    String classId;
    String room;
    String teachers;
    String comment;
    List<ScheduleCourse> courses;

    public ScheduleLecture(JSONObject json) throws JSONException {
        this.lectureStart = json.getString("lectureStart");
        this.lectureEnd = json.getString("lectureEnd");
        this.type = json.getString("type");
        this.classId = json.getString("classId");
        this.room = json.getString("room");
        this.teachers = json.getString("teachers");
        this.comment = json.getString("comment");
        this.courses = JSONArrayToCourses(json.getJSONArray("courses"));
    }

    public static List<ScheduleCourse> JSONArrayToCourses(JSONArray jsonArray) throws JSONException {
        List<ScheduleCourse> retVal = new ArrayList<ScheduleCourse>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new ScheduleCourse(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }

    public String getLectureStart() {
        return lectureStart;
    }

    public void setLectureStart(String lectureStart) {
        this.lectureStart = lectureStart;
    }

    public String getLectureEnd() {
        return lectureEnd;
    }

    public void setLectureEnd(String lectureEnd) {
        this.lectureEnd = lectureEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ScheduleCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<ScheduleCourse> courses) {
        this.courses = courses;
    }
}
