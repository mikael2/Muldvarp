/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import no.hials.muldvarp.R;

/**
 *
 * @author johan
 */
public class VideoActivity extends Activity {

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
        String videoID = extras.getString("videoID");
        System.out.println(videoID);
        
        //Print some stuff based on extras from previous activity
        
        //Video ID
        TextView textVideoID = (TextView) findViewById(R.id.videoID);
        textVideoID.setText(videoID);
        
        //Video Title
        TextView textVideoTitle = (TextView) findViewById(R.id.videotitle);
        textVideoTitle.setText("Test Title");
                
        
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
