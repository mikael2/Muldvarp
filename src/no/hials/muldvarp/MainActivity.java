package no.hials.muldvarp;

import no.hials.muldvarp.desktop.DesktopFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import no.hials.muldvarp.desktop.SettingsFragment;
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
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE); // ??
        
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
}
