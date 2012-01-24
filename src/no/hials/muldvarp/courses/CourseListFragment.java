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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View fragmentView = inflater.inflate(R.layout.course_list, container, false);
        
        ListView listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        listview.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.course_list_item, R.id.courselisttext, COURSELISTITEMS));
        listview.setTextFilterEnabled(true);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                startActivityForResult(myIntent, 0);
            }  
        });  
        
        return fragmentView;
    }
    
    static final String[] COURSELISTITEMS = new String[] {
        "Test", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10"
    };
}
