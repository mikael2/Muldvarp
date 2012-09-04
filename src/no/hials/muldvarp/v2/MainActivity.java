/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.LoginActivity;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.MainPreferenceActivity;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.fragments.CourseListFragment;
import no.hials.muldvarp.v2.fragments.ProgrammeListFragment;
import no.hials.muldvarp.v2.fragments.TaskListFragment;
import no.hials.muldvarp.v2.utility.TabListener;

public class MainActivity extends Activity {
    
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<Programme> programmeList = new ArrayList<Programme>();
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if(fragmentList.isEmpty()) {
            fragmentList.add(new TaskListFragment());
            fragmentList.add(new TaskListFragment());
            fragmentList.add(new ProgrammeListFragment());
        }
        
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_list,
          android.R.layout.simple_spinner_dropdown_item);
        
        OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.main_list);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                try {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if(fragmentList.get(position) != null) {
                        ft.replace(R.id.desktop, fragmentList.get(position), strings[position]);
                    } else {
                        ft.replace(R.id.desktop, fragmentList.get(0), strings[0]);
                    }
                    ft.commit();
                } catch(IndexOutOfBoundsException e) {
                    Log.e(getLocalClassName(), e.getMessage());
                    return false;
                }
                return true;
            }
        };
        
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
        
        //createTabs(actionBar);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    private void createTabs(ActionBar actionBar) {
        actionBar.addTab(actionBar.newTab()
                .setText("Simple")
                .setTabListener(new TabListener<CourseListFragment>(
                        this,
                        "Tema",
                        CourseListFragment.class)));
        
        actionBar.addTab(actionBar.newTab()
                .setText("Simple")
                .setTabListener(new TabListener<CourseListFragment>(
                        this,
                        "Tema",
                        CourseListFragment.class)));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, no.hials.muldvarp.v2.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                Intent prefs = new Intent(this, MainPreferenceActivity.class);
                prefs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefs);
                return true;
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public List<Programme> getProgrammeList() {
        programmeList.add(new Programme("test"));
        return programmeList;
    }
}