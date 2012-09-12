/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.utility.utils;

/**
 *
 * @author kristoffer
 */
public class CourseActivity extends MuldvarpActivity {
    public List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Programme selectedProgramme;
    private Activity activity = this;
    public ActionBar actionBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.programme_list,
          android.R.layout.simple_spinner_dropdown_item);
        
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.programme_list);
            
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                return utils.changeFragment(activity, fragmentList, position);
            }
        };
        
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }
    
    @Override
    public List<Course> getCourseList() {
        return selectedProgramme.getCourses();
    }

    @Override
    public Programme getSelectedProgramme() {
        return selectedProgramme;
    }
}
