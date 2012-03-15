/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.utility.AsyncHTTPRequest;
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
//    
//    
//    public void getItemsFromWebResource(int itemType) {
//        
//        
//        //Define handler
//        //Defines what should happen depending on the returned message.
//        handler = new Handler() {
//
//            public void handleMessage(Message message) {
//                
//                //TODO: Comment
//                switch (message.what) {
//                    
//                    //Connection Start
//                    case AsyncHTTPRequest.CON_START: {
//
//                        System.out.println("Handler: Connection Started");
//                        //TODO: Loading
//
//                        break;
//                    }
//                        
//                    //Connection Success
//                    case AsyncHTTPRequest.CON_SUCCEED: {
//
//                        String response = (String) message.obj;
//                                                
//                        //Have to use getSupportFragmentManager() since we are using a support package.
//                        CustomListFragmentSwipe customListFragmentSwipe = (CustomListFragmentSwipe) fragmentPager.getAdapter().getItem(getType);
//                        
//                        if(customListFragmentSwipe != null){
//                        
//                                                       
//                            
//                            customListFragmentSwipe.getAdapter(createListItems(response));
//                        } else  {
//                            
//                            System.out.println("Could not get fragment.");
//                        }
//                        
//                        
//                        
//
//                        break;
//                    }
//                        
//                    //Connection Error
//                    case AsyncHTTPRequest.CON_ERROR: {
//                        
//                        //TODO: Create Dialogbox 
//
//                        break;
//                    }
//                }
//            }
//        };
//
//
//
//        //Create new GET request with handler
//        switch (itemType) {
//
//            case 0:
//                new AsyncHTTPRequest(handler).httpGet(videoURL);
//                break;
//            case 1:
//                new AsyncHTTPRequest(handler).httpGet(courseURL);
//                System.out.println("course");
//                break;
//            case 2:
//                new AsyncHTTPRequest(handler).httpGet(videoURL);
//                break;
//
//            default:
//                System.out.println("No tab - it was null");
//                break;
//        }
//
//
//
//
//    }
//
//    //Midlertidig testversjon
//    public ArrayList createListItems(String jsonString) {
//        ArrayList itemList = new ArrayList();
//
//        try {
//            JSONArray jArray = new JSONArray(jsonString);            
//                       
//
//            if (getType == 0) {
//                
//                //Video BS her
//                for (int i = 0; i < jArray.length(); i++) {
//
//                    JSONObject currentObject = jArray.getJSONObject(i);
//
//                    itemList.add(new Video(currentObject.getString("id"),
//                            currentObject.getString("videoName"),
//                            currentObject.getString("videoDetail"),
//                            currentObject.getString("videoDescription"),
//                            "Video",
//                            null,
//                            currentObject.getString("videoURL")));
//
//                }
//
//            } else if (getType == 1) {
//
//                //Course BS her
//                for (int i = 0; i < jArray.length(); i++) {
//
//                    JSONObject currentObject = jArray.getJSONObject(i);
//
//                    itemList.add(new Course(currentObject.getString("name"),
//                            null,
//                            null,
//                            null,
//                            null));
//
//                }
//            } else {
//
//                System.out.println("It was null. or unrelevant :<");
//            }
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//        }
//
//        return itemList;
//    }
}
