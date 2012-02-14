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
import android.widget.TextView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailExamFragment extends Fragment {
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_exam, container, false);
        
        String examinfo = "For å melde deg til eksamen må du gjøre blablablablabalb blablablalb balblablbaal";

        TextView examtext = (TextView)fragmentView.findViewById(R.id.text);
        examtext.setText(examinfo);
        
        listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        CourseDetailActivity activity = (CourseDetailActivity)getActivity();
        Course c = activity.getActiveCourse();
        
        ArrayList<Exam> exams = c.getExams();
            
            listview.setAdapter(
                new CourseDetailExamListAdapter(
                        CourseDetailExamFragment.this.getActivity().getApplicationContext(),
                        R.layout.course_handin_list_item,
                        R.id.name,
                        exams));
        
        return fragmentView;
    }
}
