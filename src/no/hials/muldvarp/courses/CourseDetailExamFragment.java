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
import android.widget.TextView;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.Exam;

/**
 *
 * @author kristoffer
 */
public class CourseDetailExamFragment extends Fragment {
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
        //if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.course_exam, container, false);
            String examinfo = "For å melde deg til eksamen må du gjøre blablablablabalb blablablalb balblablbaal";
            TextView examtext = (TextView)fragmentView.findViewById(R.id.text);
            examtext.setText(examinfo);
            listview = (ListView)fragmentView.findViewById(R.id.listview);
        //}
        if (c != null) {
            ArrayList<Exam> exams = c.getExams();
            
            listview.setAdapter(
                new CourseDetailExamListAdapter(
                        fragmentView.getContext(),
                        R.layout.course_handin_list_item,
                        R.id.name,
                        exams));
        }
        return fragmentView;
    }
    
    public void ready(Course course) {
//        if(c == null) {
//            CourseDetailActivity activity = (CourseDetailActivity)CourseDetailExamFragment.this.getActivity();
//            c = activity.getActiveCourse();
            this.c = course;
//        }
    }
}
