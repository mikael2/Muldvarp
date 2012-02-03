/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import no.hials.muldvarp.MainActivity;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseActivity extends Activity {
    private boolean showGrid;
    private Fragment CourseListFragment = new CourseListFragment();
    private Fragment CourseGridFragment = new CourseGridFragment();
    private Fragment currentFragment;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_main);
                
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);  
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE); // ??

        addDynamicFragment(CourseListFragment); // default view

//        ActionBar.Tab tab = actionBar.newTab();
//        tab.setText(R.string.list)
//           .setTabListener(new TabListener<CourseListFragment>(
//           this, "List", CourseListFragment.class));
//        actionBar.addTab(tab);        
//
//        tab = actionBar.newTab();
//        tab.setText(R.string.grid)
//           .setTabListener(new TabListener<CourseGridFragment>(
//           this, "Grid", CourseGridFragment.class));
//        actionBar.addTab(tab);
//        
//        if (savedInstanceState != null) {
//            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("course", 0));
//        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("course", getActionBar().getSelectedNavigationIndex());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_activity, menu);
//        MenuItem menuItem = menu.findItem(R.id.actionItem);
//        
//        menuItem.setOnActionExpandListener(new OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                // Do something when collapsed
//                return true;  // Return true to collapse action view
//            }
//
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                // Do something when expanded
//                return true;  // Return true to expand action view
//            }
//        });
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
            case R.id.showgrid:
                if(showGrid == false) {
                    showGrid = true;
                    addDynamicFragment(CourseGridFragment);
                } else {
                    showGrid = false;
                    addDynamicFragment(CourseListFragment);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void addDynamicFragment(Fragment fg) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }

        ft.attach(fg);
        ft.add(R.id.course_layout, fg).commit();
        
        currentFragment = fg;        
    }
}
