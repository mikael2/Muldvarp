package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.view.FragmentPager;

/**
 * Class defining the Video-activity. It contains methods to create and retrieve
 * data used to make up the main view for the video activity.
 *
 * @author johan
 */
public class VideoMainActivitySwipe extends FragmentActivity{   
    
    static String TAB1 = "My Videos";
    static String TAB2 = "Courses";
    static String TAB3 = "Student";
    //Global Variables
    //ActionBar Tabs
    ActionBar actionBar;
    OnPageChangeListener userListener;
    FragmentPager fragmentPager;
    //UI stuff
    ProgressDialog progressDialog;
    
    //Service
    MuldvarpService muldvarpService;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver broadcastReceiver;

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
        
        //If no saved instance state exists
        if(savedInstanceState == null){
            
            //Singleton initialization
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            //Set up which intents to listen for using an IntentFilter
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE);
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE);
            
            //Define BroadCastReceiver and what to do depending on the Intent by overriding the onReceive method
            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context thisContext, Intent receivedIntent) {
                    
                    if(receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE)){
                        
                        //NYI
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE)) {
                        
                        //NYI
                        
                    }
                    
                }
            }; //END OF new BroadcastReceiver
        }            
        
        //Instantiate TabListener
        TabListener videoTabListener = new VideoTabListener(fragmentPager, fragmentPager, null);
        
        //Add tabs along with VideoTabListener
        fragmentPager.addTab(TAB1, VideoListFragmentSwipe.class, videoTabListener);
        fragmentPager.addTab(TAB2, VideoListFragmentSwipe.class, videoTabListener);
        fragmentPager.addTab(TAB3, VideoListFragmentSwipe.class, videoTabListener);
        
        

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
