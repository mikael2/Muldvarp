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
public class VideoCourseActivity extends Activity implements ActionBar.TabListener {
    
    
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
        

          actionBar.addTab(actionBar.newTab()
                .setText("My Videos")
                .setTabListener(this));
          actionBar.addTab(actionBar.newTab()
                .setText("Courses")
                .setTabListener(this));
          actionBar.addTab(actionBar.newTab()
                .setText("Student")
                .setTabListener(this));
        

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    //Simulate data input
    public ArrayList getData(int type) {      
        
//        Array of vidya
//        Simulates a list of Videos generated from an external resource
//        Test array of courses
        ArrayList<Course> courseArrayList = new ArrayList<Course>();
        
        for (int i = 0; i < 30; i++) {
            
            courseArrayList.add(new Course("COR" + (1000 + i), "Coursename"));
            
        }
        
        return courseArrayList;
    }
    
    
    //Below are abastract methods from Tablistener
    
    
    /**
     * 
     * 
     * @param tab
     * @param arg1 
     */
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
                        
        
//        CustomListView customListView = (CustomListView) getFragmentManager().findFragmentById(R.id.customlistview);
//        customListView.getAdapter(getData(tab.getPosition()));
        
    }

    
    //NYI
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        
    }

    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        
    }
    
    

}
