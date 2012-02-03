package no.hials.muldvarp;

import no.hials.muldvarp.desktop.DesktopFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import no.hials.muldvarp.courses.TabListener; // Denne tablisteneren fikser buggen
import no.hials.muldvarp.desktop.SettingsFragment;

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
           .setTabListener(new TabListener<SettingsFragment>(
           this, SETTINGS_FRAGMENT, SettingsFragment.class));
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }     
}
