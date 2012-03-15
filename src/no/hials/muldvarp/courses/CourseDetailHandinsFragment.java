/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.ObligatoryTask;

/**
 *
 * @author kristoffer
 */
public class CourseDetailHandinsFragment extends Fragment {
    ListView listview;
    Course c;
    View fragmentView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.course_handin, container, false);
        
        listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        if(c == null) {
            CourseDetailActivity activity = (CourseDetailActivity)CourseDetailHandinsFragment.this.getActivity();
            c = activity.getActiveCourse();
        }
        
        ArrayList<ObligatoryTask> tasks = c.getObligatoryTasks();
            
        listview.setAdapter(
            new CourseDetailHandinListAdapter(
                    fragmentView.getContext(),
                    R.layout.course_handin_list_item,
                    R.id.name,
                    tasks));
        
        return fragmentView;
    }
    
    public void ready() {
        
    }
}


