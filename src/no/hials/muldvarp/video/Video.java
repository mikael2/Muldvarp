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
    String videoURL;
    boolean savedLocally;
    
    
    public Video(String itemName, String itemDescription, String itemType, Bitmap itemThumbnail, String videoURL) {
        
        super(itemName, itemDescription, itemType, itemThumbnail);
        this.videoURL = videoURL;
        
    }
    
}
