/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.graphics.Bitmap;
import no.hials.muldvarp.entities.ListItem;

/**
 * Class defining a item item
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
    
    
    
}
