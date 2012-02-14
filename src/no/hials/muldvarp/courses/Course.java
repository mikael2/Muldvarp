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
    Short id = 1; // temp greie
    String name;
    String detail;
    String imageurl;
    
    Integer revision;
    ArrayList<Theme> themes;
    ArrayList<ObligatoryTask> obligatoryTasks;
    ArrayList<Exam> exams;
    
    public Course() {
        
    }
    
    public Course(String name, String detail, String url) {
        this.name = name;
        this.detail = detail;
        this.imageurl = url;
    }

    public Short getId() {
        return id;
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

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    public ArrayList<ObligatoryTask> getObligatoryTasks() {
        return obligatoryTasks;
    }

    public void setObligatoryTasks(ArrayList<ObligatoryTask> obligatoryTasks) {
        this.obligatoryTasks = obligatoryTasks;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }
}
