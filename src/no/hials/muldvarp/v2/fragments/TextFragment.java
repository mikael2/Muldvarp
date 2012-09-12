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
import no.hials.muldvarp.v2.domain.Date;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Help;
import no.hials.muldvarp.v2.domain.Requirement;

/**
 *
 * @author kristoffer
 */
public class TextFragment extends MuldvarpFragment {
    View fragmentView;
    private TextView text;
    public enum Type {NEWS, REQUIREMENT, HELP, DATE}
    TextFragment.Type type;
    TextView title;
    Domain item;
    
    public TextFragment(TextFragment.Type type) {
        this.type = type;
    }

    public TextFragment(TextFragment.Type type, Domain item) {
        this.type = type;
        this.item = item;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.v2_textlayout, container, false);
            title = (TextView)fragmentView.findViewById(R.id.title);
            text = (TextView)fragmentView.findViewById(R.id.text);
        }
        itemsReady();
        return fragmentView;
    }
    
    public void itemsReady() {
        switch(type) {
            case NEWS:
                title.setText(item.getName());
                text.setText(item.getDetail());
                break;
            case REQUIREMENT:
                Requirement r = activity.getRequirement();
                title.setText(r.getName());
                text.setText(r.getDetail());
                break;
            case HELP:
                Help h = activity.getHelp();
                title.setText(h.getName());
                text.setText(h.getDetail());
                break;
            case DATE:
                Date d = activity.getDate();
                title.setText(d.getName());
                text.setText(d.getDetail());
                break;
        }
    }
}
