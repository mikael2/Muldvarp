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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Course;

/**
 *
 * @author kristoffer
 */
public class CourseListFragment extends Fragment {
    private EditText filterText;
    CourseListAdapter adapter;
    ListView listview;
    GridView gridview;
    Boolean isGrid;
    View fragmentView;
    List<Course> items;
    CourseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (CourseActivity)CourseListFragment.this.getActivity();
        isGrid = activity.getIsGrid();
        if(fragmentView == null) {
            if(isGrid) {
                fragmentView = inflater.inflate(R.layout.course_grid, container, false);
                gridview = (GridView)fragmentView.findViewById(R.id.gridview);
            } else {
                fragmentView = inflater.inflate(R.layout.course_list, container, false);
                listview = (ListView)fragmentView.findViewById(R.id.listview);
            }
        }
        
        return fragmentView;
    }
    
    public void itemsReady() {
        
        if(items == null) {
            items = activity.getCourseList();
        }
        
        //listview = (ListView)fragmentView.findViewById(R.id.listview);
        if(isGrid) {
            gridview.setAdapter(
                new CourseListAdapter(
                        fragmentView.getContext(),
                        R.layout.course_grid_list_item,
                        R.id.courselisttext,
                        items,
                        false)
                );
            adapter = (CourseListAdapter)gridview.getAdapter();
        } else {
           listview.setAdapter(
            new CourseListAdapter(
                    fragmentView.getContext(), 
                    R.layout.course_list_item, 
                    R.id.courselisttext, 
                    items,
                    true
                    )
            ); 
           adapter = (CourseListAdapter)listview.getAdapter();
        }

        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
               
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(fragmentView.getContext());
        boolean loggedin = prefs.getBoolean("debugloggedin", false);
        
        if(isGrid) {
            if(loggedin) {
                gridview.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Course selectedItem = (Course)items.get(position);
                        Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }  
                });
            } else {
                gridview.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Course selectedItem = (Course)items.get(position);
                        Intent myIntent = new Intent(view.getContext(), CoursePublicDetailActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }  
                }); 
            }
        } else {
            if(loggedin) {
                listview.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Course selectedItem = (Course)items.get(position);
                        Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }  
                });
            } else {
                listview.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Course selectedItem = (Course)items.get(position);
                        Intent myIntent = new Intent(view.getContext(), CoursePublicDetailActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }  
                }); 
            }
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
}
