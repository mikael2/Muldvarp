/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseActivity extends Activity {    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_main);
                
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);  
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE); // ??

        ActionBar.Tab tab = actionBar.newTab();
        tab.setText("List")
           .setTabListener(new TabListener<CourseListFragment>(
           this, "List", CourseListFragment.class));
        actionBar.addTab(tab);        

        tab = actionBar.newTab();
        tab.setText("Grid")
           .setTabListener(new TabListener<CourseGridFragment>(
           this, "Grid", CourseGridFragment.class));
        actionBar.addTab(tab);
        
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
