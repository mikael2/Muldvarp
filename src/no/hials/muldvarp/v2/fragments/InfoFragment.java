/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.text.Html;
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
                
                s += "<br />" + p.getDescription() + "<br /><br />";
                s += "<br />" + p.getStructure() + "<br /><br />";
            } else if(owningActivity.domain instanceof Course) {
                Course c = (Course)owningActivity.domain;
                
                s += "<h1>Temaer:</h1>";
                for(Topic t : c.getTopics()) {
                    s += "<h2>" + t.getName() + "</h2>";
                    s += "<h3>Oppgaver:</h3>";
                    for(Task tt : t.getTasks()) {
                        s += "<div style='margin:10px;'>" + tt.getName() + "</div>";
                    }
                }
                
                s += "<h1>Obligatoriske Innleveringer:</h1>";
                for(ObligatoryTask ot : c.getObligatoryTasks()) {
                    s += ot.getName() + "     Status: ";
                    if(ot.getDone()) {
                        s += "Godkjent";
                    } else {
                        s += "Ikke Godkjent";
                    }
                    s += "<br />";
                }
                s += "<h1>Eksamener:</h1>";
                for(Exam e : c.getExams()) {
                    s += e.getName() + "     Rom: " + e.getRoom() + "      Dato:" + e.getExamDate() + "<br />";
                }
            }
            
            displayText.setText(Html.fromHtml(s));
        }
        
        return fragmentView;
    }
    
}
