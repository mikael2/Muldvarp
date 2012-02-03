/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailHandinsFragment extends Fragment {
    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_handin, container, false);
        
        ListView listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        ArrayList array = new ArrayList();
        
        // testdata
        Handin h = new Handin("Obligatorisk 1");
        array.add(h);
        h = new Handin("Obligatorisk 2");
        array.add(h);
        
        
        listview.setAdapter(
                new CourseDetailHandinListAdapter(
                        this.getActivity().getApplicationContext(),
                        R.layout.course_handin_list_item,
                        R.id.name,
                        array));

        listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                    Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
//                    startActivityForResult(myIntent, 0);
                }  
            });
        
        return fragmentView;
    }
}


