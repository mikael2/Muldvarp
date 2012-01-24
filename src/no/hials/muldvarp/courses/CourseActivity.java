/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import no.hials.muldvarp.desktop.TabListener;
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
        
        
        
        
//        Resources res = getResources(); // Resource object to get Drawables
//        TabHost tabHost = getTabHost();  // The activity TabHost
//        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
//        Intent intent;  // Reusable Intent for each tab
//
//        // Create an Intent to launch an Activity for the tab (to be reused)
//        intent = new Intent().setClass(this, CourseListActivity.class);
//
//        // Initialize a TabSpec for each tab and add it to the TabHost
//        spec = tabHost.newTabSpec("list").setIndicator("List").setContent(intent);
//        tabHost.addTab(spec);
//
//        intent = new Intent().setClass(this, CourseGridActivity.class);
//        spec = tabHost.newTabSpec("grid").setIndicator("Grid").setContent(intent);
//        tabHost.addTab(spec);
//
//        tabHost.setCurrentTab(2);
    }
}
