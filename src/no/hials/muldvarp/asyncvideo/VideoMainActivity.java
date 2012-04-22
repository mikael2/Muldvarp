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
import java.util.HashMap;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.asyncutilities.AsyncFileIOUtility;
import no.hials.muldvarp.asyncutilities.WebResourceUtilities;
import no.hials.muldvarp.entities.ListItem;
import no.hials.muldvarp.video.VideoFragmentListSwipe;
import no.hials.muldvarp.view.FragmentPager;

/**
 * Class defining the main Video Activity. 
 *
 * @author johan
 */
public class VideoMainActivity extends FragmentActivity{
    
    //Tab names
    static String VIDEOACTIVITY_TAB1 = "My Videos";
    static String VIDEOACTIVITY_TAB2 = "Courses";
    static String VIDEOACTIVITY_TAB3 = "Student";
    
    //Global Variables    
    //ActionBar Tabs
    ActionBar actionBar;
    //Global variables    
    FragmentPager fragmentPager;
    
    ArrayList<HashMap<String, ArrayList<ListItem>>> fragmentContents;
    ArrayList<String> fragTest;
    //UI stuff
    ProgressDialog progressDialog;
    //Service
    MuldvarpService muldvarpService;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver broadcastReceiver;
    boolean muldvarpBound;
    private ServiceConnection serviceConnection;

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

        //FragmentPager implementation of the actionbar with swiping
        fragmentPager = (FragmentPager) findViewById(R.id.pager);
        fragmentPager.initializeAdapter(getSupportFragmentManager(), actionBar);
        
        //If no saved instance state exists
        if(savedInstanceState == null){
                      
            //Connect to MuldvarpService
            setServiceConnection();
            //Singleton initialization of LocalBroadcastManager
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            //Set up which intents to listen for using an IntentFilter
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE);
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOCOURSE_LOAD);
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE);
            intentFilter.addAction(MuldvarpService.ACTION_VIDEOSTUDENT_LOAD);
            intentFilter.addAction(MuldvarpService.ACTION_PROGRAMMES_UPDATE);
            intentFilter.addAction(MuldvarpService.ACTION_PROGRAMMES_LOAD);
            intentFilter.addAction(MuldvarpService.SERVER_NOT_AVAILABLE);
            
            getBroadcastReceiver();
            localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
            Intent intent = new Intent(this, MuldvarpService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
                
        //Add tabs to FragmentPager, and keep record of it a local variable array
        addFragmentToTab(VIDEOACTIVITY_TAB1.toString(), getString(R.string.cacheVideoCourseList), VideoFragmentListSwipe.class);
        addFragmentToTab(VIDEOACTIVITY_TAB2.toString(), getString(R.string.cacheProgrammeList), VideoFragmentListSwipe.class);
        addFragmentToTab(VIDEOACTIVITY_TAB3.toString(), getString(R.string.cacheCourseList), VideoFragmentListSwipe.class);
        
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
    
    private void setServiceConnection(){
        
        serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            muldvarpService = binder.getService();
            muldvarpBound = true;
            System.out.println("VideoMainActivity: Connected to MuldvarpService.");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            muldvarpService = null;
            muldvarpBound = false;
            System.out.println("VideoMainActivity: Disconnected from MuldvarpService.");
        }
    };
    }
    
    public void addFragmentToTab(String tabName, String listItemType, Class fragment){        
        
        //Add fragment to tab and return it's position in the fragmentpager adapter view list
        int tabPosition = fragmentPager.addTab(tabName, fragment, null).getPosition();        
        //add String with empty item list, will need fixing
//        //bad way to do it
//        HashMap<String, ArrayList<ListItem>> itemListHashMap = new HashMap();
//        ArrayList<ListItem> tempArray = null;
//        System.out.println("TESTAN GAEMS =" +  listItemType);
//        itemListHashMap.put(listItemType, tempArray);
//        fragmentContents.add(itemListHashMap);
        
        //Set some stuff. Not really that important, but eh
        VideoFragmentListSwipe currentFragment = (VideoFragmentListSwipe) fragmentPager.getFragmentInTab(tabPosition);
        currentFragment.setFragmentName(tabName);   
        currentFragment.setListItemType(listItemType);                  
    }
    
    public boolean requestItems(String requestType){
        
        System.out.println("VideoMainActivity: requestItems:" + requestType);
        if(requestType.equals(getString(R.string.cacheProgrammeList))){
            
            muldvarpService.requestProgrammes();
            return true;
            
        } else if(requestType.equals(getString(R.string.cacheVideoCourseList))){
            
            muldvarpService.requestVideos();
            return true;
        } else {
            return false;
        }
    }
          
    public void updateFragment(int position, String itemListName) {
        
        //Get fragment in tab, and update it's contents
        VideoFragmentListSwipe currentFragment = (VideoFragmentListSwipe) fragmentPager.getFragmentInTab(position);
        
        ArrayList<ListItem> testArray = fragmentContents.get(position).get(itemListName);
        if(testArray != null) {
            
            currentFragment.updateContent(testArray);        
        }
    }
    
    public VideoFragmentListSwipe updateFragmentbyListName(String listItemName, ArrayList<ListItem> itemList){
        
        VideoFragmentListSwipe currentFragment = null;
        
        //Replaces the first occurence
        int i = 0;
        while (fragmentContents.size() >= i) {            
            
            if(fragmentContents.get(i).containsKey(listItemName)){
                
                fragmentContents.get(i).put(listItemName, itemList);
                updateFragment(i, listItemName);
                break;
            } else {
                HashMap<String, ArrayList<ListItem>> itemListHashMap = new HashMap();
                
                itemListHashMap.put(listItemName, itemList);
                fragmentContents.add(itemListHashMap);
            }
            i++;
        }
        
        return currentFragment;
    }
    
    
    
    public void showProgressDialog(){
        
        progressDialog = new ProgressDialog(VideoMainActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    
    public void makeShortToast(String toastMessage, Context applicationContext){
        
        if(applicationContext != null){
            
            applicationContext = getApplicationContext();
        }
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show();  
        
    }
    
    private void getListItemsFromLocalStorage(final String type){       
        
        Handler handler = new Handler(){
            
            String listItemType = type;
            @Override
            public void handleMessage(Message message){
                
                switch (message.what) {
                                            
                    case AsyncFileIOUtility.IO_SUCCEED: {

                        String response = (String) message.obj;
                        ArrayList<ListItem> newListItems = WebResourceUtilities
                                .createListItemsFromJSONString(response, type, getApplicationContext());
                        updateFragmentbyListName(listItemType, newListItems);
                        //Dismiss progressdialog
                        progressDialog.dismiss();                        
                        break;
                    }
                        
                    //Connection Error
                    case AsyncFileIOUtility.IO_ERROR: {
                        
                        String response = (String) message.obj;
                        progressDialog.dismiss();
                        makeShortToast("Error reading from cache.", null);
                        break;
                    }
                }
            }//handleMessage
        };//Handler
        
        new AsyncFileIOUtility(handler).readFile(new File(getCacheDir(), type));
    }
    
    private void getBroadcastReceiver(){
        
        //Define BroadCastReceiver and what to do depending on the Intent by overriding the onReceive method
        broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context thisContext, Intent receivedIntent) {
                    
                    System.out.println("VideoMainActivity: Received "+  receivedIntent.getAction());
                    
                    if(receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE)){                        
                        getListItemsFromLocalStorage(getString(R.string.cacheVideoCourseList));
                        makeShortToast("Videos updated.", thisContext);                      
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOSTUDENT_UPDATE)) { 
                        getListItemsFromLocalStorage(getString(R.string.cacheVideoCourseList));
                        makeShortToast("Videos updated.", thisContext);
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOCOURSE_LOAD)) {                        
                        getListItemsFromLocalStorage(getString(R.string.cacheVideoCourseList));
                        makeShortToast("Videos loaded from cache.", thisContext);
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_VIDEOSTUDENT_LOAD)) {                        
                        getListItemsFromLocalStorage(getString(R.string.cacheVideoCourseList));
                        makeShortToast("Videos loaded from cache.", thisContext);
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_PROGRAMMES_UPDATE)) {                        
                        getListItemsFromLocalStorage(getString(R.string.cacheProgrammeList));
                        makeShortToast("Programmes updated.", thisContext);
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.ACTION_PROGRAMMES_LOAD)) {                        
                        getListItemsFromLocalStorage(getString(R.string.cacheProgrammeList));
                        makeShortToast("Programmes updated.", thisContext);
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.SERVER_NOT_AVAILABLE)) {  
                        
                        makeShortToast(getString(R.string.cannot_connect), thisContext);
                        
                    }
                                        
                }
            }; //END OF new BroadcastReceiver
    }
    
}
