/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import no.hials.muldvarp.desktop.TabListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        
        // BUG innhold i fragments henger igjen etter orientation skifte
        
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
  
    }
    
    
}
