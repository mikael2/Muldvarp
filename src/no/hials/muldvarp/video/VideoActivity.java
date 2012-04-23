/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.asyncutilities.AsyncFileIOUtility;
import no.hials.muldvarp.asyncutilities.AsyncHTTPRequest;
import no.hials.muldvarp.asyncutilities.WebResourceUtilities;
import no.hials.muldvarp.asyncvideo.VideoMainActivity;
import no.hials.muldvarp.entities.ListItem;
import no.hials.muldvarp.entities.Video;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class VideoActivity extends Activity {

    //Global variables
    String videoID;
    String videoName;
    String videoDescription;
    String videoURL;   
    VideoView videoView;
    
    //UI stuff
    ProgressDialog progressDialog;
    
     //Service
    MuldvarpService muldvarpService;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver broadcastReceiver;
    boolean muldvarpBound;
    private ServiceConnection serviceConnection;
    
    //TEST
    static final int OPTION1 = 0;
    static final int OPTION2 = 1;
    
    //MORETEST
    static String googleJSONQuery = "http://gdata.youtube.com/feeds/api/videos?alt=json&q=";
    
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set layout
        setContentView(R.layout.video_detail);
        
        //Initialize progressDialog
        progressDialog = new ProgressDialog(VideoActivity.this);
                
        //Get extras from previous activity
        //Gets data based on a key represented by a string
        Bundle extras = getIntent().getExtras();
        videoID = extras.getString("videoID");
        videoName = extras.getString("videoName");
        videoDescription = extras.getString("itemDescription");
        videoURL = extras.getString("videoURL");
        
        //Set activity title to be displayed in the top bar.
        setTitle(videoName);
        
        
        videoView = (VideoView)findViewById(R.id.myvideoview);
//        videoView.setVisibility(1);
//        videoView.requestFocus();
        videoView.setVideoURI(Uri.parse("rtsp://v3.cache4.c.youtube.com/CjYLENy73wIaLQkBRdI28FHBXhMYJCAkFEIJbXYtZ29vZ2xlSARSBXdhdGNoYOWZlvT3jee4Tww=/0/0/0/video.3gp"));
        MediaController mediaController = new MediaController(this);        
        videoView.setMediaController(mediaController);
        
        
        videoView.start();
//        get3gp(videoURL);
        
        
        //Print some stuff based on extras from previous activity
 
        
        
        //Create button for youtube
        Button youtubeButton = (Button) findViewById(R.id.youtubeapp);
        youtubeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                
                startYoutubeApp("test");
                
            }
        });       
        
        //Video ID
        TextView textVideoID = (TextView) findViewById(R.id.videoID);
        textVideoID.setText(videoID);
        
        
        //Video Title
        TextView textVideoName = (TextView) findViewById(R.id.videotitle);
        textVideoName.setText(videoName);
        
        //Video Description
        TextView textVideoDescription = (TextView) findViewById(R.id.videodescription);
        textVideoDescription.setText(videoDescription);
                
        
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
        inflater.inflate(R.menu.video_activity, menu);
        return true;
    }
    
    /**
     * This method is called whenever an item in the options menu is selected.
     * 
     * This is an overridden method.
     * 
     * @param item The item that was selected.
     * @return 
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_download:
                
                final CharSequence[] items = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Download this video?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        
                        switch(item) {
                            
                            case 0:
                                Toast.makeText(getApplicationContext(), "Download not yet implemented", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Downlad wasn't implemented anyway.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "You dun goofed", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                
                //NYI
                return true;
                
            case R.id.video_bookmark:
                
                Toast.makeText(this, "Video added to favourites!", Toast.LENGTH_SHORT).show();
                
                //NYI
                return true;
            case R.id.video_settings:
                
                Toast.makeText(this, "Menu clicked.", Toast.LENGTH_SHORT).show();
                
                //NYI
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch(id) {
            case OPTION1:
                // do the work to define the pause Dialog
                break;
            case OPTION2:
                // do the work to define the game over Dialog
                break;
            default:
                dialog = null;
        }
        return dialog;
    }   
    
    private void setServiceConnection(){
        
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
                muldvarpService = binder.getService();
                muldvarpBound = true;
                System.out.println("VideoActivity: Connected to MuldvarpService.");
                muldvarpService.requestVideo(OPTION1);
                
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                muldvarpService = null;
                muldvarpBound = false;
                System.out.println("VideoMainActivity: Disconnected from MuldvarpService.");
            }
        };
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
    
    public void startVideo(String srcPath) {
        
        videoView.setVideoURI(Uri.parse(srcPath));
        
        videoView.start();
        
    }
    
    public void writeVideosToBookmark(Video video){        
                
        Handler handler = new Handler(){
            
            @Override
            public void handleMessage(Message message){
                
                switch(message.what){
                    
                    case AsyncFileIOUtility.IO_SUCCEED: {
                        
                        
                     
                        break;
                    }                        
                        
                    case AsyncFileIOUtility.IO_ERROR: {
                     
                        break;
                    }    
                }                
            }
        };
        
        
        new AsyncFileIOUtility(handler);
    }
    
    public void readVideosFromBookmark(){
        
        
        
        Handler handler = new Handler(){
            
            @Override
            public void handleMessage(Message message){
                
                switch(message.what){
                    
                    case AsyncFileIOUtility.IO_SUCCEED: {
                        
                        String response = (String) message.obj;
                        String type = getString(R.string.cacheVideoCourseList);
                        ArrayList<ListItem> newListItems = WebResourceUtilities
                                .createListItemsFromJSONString(response, type, getApplicationContext());
                     
                        break;
                    }                        
                        
                    case AsyncFileIOUtility.IO_ERROR: {
                     
                        break;
                    }    
                }                
            }
        };
        
        File file = new File(getFilesDir().getAbsolutePath() + "videoBookmarks");
        new AsyncFileIOUtility(handler).readFile(file);
    }
    
    /**
     * Test function for quering the youtube api
     * 
     * 
     * @param youtubeVideoID
     * @return 
     */
    public String get3gp(String youtubeVideoID) {
               
        Handler handler = new Handler() {

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
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            
                            JSONObject herf = jsonObject.getJSONObject("feed");
                            JSONArray derf = herf.getJSONArray("entry");
                            JSONObject hurf = derf.getJSONObject(0);
                            JSONObject derp = hurf.getJSONObject("media$group");
                            JSONArray herp = derp.getJSONArray("media$content");
                            JSONObject murr = herp.getJSONObject(1);
                            
                            String durr = murr.getString("url");
                            
                            System.out.println(durr);
                            startVideo(durr);
                            
                            
                        } catch (JSONException ex) {
                            Logger.getLogger(VideoActivity.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println(googleJSONQuery + youtubeVideoID);
        new AsyncHTTPRequest(handler).httpGet(googleJSONQuery + youtubeVideoID);
        return "her";
    }
    
    public void startYoutubeApp(String URI){       
        System.out.println(videoURL);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoURL)));
        
        
    }
    
    /**
     * This method returns a Video based on the string value of it's ID.
     * 
     * 
     * @param videoID
     * @return Video
     */
    public Video getVideoData(String videoID) {
        
        //NYI
        return new Video(videoID, videoID, videoID, videoID, videoID, null, videoID);
    }

}
