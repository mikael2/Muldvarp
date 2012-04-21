/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.entities;

import android.graphics.Bitmap;
import java.util.ArrayList;

/**
 *
 * @author johan
 */
public class Programme extends ListItem {
    
    String programmeID;
    ArrayList<Course> coursesInProgramme;
    
    /**
     * Constructor for the Programme entity class.
     * 
     * @param programmeID
     * @param programmeName
     * @param smallDetail
     * @param programmeDescription
     * @param programmeType
     * @param itemThumbnail 
     */
    public Programme(String programmeID, String programmeName, String smallDetail, String programmeDescription, String programmeType, Bitmap itemThumbnail) {
        
        super(programmeName, smallDetail, programmeDescription, programmeType, itemThumbnail);
        this.programmeID = programmeID;
    }
    
    /**
     * Overloaded constructor for the Programme entity class.
     * 
     * @param programmeID
     * @param coursesInProgramme
     * @param programmeName
     * @param smallDetail
     * @param programmeDescription
     * @param programmeType
     * @param itemThumbnail 
     */
    public Programme(String programmeID, ArrayList<Course> coursesInProgramme, String programmeName, String smallDetail, String programmeDescription, String programmeType, Bitmap itemThumbnail) {
        
        super(programmeName, smallDetail, programmeDescription, programmeType, itemThumbnail);
        this.programmeID = programmeID;
        this.coursesInProgramme = coursesInProgramme;
    }

    public String getProgrammeID() {
        return programmeID;
    }

    public void setProgrammeID(String programmeID) {
        this.programmeID = programmeID;
    }

    public ArrayList<Course> getCoursesInProgramme() {
        return coursesInProgramme;
    }

    public void setCoursesInProgramme(ArrayList<Course> coursesInProgramme) {
        this.coursesInProgramme = coursesInProgramme;
    }
    
    
    
}
