/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Video;

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
        
        //Print some stuff based on extras from previous activity
        
        
        //Create button for youtube
        Button youtubeButton = (Button) findViewById(R.id.youtubeapp);
        youtubeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                
                startYoutubeApp("test");
                
            }
        });       
        
        
        System.out.println(videoID);
        System.out.println(videoName);
        System.out.println(videoDescription);
        
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
     * This function sets an onClickListener for a button resource, where the supplied
     * action is a class to be started as an intent.
     * 
     * @param buttonid the integer value of the buttons ID
     * @param action the class to be started as an intent
     * @param uri The string value of the URI 
     */
    private void createButton(int buttonid, final Class action, final String uri) {
        Button button = (Button) findViewById(buttonid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), action);
                myIntent.putExtra("uri", uri);
                startActivityForResult(myIntent, 0);
            }
        });
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
