/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.asyncutilities;

import no.hials.muldvarp.asyncutilities.AsyncHTTPRequest;
import android.app.LauncherActivity.ListItem;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.Video;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class contains functionality to make and process requests, as well as caching the results.
 * 
 * @author johan
 */
public class WebresourceUtilities {
    
    /**
     * Function which sends a request for a Web Resource and dispatches a Handler to process the response.
     * 
     * @param itemType The type of item.
     */
    public void getDataFromWebResource(String URI, String header) {       
        //Define handler
        //Defines what should happen depending on the returned message.
        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message message) {
                
                //TODO: Comment
                switch (message.what) {
                    
                    //Connection Start
                    case AsyncHTTPRequest.CON_START: {

                        System.out.println(getClass().getName() + ": Handler: Connection started.");

                        break;
                    }
                        
                    //Connection Success
                    case AsyncHTTPRequest.CON_SUCCEED: {

                        String response = (String) message.obj;
                        

                        break;
                    }
                        
                    //Connection Error
                    case AsyncHTTPRequest.CON_ERROR: {
                        
                        //TODO: Create Dialogbox 

                        break;
                    }
                }
            }
        };


        //Get resource URL and make asynchronous HTTP request
        String resourceURL = "hurr";
        System.out.println("Reequesting resource:");
        System.out.println(resourceURL);
        new AsyncHTTPRequest(handler).httpGet(resourceURL);

    }

    //Midlertidig testversjon
    public static ArrayList<ListItem> createListItemsFromJSONString(String jsonString, String type) {
        ArrayList itemList = new ArrayList();

        try {
            JSONArray jArray = new JSONArray(jsonString);            
                       
            if (type.equals("Video")) {
                //Video BS her
                System.out.println("WebresourceUtilities: Array length: " + jArray.length());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject currentObject = jArray.getJSONObject(i);

                    System.out.println(currentObject.toString());
                    
                    itemList.add(new Video(currentObject.getString("id"),
                            currentObject.getString("videoName"),
                            currentObject.getString("videoDetail"),
                            currentObject.getString("videoDescription"),
                            "Video",
                            null,
                            currentObject.getString("videoURI")));
                }

            } else if (type.equals("Course")) {

                //Course BS her
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
