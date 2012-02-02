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
import android.widget.FrameLayout;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.TabListener;

/**
 * Class defining the Video-activity.
 * 
 * @author johan
 */
public class VideoMainActivity extends Activity implements ActionBar.TabListener {
    
    
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
        
        //Just to get different arrays
        String vidString = "";
        
        switch (type) {
            
            case 0: vidString = "AVID";
                    break;
            case 1: vidString = "DIV";
                    break;
            case 2: vidString = "IVD";
                    break;
            case 3: vidString = "AIVD";
                    break;
            default: break;
            
            
        }        
        
        //Array of vidya
        //Simulates a list of Videos generated from an external resource
        ArrayList<Video> videoArrayList = new ArrayList<Video>();
        
        for (int i = 0; i < 30; i++) {
            
            videoArrayList.add(new Video("VID" + (1000 + i), vidString + "Video" + i, "test", "test", "test", null, "test"));
            System.out.println(videoArrayList.get(i).getItemName()); //TEST
            
        }       
        
        return videoArrayList;
    }
    
    
    //Below are abastract methods from Tablisteenr.
    
    

    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        
        CustomListView customListView = (CustomListView) getFragmentManager().findFragmentById(R.id.customlistview);
        customListView.getAdapter(getData(tab.getPosition()));
        
    }

    
    //NYI
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        
    }

    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        
    }
    
    

}
