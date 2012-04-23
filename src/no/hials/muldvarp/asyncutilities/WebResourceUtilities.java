/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.asyncutilities;

import android.content.Context;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.ListItem;
import no.hials.muldvarp.entities.Programme;
import no.hials.muldvarp.entities.Video;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class contains functionality to make and process requests, as well as caching the results.
 * 
 * @author johan
 */
public class WebResourceUtilities {   

    /**
     * This function creates an ArrayList of ListItems from a JSONArray represented
     * by a String. Currently supports Video, Programmes, Course.
     * 
     * @param jsonString String value of JSONArray
     * @param type The type of JSONArray represented by it's cache name
     * @param context The application context to get the cache String name
     * @return ArrayList<ListItem>
     */
    public static ArrayList<ListItem> createListItemsFromJSONString(String jsonString, String type, Context context) {
        ArrayList itemList = new ArrayList();        
        
        try {
            System.out.println("WebResourceUtilities: Printing JSONString: " + jsonString);
            JSONArray jArray = new JSONArray(jsonString);            
                       
            if (type.equals(context.getString(R.string.cacheVideoCourseList))) {
                //Video BS her
                System.out.println("WebresourceUtilities: Array length: " + jArray.length());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject currentObject = jArray.getJSONObject(i);
                    itemList.add(new Video(currentObject.getString("id"),
                            currentObject.getString("videoName"),
                            currentObject.getString("videoDetail"),
                            currentObject.getString("videoDescription"),
                            "Video",
                            null,
                            currentObject.getString("videoURI")));
                }

            } else if (type.equals(context.getString(R.string.cacheCourseList))) {

                //Course BS her
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject currentObject = jArray.getJSONObject(i);

                    itemList.add(new Course(currentObject.getString("name"),
                            null,
                            null,
                            null,
                            null));

                }
            } else if (type.equals(context.getString(R.string.cacheProgrammeList))) {

                //Course BS her
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject currentObject = jArray.getJSONObject(i);

                    itemList.add(new Programme(currentObject.getString("id"), 
                            currentObject.getString("name"),
                            null,  //no small description
                            currentObject.getString("detail"),
                            null, //no type
                            null)); //no bitmap (yet)

                }
            } else {

                System.out.println("It was null. or unrelevant :<");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return itemList;
    }
    
    public static void derp(){        
        
    }
}
