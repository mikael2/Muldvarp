/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Course;

/**
 * Class defining the VideoCCourse-activity.
 * 
 * @author johan
 */
public class VideoCourseActivity extends Activity{
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set layout from video_main.xml using muldvarp.R
        setContentView(R.layout.video_courses);
        
        //Use ActionBar and configure the actionbar
        final ActionBar actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
        

        

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    

}
