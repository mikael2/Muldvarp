package no.hials.muldvarp.asyncvideo;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.asyncutilities.AsyncFileIOUtility;
import no.hials.muldvarp.asyncutilities.WebresourceUtilities;
import no.hials.muldvarp.video.VideoListFragmentSwipe;
import no.hials.muldvarp.view.FragmentPager;

/**
 * Class defining the Video-activity. It contains methods to create and retrieve
 * data used to make up the main view for the video activity.
 *
 * @author johan
 */
public class VideoTestActivity extends FragmentActivity{
    
    //Tab names
    static String VIDEOACTIVITY_TAB1 = "My Videos";
    static String VIDEOACTIVITY_TAB2 = "Courses";
    static String VIDEOACTIVITY_TAB3 = "Student";
    
    //Global Variables    
    //ActionBar Tabs
    ActionBar actionBar;
    //Global variables    
    FragmentPager fragmentPager;
    ArrayList<ArrayList<Object>> fragmentContents;
    //UI stuff
    ProgressDialog progressDialog;
    //Service
    MuldvarpService muldvarpService;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver broadcastReceiver;
    boolean muldvarpBound;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set layout from video_main.xml using muldvarp.R
        setContentView(R.layout.video_main);

        //Use ActionBar and configure the actionbar
        actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Videos");

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
                        
                        getListItemsFromCache("Video");
                        System.out.println("VideoMainActivity: Received MuldvarpService.ACTION_VIDEOCOURSE_UPDATE");
                        Toast.makeText(thisContext, "Videos updated.", Toast.LENGTH_SHORT).show();                       
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE)) {
                        
                        System.out.println("VideoMainActivity: Received MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE");
                        Toast.makeText(thisContext, "Videos updated.", Toast.LENGTH_SHORT).show();
                    }
                                        
                }
            }; //END OF new BroadcastReceiver
            
            localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
            Intent intent = new Intent(this, MuldvarpService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
                
        //Add tabs to FragmentPager
        addFragmentToTab(VIDEOACTIVITY_TAB1);
        addFragmentToTab(VIDEOACTIVITY_TAB2);
        addFragmentToTab(VIDEOACTIVITY_TAB3);
        
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        
        switch (item.getItemId()) {
                
            case R.id.videomain_settings:
                
                System.out.println("do nothing");
                return true;
                
            case R.id.videomain_update:
                
                if(muldvarpBound){
                    showProgressDialog();
                    muldvarpService.requestVideos();
                } else {
                    Toast.makeText(this, "Not connected to MuldvarpService.", Toast.LENGTH_SHORT).show();
                }                
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (muldvarpBound) {
            unbindService(serviceConnection);
            muldvarpBound = false;
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(localBroadcastManager != null)
            localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
    
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
            IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            muldvarpService = binder.getService();
            muldvarpBound = true;
            showProgressDialog();
            muldvarpService.requestVideos();
            
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            muldvarpService = null;
            muldvarpBound = false;
        }
    };
    
    public void addFragmentToTab(String tabName){
        
        fragmentPager.addTab(tabName, VideoListFragmentSwipe.class, null);
        
    }
          
    public void updateFragment(int position) {
        
        VideoListFragmentSwipe currentFragment = (VideoListFragmentSwipe) fragmentPager.getTab(position);
        currentFragment.updateContent(fragmentContents.get(position));
        
    }
    
    public void updateFragments() {
        
        for(int i = 0; i < fragmentPager.getTabListSize(); i++){
            
            updateFragment(i);
        }
                
    }
    
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(VideoTestActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    
    private void getListItemsFromCache(String type){        
        
        Handler handler = new Handler(){
                                   
            @Override
            public void handleMessage(Message message){
                
                //TODO: Comment
                switch (message.what) {
                    
                    //Connection Start
                    case AsyncFileIOUtility.IO_START: {

                        String response = (String) message.obj;

                        break;
                    }
                        
                    //Read success
                    case AsyncFileIOUtility.IO_SUCCEED: {

                        String response = (String) message.obj;
                        ArrayList newList = WebresourceUtilities.createListItemsFromJSONString(response, "Video");
                        VideoListFragmentSwipe currentFragment = (VideoListFragmentSwipe) fragmentPager.getTab(0);
                        currentFragment.updateContent(newList);
                        //Dismiss progressdialog
                        progressDialog.dismiss();
                        
//                        fragmentContents.add(newList);
//                        updateFragments();
                        

                        break;
                    }
                        
                    //Connection Error
                    case AsyncFileIOUtility.IO_ERROR: {
                        
                        String response = (String) message.obj;

                        break;
                    }
                }
            }
        };
        File cacheFile = new File(getCacheDir(), getString(R.string.cacheVideoCourseList));
        new AsyncFileIOUtility(handler).readFile(cacheFile);
    }
    
}
