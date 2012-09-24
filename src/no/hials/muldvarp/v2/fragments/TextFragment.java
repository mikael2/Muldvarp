/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;

/**
 *
 * @author kristoffer
 */
public class TextFragment extends MuldvarpFragment {
    View fragmentView;
    private TextView text;
    public enum Type {REQUIREMENT, HELP, DATE, INFO}
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
//        switch(type) {
//            case REQUIREMENT:
//                item = owningActivity.getRequirement();
//                break;
//            case HELP:
//                item = owningActivity.getHelp();
//                break;
//            case DATE:
//                item = owningActivity.getDate();
//                break;
//            case INFO:
//                item = owningActivity.getInfo();
//                break;
//        }
//        title.setText(item.getName());
//        text.setText(Html.fromHtml(item.getDetail()));
    }
}
