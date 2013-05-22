/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Exam;
import no.hials.muldvarp.v2.domain.ObligatoryTask;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Task;
import no.hials.muldvarp.v2.domain.Topic;

/**
 *
 * @author Kristoffer
 */
public class InfoFragment extends MuldvarpFragment {
    View fragmentView;
    int id;
    TextView displayText;
    
    public InfoFragment(String title, int iconResourceID, int id) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.id = id;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.infoview, container, false);
            displayText = (TextView) fragmentView.findViewById(R.id.textview);
            
            String s = "";
            if(owningActivity.domain instanceof Programme) {
                Programme p = (Programme)owningActivity.domain;
                
                s += "Beskrivelse:\n" + p.getDescription() + "\n\n";
                s += "Struktur???:\n" + p.getStructure() + "\n\n";
            } else if(owningActivity.domain instanceof Course) {
                Course c = (Course)owningActivity.domain;
                
                s += "Temaer:\n";
                for(Topic t : c.getTopics()) {
                    s += t.getName() + "\n\n";
                    s += "Oppgaver:\n";
                    for(Task tt : t.getTasks()) {
                        s += tt.getName();
                    }
                    s += "\n\n";
                }
                s += "\n";
                
                s += "Obligatoriske Innleveringer:\n\n";
                for(ObligatoryTask ot : c.getObligatoryTasks()) {
                    s += ot.getName() + "     Status: " + ot.getDone() + "\n";
                }
                s += "\n";
                s += "Eksamener\n";
                for(Exam e : c.getExams()) {
                    s += e.getName() + "     Rom: " + e.getRoom() + "      Dato:" + e.getExamDate() + "\n";
                }
            }
            
            displayText.setText(s);
        }
        
        return fragmentView;
    }
    
}
