/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MainActivity;

/**
 *
 * @author kristoffer
 */
public class VideoFragment extends MuldvarpFragment {
    Activity activity;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)VideoFragment.this.getActivity();
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.video_main, container, false);

//        ListView listview = (ListView) retVal.findViewById(R.id.listview);
//        
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                
//            }
//        });
        
        return retVal;
    }
}