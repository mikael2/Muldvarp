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
import no.hials.muldvarp.v2.fragments.DateFragment;
import no.hials.muldvarp.v2.fragments.DocumentFragment;
import no.hials.muldvarp.v2.fragments.InformationFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.NewsFragment;
import no.hials.muldvarp.v2.fragments.QuizFragment;
import no.hials.muldvarp.v2.fragments.RequirementFragment;
import no.hials.muldvarp.v2.fragments.VideoFragment;
import no.hials.muldvarp.v2.utility.utils;

/**
 *
 * @author kristoffer
 */
public class ProgrammeActivity extends Activity {
    public List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<Course> courseList = new ArrayList<Course>();
    private Activity activity = this;
    public ActionBar actionBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(fragmentList.isEmpty()) {
            fragmentList.add(new InformationFragment(InformationFragment.Type.PROGRAMME));
            fragmentList.add(new NewsFragment());            
            fragmentList.add(new ListFragment(ListFragment.Type.COURSES));
            fragmentList.add(new VideoFragment());
            fragmentList.add(new QuizFragment());
            fragmentList.add(new DocumentFragment());
            fragmentList.add(new RequirementFragment());
            fragmentList.add(new DateFragment());
        }
        
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_list,
          android.R.layout.simple_spinner_dropdown_item);
        
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.programme_list);
            
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                return utils.changeFragment(activity, fragmentList, strings, R.id.desktop, position);
            }
        };
        
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }
    
    public List<Course> getCourseList() {
        courseList.add(new Course("Matematikk B"));
        courseList.add(new Course("Programmering"));
        return courseList;
    }
}
