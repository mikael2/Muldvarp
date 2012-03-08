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
import java.io.IOException;
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
        TextView textVideoTitle = (TextView) findViewById(R.id.videotitle);
        textVideoTitle.setText("Test Title");
                
        
        
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
    
    
    
    public void mediaPlayer() throws IOException {

        
    }
    
    public void startYoutubeApp(String URI){
        
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=Vxi7JRJrod4")));
        
        
    }
    
    public void test() {
        
        //VideoIntent går her
        Intent lVideoIntent = new Intent(null, Uri.parse("ytv://cxLG2wtE7TM"), this, VideoIntent.class);
       startActivity(lVideoIntent);
        
        
    }
}
