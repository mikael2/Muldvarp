/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        
        try {
            mediaPlayer();
        } catch (IOException ex) {
            Logger.getLogger(VideoActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    
    public void mediaPlayer() throws IOException {
        
        String url = "http://www.mmhp.net/Sounds/Archive/MM2Fortress1.mp3"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
        mediaPlayer.start();
        
    }
}
