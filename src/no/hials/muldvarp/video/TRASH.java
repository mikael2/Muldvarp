/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import java.util.ArrayList;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.Video;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    /**
     * Old function.
     * 
     * @param jsonString
     * @return 
     */
    public ArrayList createListItems(String jsonString) {
        ArrayList itemList = new ArrayList();

        try {
            JSONObject jObject = new JSONObject(jsonString);
            JSONArray jArray;
            
                       

            if (jObject.optJSONArray("video") != null) {
                jArray = jObject.getJSONArray("video");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject currentObject = jArray.getJSONObject(i);

                    itemList.add(new Video(currentObject.getString("id"),
                            currentObject.getString("videoName"),
                            currentObject.getString("videoDetail"),
                            currentObject.getString("videoDescription"),
                            "Video",
                            null,
                            currentObject.getString("videoThumbURL")));

                }

            } else if (jObject.optJSONArray("course") != null) {

                jArray = jObject.getJSONArray("course");
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject currentObject = jArray.getJSONObject(i);

                    itemList.add(new Course(currentObject.getString("name"),
                            null,
                            null,
                            null,
                            null));

                }
            } else {

                System.out.println("It was null. or unrelevant :<");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return itemList;
    }
}
