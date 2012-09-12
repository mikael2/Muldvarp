package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;

/**
 *
 * @author kristoffer
 */
public class Course extends Domain {
    String imageurl;
    
    Integer revision;
    ArrayList<Theme> themes;
    ArrayList<ObligatoryTask> obligatoryTasks;
    ArrayList<Exam> exams;
    
    public Course(String name) {
        super(name);
    }
    
    public Course(String name, String detail, String url) {
        super(name);
        super.detail = detail;
        this.imageurl = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
