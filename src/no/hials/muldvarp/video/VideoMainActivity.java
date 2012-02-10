/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.utility.AsyncHTTPRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class defining the Video-activity. It contains methods to create and retrieve data
 * used to make up the main view for the video activity.
 * 
 * @author johan
 */
public class VideoMainActivity extends Activity implements ActionBar.TabListener {
    
    
    
    static String TAB1 = "My Videos";
    static String TAB2 = "Courses";
    static String TAB3 = "Student";
    //Resources
    String videoURL = "http://master.uials.no:8080/muldvarp/resources/video";
    String courseURL = "http://master.uials.no:8080/muldvarp/resources/course";
    
    //Global variables
    private Handler handler;
    
    //UI stuff
    ProgressDialog progressDialog;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        //Set layout from video_main.xml using muldvarp.R
        setContentView(R.layout.video_main);
        
        
        //Use ActionBar and configure the actionbar
        final ActionBar actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
               
        
                
        //Add tabs to actionbar
          actionBar.addTab(actionBar.newTab()
                .setText(TAB1)
                .setTabListener(this));
          actionBar.addTab(actionBar.newTab()
                .setText(TAB2)
                .setTabListener(this));
          actionBar.addTab(actionBar.newTab()
                .setText(TAB3)
                .setTabListener(this));
        
         
         

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    
    public void getItemsFromWebResource(int itemType) {
        
        //Define handler
        //Defines what should happen depending on the returned message.
        Handler handler = new Handler() {
			public void handleMessage(Message message) {
				switch (message.what) {
				case AsyncHTTPRequest.CON_START: {
					
                                        System.out.println("Handler: Connection Started");
                                    
					break;
				}
				case AsyncHTTPRequest.CON_SUCCEED: {
                                    
					String response = (String) message.obj;
                                        createListItems(response);
                                        
                                        //Get listView
                                        CustomListView customListView = (CustomListView) getFragmentManager().findFragmentById(R.id.customlistview);
        
                                        customListView.getAdapter(createListItems(response));
					
					break;
				}
				case AsyncHTTPRequest.CON_ERROR: {
					
					break;
				}
				}
			}
		};
        
        
        System.out.println(itemType);
        
        //Create new GET request with handler
        switch(itemType) {
            
            case 0: 
                new AsyncHTTPRequest(handler).httpGet(videoURL);
                break;
            case 1:
                new AsyncHTTPRequest(handler).httpGet(courseURL);
                break;
            case 2:
                new AsyncHTTPRequest(handler).httpGet(videoURL);
                break;
            
        }
        
               
        
        
    }
    
    public ArrayList createListItems(String jsonString) {
    
        ArrayList itemList = new ArrayList();
        
        
      try{ 
            
            JSONObject jObject = new JSONObject(jsonString);
            
            JSONArray jArray;
            
            
            if(jObject.optJSONArray("video") != null) {
                
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

            } else if(jObject.optJSONArray("course") != null) {
                
                jArray = jObject.getJSONArray("course");
                for (int i = 0; i < jArray.length(); i++) {
                
                JSONObject currentObject = jArray.getJSONObject(i);
                
                itemList.add(new Course(currentObject.getString("name"),
                                        null,
                                        null,
                                        null,
                                        null));
                
                }
            }
                

            else {
                
                System.out.println("It was null :<");
            }
      } catch(Exception ex) {
          
          ex.printStackTrace();
      }
      
      return itemList;
    }
    
    
    
    
    
    
    
    //Simulate data input
    public ArrayList getFakeData(int type) {
        
        //Just to get different arrays
        String vidString = "";
        
        switch (type) {
            
            case 0: vidString = "AVID";
                    break;
            case 1: vidString = "DIV";
                    break;
            case 2: vidString = "IVD";
                    break;
            case 3: vidString = "AIVD";
                    break;
            default: break;
            
            
        }        
        
        //Array of vidya
        //Simulates a list of Videos generated from an external resource
        ArrayList<Video> videoArrayList = new ArrayList<Video>();
        
        for (int i = 0; i < 30; i++) {
            
            videoArrayList.add(new Video(vidString + (1000 + i), vidString + "Video" + i, "test", "test", "test", null, "test"));
            
        }       
        
        return videoArrayList;
    }
       
    
    
    //Below are abastract methods from Tablistener
    
    /**
     * 
     * 
     * @param tab
     * @param arg1 
     */
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        
        //Get name of tab
        String tabName = tab.getText().toString();
        
        //Get listView
        CustomListView customListView = (CustomListView) getFragmentManager().findFragmentById(R.id.customlistview);
        
        //Set view name with data from tab
        customListView.setViewName(tabName);
        
        getItemsFromWebResource(tab.getPosition());
        
        
        
        
        //Get data and update the ListView
        //customListView.getAdapter(getFakeData(tab.getPosition()));
        
    }

    
    //NYI
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        
    }

    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        
    }
    
    

}
