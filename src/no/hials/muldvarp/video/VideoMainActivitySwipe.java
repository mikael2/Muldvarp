package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.view.FragmentPager;

/**
 * Class defining the Video-activity. It contains methods to create and retrieve
 * data used to make up the main view for the video activity.
 *
 * @author johan
 */
public class VideoMainActivitySwipe extends FragmentActivity{

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
        setContentView(R.layout.video_main);

        //Set activity title to be displayed in the top bar.
        setTitle("Video");

        //Use ActionBar and configure the actionbar
        actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        //FragmentPager implementation of the actionbar with swiping
        fragmentPager = (FragmentPager) findViewById(R.id.pager);
        fragmentPager.initializeAdapter(getSupportFragmentManager(), actionBar);
        
        
        ArrayList<String> resourceList = new ArrayList();
        resourceList.add(videoURL);
        resourceList.add(courseURL);
        resourceList.add(videoURL);
        resourceList.add(videoURL);
        
        //Instantiate TabListener
        TabListener videoTabListener = new VideoTabListener(fragmentPager, fragmentPager, resourceList);
        
        //Add tabs along with VideoTabListener
        fragmentPager.addTab(TAB1, CustomListFragmentSwipe.class, videoTabListener);
        fragmentPager.addTab(TAB2, CustomListFragmentSwipe.class, videoTabListener);
        fragmentPager.addTab(TAB3, CustomListFragmentSwipe.class, videoTabListener);

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    /**
     * This is called once the options menu is first displayed.
     * This is an overridden method.
     * 
     * @param menu The Menu where items are placed.
     * @return 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_mainactivity, menu);
        return true;
    }
    
    
        
    
    
}
