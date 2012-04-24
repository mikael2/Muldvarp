/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.entities;

import android.graphics.Bitmap;

/**
 * Class defining a Video ListItem.
 * 
 * @author johan
 */
public class Video extends ListItem {
    
    //Class variables
    String videoID;
    String videoURL;
    boolean savedLocally;
    
    
    public Video(String videoID, String itemName, String smallDetail, String itemDescription, String itemType, Bitmap itemThumbnail, String videoURL) {
        
        super(itemName, smallDetail, itemDescription, itemType, itemThumbnail);
        this.videoID = videoID;
        this.videoURL = videoURL;
        
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public boolean isSavedLocally() {
        return savedLocally;
    }

    public void setSavedLocally(boolean savedLocally) {
        this.savedLocally = savedLocally;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Video other = (Video) obj;
        if ((this.videoID == null) ? (other.videoID != null) : !this.videoID.equals(other.videoID)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.videoID != null ? this.videoID.hashCode() : 0);
        return hash;
    }
    
    
    
}
