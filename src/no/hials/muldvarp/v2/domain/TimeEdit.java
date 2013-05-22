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
 * @author kb
 */
public class TimeEdit extends Domain implements Serializable {
    String day;
    String date;
    List<ScheduleLecture> lectures;
    
    public TimeEdit(JSONObject json) throws JSONException {
        this.day = json.getString("day");
        this.date = json.getString("date");
        this.lectures = JSONArrayToLectures(json.getJSONArray("lectures"));
    }
    
    public static List<ScheduleLecture> JSONArrayToLectures(JSONArray jsonArray) throws JSONException {
        List<ScheduleLecture> retVal = new ArrayList<ScheduleLecture>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new ScheduleLecture(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }
    
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ScheduleLecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<ScheduleLecture> lectures) {
        this.lectures = lectures;
    }
    
    public static class ScheduleLecture{
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
    
    public static class ScheduleCourse {
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
}
