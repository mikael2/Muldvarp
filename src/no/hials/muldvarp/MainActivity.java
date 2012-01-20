package no.hials.muldvarp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.desktop.TabListener;

public class MainActivity extends Activity
{
    private static final String DESKTOP_FRAGMENT  = "desktop";
    private static final String SETTINGS_FRAGMENT = "settings";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);  

        ActionBar.Tab tab = actionBar.newTab();
        tab.setText(R.string.desktop_name)
           .setTabListener(new TabListener<DesktopFragment>(
           this, DESKTOP_FRAGMENT, DesktopFragment.class));
        actionBar.addTab(tab);        

        tab = actionBar.newTab();
        tab.setText(R.string.settings_name)
           .setTabListener(new TabListener<DesktopFragment>(
           this, SETTINGS_FRAGMENT, DesktopFragment.class));
        actionBar.addTab(tab);           
        
        /*Button testbutton = (Button) findViewById(R.id.coursesbutton);
        testbutton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });*/
    }
    
}
