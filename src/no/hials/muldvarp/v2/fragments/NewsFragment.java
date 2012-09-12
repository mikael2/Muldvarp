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

/**
 *
 * @author kristoffer
 */
public class NewsFragment extends MuldvarpFragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.news, container, false);

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
