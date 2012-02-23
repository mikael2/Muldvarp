/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.Course;
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
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseListFragment extends Fragment {
    private EditText filterText;
    CourseListAdapter adapter;
    ListView listview;
    View fragmentView;
    ArrayList<Course> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("CourseListFragment onCreateView()");
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.course_list, container, false);
            listview = (ListView)fragmentView.findViewById(R.id.listview);
        }
        
        return fragmentView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    public void itemsReady() {
        
        if(items == null) {
            CourseActivity activity = (CourseActivity)CourseListFragment.this.getActivity();
            items = activity.getCourseList();
        }
        
        //listview = (ListView)fragmentView.findViewById(R.id.listview);
        listview.setAdapter(
            new CourseListAdapter(
                    fragmentView.getContext(), 
                    R.layout.course_list_item, 
                    R.id.courselisttext, 
                    items,
                    true
                    ));

        adapter = (CourseListAdapter)listview.getAdapter();

        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
               
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(fragmentView.getContext());
        boolean loggedin = prefs.getBoolean("debugloggedin", false);
        
        if(loggedin == true) {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Course selectedItem = (Course)items.get(position);
                    Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                    myIntent.putExtra("id", selectedItem.getName());
                    startActivityForResult(myIntent, 0);
                }  
            });
        } else {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Course selectedItem = (Course)items.get(position);
                    Intent myIntent = new Intent(view.getContext(), CoursePublicDetailActivity.class);
                    myIntent.putExtra("id", selectedItem.getName());
                    startActivityForResult(myIntent, 0);
                }  
            }); 
        }
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
    
//    @Override
//    public void onStop() {
//        super.onStop();
//        System.out.println("CourseListFragment onStop()");
//        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
//    }
}
