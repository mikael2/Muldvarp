/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseListFragment extends Fragment {
    private EditText filterText = null;
    CourseListAdapter adapter = null;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View fragmentView = inflater.inflate(R.layout.course_list, container, false);
        
        ListView listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        // testdata
        ArrayList array = new ArrayList();
        for(int i = 0; i <= 20; i++) {
           Course c = new Course("Longt seriøst fagnavn " + i, "ID10101010");
           array.add(c); 
        }
        
        Course c = new Course("Ikontest", "blablabla", "http://developer.android.com/assets/images/bg_logo.png");
        array.add(c);
        
        Gson gson = new Gson();
        c = new Course("Gson test", "blablabla");
        String json = gson.toJson(c);
        
        Course c2 = gson.fromJson(json, Course.class);
        array.add(c2);
        
        
        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        
        
        listview.setAdapter(
                new CourseListAdapter(
                        this.getActivity().getApplicationContext(), 
                        R.layout.course_list_item, 
                        R.id.courselisttext, 
                        array,
                        true
                        ));
        
        adapter = (CourseListAdapter)listview.getAdapter();
               
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        boolean loggedin = prefs.getBoolean("debugloggedin", false);

        
        if(loggedin == true) {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                    startActivityForResult(myIntent, 0);
                }  
            });
        } else {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    
                    
                    Toast.makeText(getActivity().getApplicationContext(), ((TextView) view).getText(),
                    Toast.LENGTH_SHORT).show();
                    
                    
                    Intent myIntent = new Intent(view.getContext(), CoursePublicDetailActivity.class);
                    startActivityForResult(myIntent, 0);
                }  
            }); 
        }
        
        
        return fragmentView;
    }
    
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            adapter.filter(s);
        }

    };
}
