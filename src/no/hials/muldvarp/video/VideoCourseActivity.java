package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
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
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.ListItem;
import no.hials.muldvarp.view.FragmentPager;

/**
 * Class defining the main VideoProgramme Activity. 
 *
 * @author johan
 */
public class VideoCourseActivity extends FragmentActivity{
    
    //Global Variables    
    Course course;
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
        
        //Initialize progressDialog
        progressDialog = new ProgressDialog(VideoCourseActivity.this);
        
        //Get extras from previous activity
        //Gets data based on a key represented by a string
        //Get ListItem object
        Bundle extras = getIntent().getExtras();
        ListItem listItem = (ListItem) extras.getSerializable("courseListItem");
        //Cast ListItem to Course
        course = (Course) listItem;
        
        setTitle(course.getItemName());
        
        //If no saved instance state exists
        if(savedInstanceState == null){
                      
            //Connect to MuldvarpService
            setServiceConnection();
            //Singleton initialization of LocalBroadcastManager
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            //Set up which intents to listen for using an IntentFilter
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MuldvarpService.ACTION_COURSEVIDEO_UPDATE);
            intentFilter.addAction(MuldvarpService.SERVER_NOT_AVAILABLE);
            
            getBroadcastReceiver();
            localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
            Intent intent = new Intent(this, MuldvarpService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        
        //FragmentPager implementation of the actionbar with swiping
        fragmentPager = (FragmentPager) findViewById(R.id.pager);
        fragmentPager.initializeAdapter(getSupportFragmentManager(), actionBar);
        //Initialize array
        fragmentContents = new ArrayList<HashMap<String, ArrayList<ListItem>>>();
        //Add tabs to FragmentPager, and keep record of it a local variable array
         addFragmentToTab("Videos for " + course.getItemName(), getString(R.string.cacheCourseVideoList), VideoFragmentListVideos.class);
         
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
                    
                    readyFragments();
                    showProgressDialog();                    
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
    
    @Override
    protected void onResume(){
        
        super.onResume();
        if(!muldvarpBound){
            
            
        }
        
    }
    
    private void setServiceConnection(){
        
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
                muldvarpService = binder.getService();
                muldvarpBound = true;
                System.out.println("VideoCourseActivity: Connected to MuldvarpService.");
                readyFragments();
                
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                muldvarpService = null;
                muldvarpBound = false;
                System.out.println("VideoCourseActivity: Disconnected from MuldvarpService.");
            }
        };
        
    }
    
    public void addFragmentToTab(String tabName, String listItemType, Class fragment){        
        
        //Add fragment to tab and return it's position in the fragmentpager adapter view list
        int tabPosition = fragmentPager.addTab(tabName, fragment, null).getPosition();        
        //add String with empty item list, will need fixing
        HashMap<String, ArrayList<ListItem>> itemListHashMap = new HashMap();
        ArrayList<ListItem> tempArray = null;
        System.out.println("VideoCourseActivity: Adding tab: " + tabName + " with ListItem Type: " +  listItemType);
        itemListHashMap.put(listItemType, tempArray);
        fragmentContents.add(itemListHashMap);
        
        //Set some stuff. Not really that important, but eh
        VideoFragmentListVideos currentFragment = (VideoFragmentListVideos) fragmentPager.getFragmentInTab(tabPosition);
        currentFragment.setFragmentName(tabName);   
        currentFragment.setListItemType(listItemType);                  
    }
    
    public boolean requestItems(String requestType){
        
        System.out.println("VideoCourseActivity: requestItems:" + requestType);
        if(muldvarpBound){
            
            if(requestType.equals(getString(R.string.cacheProgrammeList))){            
                muldvarpService.requestProgrammes();
                showProgressDialog();
                return true;
            
            } else if(requestType.equals(getString(R.string.cacheCourseVideoList))){
                muldvarpService.requestVideosInCourse(course.getCourseID());
                showProgressDialog();
                return true;
                
            }else if(requestType.equals(getString(R.string.videoBookmarks))){
                
                
        
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                
                getListItemsFromLocalStorage(requestType, getFilesDir().getPath() + settings.getString("username", ""));                
                showProgressDialog();
                return true;
                
            } else {
                System.out.println("VideoCourseActivity: No matching request type defined for " + requestType);
                return false;
            }
            
        } else {
            System.out.println("VideoCourseActivity: MuldvarpService not bound.");
            return false;
        }        
    }
    
    public void readyFragments(){
        
        for(int i = 0; i < fragmentPager.getTabListSize(); i++){
            
            VideoFragmentListVideos currentFragment = (VideoFragmentListVideos) fragmentPager.getFragmentInTab(i);
            
            if(currentFragment.requestContent()){
                progressDialog.dismiss();
            }
        }
        
    }
          
    public void updateFragment(int position, String itemListName) {
        
        //Get fragment in tab, and update it's contents
        VideoFragmentListVideos currentFragment = (VideoFragmentListVideos) fragmentPager.getFragmentInTab(position);
        
        ArrayList<ListItem> testArray = fragmentContents.get(position).get(itemListName);
        if(testArray != null) {
            
            currentFragment.updateContent(testArray);        
        }
    }
    
    public VideoFragmentListVideos updateFragmentbyListName(String listItemName, ArrayList<ListItem> itemList){
        
        VideoFragmentListVideos currentFragment = null;
        
        //Replaces the first occurence if fragmentContents is not null or empty,
        //otherwise just adds it straight in.
        if(fragmentContents == null || fragmentContents.isEmpty()){
            
            HashMap<String, ArrayList<ListItem>> itemListHashMap = new HashMap();
                
            itemListHashMap.put(listItemName, itemList);
            fragmentContents.add(itemListHashMap);            
        } else {
            
            for(int n = 0; n < fragmentContents.size(); n++){
                if(fragmentContents.get(n).containsKey(listItemName)){
                    
                    fragmentContents.get(n).put(listItemName, itemList);
                    System.out.println("Updating fragment #" + n + " with " + listItemName);
                    updateFragment(n, listItemName);
                }
            }
        }     
        
        return currentFragment;
    }    
    
    /**
     * This function shows a ProgressDialog defined as a Global Variable if it isn't already showings.
     */
    public void showProgressDialog(){
        
        if(!progressDialog.isShowing()){
         
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }        
    }
    
    public void makeShortToast(String toastMessage, Context applicationContext){
        
        if(applicationContext != null){
            
            applicationContext = getApplicationContext();
        }
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show();  
        
    }
    
    private void getListItemsFromLocalStorage(final String fileName, String filePath){       
        
        Handler handler = new Handler(){
            
            String listItemType = fileName;
            @Override
            public void handleMessage(Message message){
                
                switch (message.what) {
                                            
                    case AsyncFileIOUtility.IO_SUCCEED: {

                        String response = (String) message.obj;
                        ArrayList<ListItem> newListItems = WebResourceUtilities
                                .createListItemsFromJSONString(response, listItemType, getApplicationContext());
                        updateFragmentbyListName(listItemType, newListItems);
                        //Dismiss progressdialog      
                        progressDialog.dismiss();
                        break;
                    }
                        
                    
                    case AsyncFileIOUtility.IO_ERROR: {
                        
                        String response = (String) message.obj;
                        progressDialog.dismiss();
                        makeShortToast("Error reading from storage.", null);
                        break;
                    }
                        
                    case AsyncFileIOUtility.IO_FILENOTEXIST: {
                        
                        String response = (String) message.obj;
                        progressDialog.dismiss();
                        break;
                    }    
                        
                        
                }
            }//handleMessage
        };//Handler
        
        File file = new File(filePath, fileName);
        System.out.println("Getlistitemsfromstorage: " + file.getPath());
        AsyncFileIOUtility asyncFileIO = new AsyncFileIOUtility(handler);
        asyncFileIO.readFile(file);
        asyncFileIO.startThreadedIO();
    }
    
    private void getBroadcastReceiver(){
        
        //Define BroadCastReceiver and what to do depending on the Intent by overriding the onReceive method
        broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context thisContext, Intent receivedIntent) {
                    
                    System.out.println("VideoCourseActivity: Received "+  receivedIntent.getAction());
                    
                    if(receivedIntent.getAction().equals(MuldvarpService.ACTION_COURSEVIDEO_UPDATE)){                        
                        getListItemsFromLocalStorage(getString(R.string.cacheCourseVideoList), getCacheDir().getPath());
                        makeShortToast("Videos updated.", thisContext);                      
                        
                    } else if (receivedIntent.getAction().equals(MuldvarpService.SERVER_NOT_AVAILABLE)) {  
                        
                        makeShortToast(getString(R.string.cannot_connect), thisContext);
                        
                    }
                                        
                }
            }; //END OF new BroadcastReceiver
    }
    
}
