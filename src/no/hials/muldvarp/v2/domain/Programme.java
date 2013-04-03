/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.v2.utility.JSONUtilities;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class Programme extends Domain {
    List<Course> courses = new ArrayList<Course>();;
    String imageurl;
    int revision;
    String programmeId;
    String webLink;
    String programmeCode;
    
    public Programme() {

    }

    public Programme(JSONObject json) throws JSONException {
        super(json);
        webLink = json.getString("weblink");
        courses = JSONUtilities.JSONArrayToCourses(json.getJSONArray("courses"));
    }

    public Programme(String name) {
        super(name);
    }

    public void addCourse(Course c) {
        getCourses().add(c);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void removeCourse(Course c) {
        getCourses().remove(c);
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }
    
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }
}
