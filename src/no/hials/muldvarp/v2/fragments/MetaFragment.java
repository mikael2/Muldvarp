/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MainActivity;

/**
 *
 * @author terje
 */
public class MetaFragment extends MuldvarpFragment {
    MainActivity activity;
    View fragmentView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)MetaFragment.this.getActivity();
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.metadata, container, false);
        }
        return fragmentView;
    }
    
    public void fillFields(Object o){
        
    }
}
