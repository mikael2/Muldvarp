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
import no.hials.muldvarp.v2.MainActivity;

/**
 * This class defines a Fragment used for displaying information about an item.
 * 
 * @author Johan
 */
public class MetaFragment extends MuldvarpFragment {
    MainActivity activity;
    View fragmentView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)MetaFragment.this.getActivity();
        
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.metadata, container, false);
            
            TextView textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
            textItemTitle.setText("Hardkodet Tittel");

            TextView textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
            textItemDescription.setText("Hardkodet beskrivelse av tingen");
            
        }
        return fragmentView;
    }
    
    
    public void fillFields(Object o){
        
        TextView textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
        textItemTitle.setText("Hardkodet Tittel");
        
        TextView textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
        textItemDescription.setText("Hardkodet Tittel");
    }
}
