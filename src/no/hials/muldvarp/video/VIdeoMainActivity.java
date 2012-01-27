/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.TabListener;

/**
 * Class defining the Video-activity.
 * 
 * @author johan
 */
public class VIdeoMainActivity extends Activity {
    
    //NYI
    private static final String MYVIDEOS_FRAGMENT  = "my";
    private static final String COURSEVIDEOS_FRAGMENT = "settings";
    private static final String STUDENTVIDEOS_FRAGMENT = "studentvideos";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set layout from video_main.xml using muldvarp.R
        setContentView(R.layout.video_main);
        
        //Use ActionBar and configure the actionbar
        final ActionBar actionBar = getActionBar();
        //Show the title of the application on top of the ActionBar
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        //Show tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        
        
        //Add entries to the bar
        actionBar.addTab(actionBar.newTab()
                .setText("My Videos")
                .setTabListener(new TabListener<VideoMyFragment>(
                        this, "my", VideoMyFragment.class)));
        actionBar.addTab(actionBar.newTab()
                .setText("Courses")
                .setTabListener(new TabListener<VideoCoursesFragment>(
                        this, "videocourses", VideoCoursesFragment.class)));
        actionBar.addTab(actionBar.newTab()
                .setText("ListView")
                .setTabListener(new TabListener<CustomListView>(
                        this, "listfragment", CustomListView.class)));
        

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
