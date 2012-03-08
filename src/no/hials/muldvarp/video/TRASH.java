/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import java.util.ArrayList;
import no.hials.muldvarp.entities.Video;

/**
 * Trash that's cluttering everything else that I don't want deleted just yet.
 * 
 * @author johan
 */
public class TRASH {
    
    /**
     * Method which returns a an arraylist of video trash.
     * 
     * @param type
     * @return 
     */
    public ArrayList getFakeData(int type) {

        //Just to get different arrays
        String vidString = "";

        switch (type) {

            case 0:
                vidString = "AVID";
                break;
            case 1:
                vidString = "DIV";
                break;
            case 2:
                vidString = "IVD";
                break;
            case 3:
                vidString = "AIVD";
                break;
            default:
                break;


        }

        //Array of vidya
        //Simulates a list of Videos generated from an external resource
        ArrayList<Video> videoArrayList = new ArrayList<Video>();

        for (int i = 0; i < 30; i++) {

            videoArrayList.add(new Video(vidString + (1000 + i), vidString + "Video" + i, "test", "test", "test", null, "test"));

        }

        return videoArrayList;
    }
    
}
