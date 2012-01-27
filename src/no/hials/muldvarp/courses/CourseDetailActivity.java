/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.TabListener;

/**
 *
 * @author kristoffer
 */
public class CourseDetailActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        
        // BUG innhold i fragments henger igjen etter orientation skifte
        
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);  
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE); // ??

        ActionBar.Tab tab = actionBar.newTab();
        tab.setText("Work")
           .setTabListener(new TabListener<CourseDetailWorkFragment>(
           this, "Work", CourseDetailWorkFragment.class));
        actionBar.addTab(tab);        

        tab = actionBar.newTab();
        tab.setText("Handins")
           .setTabListener(new TabListener<CourseDetailHandinsFragment>(
           this, "Handins", CourseDetailHandinsFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab();
        tab.setText("Exam")
           .setTabListener(new TabListener<CourseDetailExamFragment>(
           this, "Exam", CourseDetailExamFragment.class));
        actionBar.addTab(tab);
    }
}
