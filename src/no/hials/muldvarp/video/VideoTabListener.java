/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import no.hials.muldvarp.asyncutilities.AsyncHTTPRequest;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.view.FragmentPager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TabListener for the VideoMainActivity. Includes functionality retrieving data from a web resource by making asynchronous HTTP requests.
 * 
 * 
 * @author johan
 */
public class VideoTabListener implements ActionBar.TabListener {
    
    //Global Variables
    ViewPager viewPager;
    FragmentPager fragmentPager;
    ActionBar.TabListener userListener;
    
    Handler handler;
    public ArrayList<String> resourceList;

    //Currently selected tab represented by an int
    private int currentTab = 0;
    
    
    /**
     * Constructor for the VideoMainActivityTabListener.
     * 
     * @param viewPager
     * @param fragmentPager 
     */
    public VideoTabListener(ViewPager viewPager, FragmentPager fragmentPager, ArrayList resourceList) {
        
        this.viewPager = viewPager;
        this.fragmentPager = fragmentPager;
        this.resourceList = resourceList;
        
    }
    

    /**
     * This function is called when a tab is selected.
     * 
     * @param tab
     * @param fragmentTransaction 
     */
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
                
        currentTab = tab.getPosition();
        
        VideoListFragmentSwipe customListFragmentSwipe = (VideoListFragmentSwipe) fragmentPager.getAdapter().getItem(currentTab);
        
        //Temp fix
        if(currentTab == 1) {
        customListFragmentSwipe.setViewName("Courses");
        }
        
        viewPager.setCurrentItem(currentTab);
        
        if(userListener != null){
            userListener.onTabSelected(tab, fragmentTransaction);
        }
        
        //If there are no resources to get, do nothing
        if(resourceList != null) {
        
            getItemsFromWebResource();
        } else {
            
            System.out.println("VIDEOTABLISTENER: No resources available.");
        }
        
    }

    /**
     * This function is called when a tab is unselected.
     * 
     * @param tab
     * @param fragmentTransaction 
     */
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {        
        
        if(userListener != null){
            
            userListener.onTabUnselected(tab, fragmentTransaction);
        }
        
        //NYI
        System.out.println("VIDEOTABLISTENER: Unselected!");
    }

    /**
     * This function is called when a tab is reselected.
     * 
     * @param tab
     * @param fragmentTransaction 
     */
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        
        if(userListener != null){
            userListener.onTabReselected(tab, fragmentTransaction);
        }
        
        //NYI
        System.out.println("Reselected!");
    }
    
    /**
     * Set function for OnPageChangeListener.
     * 
     * @param listener 
     */
    public void setOnPageChangeListener(ActionBar.TabListener listener) {
        userListener = listener;
    }
    
    /**
     * Get function for OnPageChangeListener.
     * 
     * @return OnPageChangeListener
     */
    public ActionBar.TabListener getOnPageChangeListener() {
        return userListener;
    }
    
    /**
     * Set function for the ViewPager.
     * 
     * @param viewPager The ViewPager to be set.
     */
    public void setViewPager(ViewPager viewPager){
        
        this.viewPager = viewPager;
    }

    
    /**
     * Set list of resources.
     * 
     * @param resourceList 
     */
    public void setResourceList(ArrayList<String> resourceList) {
        this.resourceList = resourceList;
    }
    
    /**
     * Add to list of resources.
     * 
     * @param resource 
     */
    public void addResourceList(String resource) {
        
        resourceList.add(resource);
        
    }
    
    
    /**
     * Function which sends a request for a Web Resource and dispatches a Handler to process the response.
     * 
     * @param itemType The type of item.
     */
    public void getItemsFromWebResource() {       
        //Define handler
        //Defines what should happen depending on the returned message.
        handler = new Handler() {

            @Override
            public void handleMessage(Message message) {
                
                //TODO: Comment
                switch (message.what) {
                    
                    //Connection Start
                    case AsyncHTTPRequest.CON_START: {

                        System.out.println("Handler: Connection Started");
                        //TODO: Loading

                        break;
                    }
                        
                    //Connection Success
                    case AsyncHTTPRequest.CON_SUCCEED: {

                        String response = (String) message.obj;
                                                
                        //Have to use getSupportFragmentManager() since we are using a support package.
                        VideoListFragmentSwipe customListFragmentSwipe = (VideoListFragmentSwipe) fragmentPager.getAdapter().getItem(currentTab);
                        
                        if(customListFragmentSwipe != null){
                        
                                                       
                            //Create list of items from the response
//                            customListFragmentSwipe.getAdapter(createListItems(response));
                            customListFragmentSwipe.updateContent(getFakeData(currentTab));
                            
                        } else  {
                            
                            System.out.println("VIDEOTABLISTENER: Could not get fragment.");
                        }
                        
                        
                        

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
        String resourceURL = resourceList.get(currentTab);
        System.out.println("Reequesting resource:");
        System.out.println(resourceURL);
        new AsyncHTTPRequest(handler).httpGet(resourceURL);

    }

    //Midlertidig testversjon
    public ArrayList createListItems(String jsonString) {
        ArrayList itemList = new ArrayList();

        try {
            JSONArray jArray = new JSONArray(jsonString);            
                       

            //hardcoded BS
            //TODO: FIX FIX FIX
            if (currentTab == 0 || currentTab == 2) {
                
                //Video BS her
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

            } else if (currentTab == 1) {

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

            videoArrayList.add(new Video(vidString + (1000 + i), vidString + "Video" + i, "test", "test", "test", null, "ON-7v4qnHP8"));
        }

        return videoArrayList;
    }
    
}
