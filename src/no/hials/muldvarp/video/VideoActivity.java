/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.utility.AsyncHTTPRequest;

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
    
    
    //TEST
    static final int OPTION1 = 0;
    static final int OPTION2 = 1;
    
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set layout
        setContentView(R.layout.video_detail);
        
                
        //Get extras from previous activity
        //Gets data based on a key represented by a string
        Bundle extras = getIntent().getExtras();
        videoID = extras.getString("videoID");
        videoName = extras.getString("videoName");
        videoDescription = extras.getString("itemDescription");
        videoURL = extras.getString("videoURL");
        
        //Set activity title to be displayed in the top bar.
        setTitle(videoName);
        
        String xmlyoutubePath = "http://gdata.youtube.com/feeds/mobile/videos/";
        
//        String SrcPath = "http://daily3gp.com/vids/747.3gp";
        String SrcPath = "rtsp://v6.cache8.c.youtube.com/CjYLENy73wIaLQmCMG2_mc1LUhMYJCAkFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJ2fi6OQ3Pi0Tww=/0/0/0/video.3gp";
        VideoView myVideoView = (VideoView)findViewById(R.id.myvideoview);
//        myVideoView.setVisibility(1);
//        myVideoView.requestFocus();
        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(myVideoView);
        myVideoView.setVideoURI(Uri.parse(SrcPath));
        myVideoView.setMediaController(mediaController);
        
        myVideoView.start();
        
        
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
                
//                Toast.makeText(this, "Downloading video...", Toast.LENGTH_SHORT).show();
                
                
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
                System.out.println("IT WORKS?");
                
                //NYI
                return true;
                
            case R.id.menu_favourite:
                
                Toast.makeText(this, "Video added to favourites!", Toast.LENGTH_SHORT).show();
                
                //NYI
                return true;
            case R.id.menu_settings:
                
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

    /**
     * Function which sends a request for a Web Resource and dispatches a Handler to process the response.
     * 
     * @param itemType The type of item.
     */
    public void getItemFromWebResource() {        
        
        //Define handler
        //Defines what should happen depending on the returned message.
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


//        //Get resource URL and make asynchronous HTTP request
//        String resourceURL = resourceList.get(currentTab);
//        System.out.println("Reequesting resource:");
//        System.out.println(resourceURL);
//        new AsyncHTTPRequest(handler).httpGet(resourceURL);

    }
}
