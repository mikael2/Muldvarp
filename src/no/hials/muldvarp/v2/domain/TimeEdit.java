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
    List<Course> courses = new ArrayList<Course>();
    
    public TimeEdit(JSONObject json) throws JSONException {
        this.day = json.getString("day");
        this.date = json.getString("date");
        this.courses = JSONArrayToCourses(json.getJSONArray("courses"));
    }
    
    public static List<Course> JSONArrayToCourses(JSONArray jsonArray) throws JSONException {
        List<Course> retVal = new ArrayList<Course>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new Course(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }
    
    public static class Course {
        String time;
        String course;
        String type;
        String mClass;
        String teacher;
        String room;

        private Course(JSONObject jsonObject) throws JSONException {
            this.time = jsonObject.getString("time");
            this.course = jsonObject.getString("course");
            this.type = jsonObject.getString("type");
            this.mClass = jsonObject.getString("mClass");
            this.teacher = jsonObject.getString("teacher");
            this.room = jsonObject.getString("room");
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getmClass() {
            return mClass;
        }

        public void setmClass(String mClass) {
            this.mClass = mClass;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
