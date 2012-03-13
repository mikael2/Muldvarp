package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.utility.AsyncHTTPRequest;
import no.hials.muldvarp.view.FragmentPager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class defining the Video-activity. It contains methods to create and retrieve
 * data used to make up the main view for the video activity.
 *
 * @author johan
 */
public class VideoMainActivitySwipe extends FragmentActivity implements ActionBar.TabListener{

    //Global Variables
    //ActionBar Tabs
    ActionBar actionBar;
    OnPageChangeListener userListener;
    static String TAB1 = "My Videos";
    static String TAB2 = "Courses";
    static String TAB3 = "Student";
    //Resources
    String videoURL = "http://master.uials.no:8080/muldvarp/resources/video";
    String courseURL = "http://master.uials.no:8080/muldvarp/resources/course";
    //Global variables
    private Handler handler;
    FragmentPager fragmentPager;
    //UI stuff
    ProgressDialog progressDialog;
    
    //HAT
    int getType = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set layout from video_main.xml using muldvarp.R
        setContentView(R.layout.directory);


        //Use ActionBar and configure the actionbar
        actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        //FragmentPager implementation of the actionbar with swiping
        fragmentPager = (FragmentPager) findViewById(R.id.pager);
        fragmentPager.initializeAdapter(getSupportFragmentManager(), actionBar);    
        fragmentPager.addTab(TAB1, CustomListFragmentSwipe.class).setTabListener(this);
        fragmentPager.addTab(TAB2, CustomListFragmentSwipe.class).setTabListener(this);
        fragmentPager.addTab(TAB3, CustomListFragmentSwipe.class).setTabListener(this);

        //Fill out list
        getItemsFromWebResource(getType);

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
        handler = new Handler() {

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
                        CustomListFragmentSwipe customListFragmentSwipe = (CustomListFragmentSwipe) fragmentPager.getAdapter().getItem(getType);
                        
                        if(customListFragmentSwipe != null){
                        
                                                       
                            
                            customListFragmentSwipe.getAdapter(createListItems(response));
                        } else  {
                            
                            System.out.println("Could not get fragment.");
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



        //Create new GET request with handler
        switch (itemType) {

            case 0:
                new AsyncHTTPRequest(handler).httpGet(videoURL);
                break;
            case 1:
                new AsyncHTTPRequest(handler).httpGet(courseURL);
                System.out.println("course");
                break;
            case 2:
                new AsyncHTTPRequest(handler).httpGet(videoURL);
                break;

            default:
                System.out.println("No tab - it was null");
                break;
        }




    }

    //Midlertidig testversjon
    public ArrayList createListItems(String jsonString) {
        ArrayList itemList = new ArrayList();

        try {
            JSONArray jArray = new JSONArray(jsonString);            
                       

            if (getType == 0) {
                
                //Video BS her
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject currentObject = jArray.getJSONObject(i);

                    itemList.add(new Video(currentObject.getString("id"),
                            currentObject.getString("videoName"),
                            currentObject.getString("videoDetail"),
                            currentObject.getString("videoDescription"),
                            "Video",
                            null,
                            currentObject.getString("videoURL")));

                }

            } else if (getType == 1) {

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
    
        
        
    /*
     * Below are abastract methods from Tablistener
     */
    
    
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {

        getType = tab.getPosition();
        getItemsFromWebResource(getType);

    }
    
    //NYI
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    }

    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
    }

    
    
}
