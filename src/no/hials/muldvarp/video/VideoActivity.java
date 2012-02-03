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
        setContentView(R.layout.library_detail);
        
        //Get extras from previous activity
        //Gets data based on a key represented by a string
        Bundle extras = getIntent().getExtras();
        int videoID = extras.getInt("videoID");
        
        //Print some stuff based on extras from previous activity
        //TextView t1 = (TextView)findViewById(R.id.texttitle);
        //t1.setText(Integer.toString(videoID));
        
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
