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
import android.widget.Button;
import android.widget.TextView;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailExamFragment extends Fragment {
    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_exam, container, false);
        
        String examinfo = "For å melde deg til eksamen må du gjøre blablablablabalb blablablalb balblablbaal";

        TextView examtext = (TextView)fragmentView.findViewById(R.id.text);
        examtext.setText(examinfo);
        
        Button b = (Button)fragmentView.findViewById(R.id.exambutton);
        b.setText("Meld opp til eksamen");
        
        return fragmentView;
    }
}
