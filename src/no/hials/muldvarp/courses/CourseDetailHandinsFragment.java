/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailHandinsFragment extends Fragment {
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_handin, container, false);
        
        listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        CourseDetailActivity activity = (CourseDetailActivity)getActivity();
        Course c = activity.getActiveCourse();
        
        ArrayList<ObligatoryTask> tasks = c.getObligatoryTasks();
            
            listview.setAdapter(
                new CourseDetailHandinListAdapter(
                        CourseDetailHandinsFragment.this.getActivity().getApplicationContext(),
                        R.layout.course_handin_list_item,
                        R.id.name,
                        tasks));
        
        return fragmentView;
    }
}


