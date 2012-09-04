/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Programme;

/**
 *
 * @author kristoffer
 */
public class ProgrammeListFragment extends Fragment {
    private EditText filterText;
    ProgrammeListAdapter adapter;
    ListView listview;
    GridView gridview;
    Boolean isGrid;
    View fragmentView;
    List<Programme> items;
    ProgrammeActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (ProgrammeActivity)ProgrammeListFragment.this.getActivity();
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

    @Override
    public void onResume() {
        super.onResume();
        if(items != null) {
            itemsReady();
        }
    }
    
    public void itemsReady() {
        
        if(items == null) {
            items = activity.getProgrammeList();
        }
        
        //listview = (ListView)fragmentView.findViewById(R.id.listview);
        if(isGrid) {
            gridview.setAdapter(
                new ProgrammeListAdapter(
                        fragmentView.getContext(),
                        R.layout.course_grid_list_item,
                        R.id.courselisttext,
                        items,
                        false)
                );
            adapter = (ProgrammeListAdapter)gridview.getAdapter();
        } else {
           listview.setAdapter(
            new ProgrammeListAdapter(
                    fragmentView.getContext(), 
                    R.layout.course_list_item, 
                    R.id.courselisttext, 
                    items,
                    true
                    )
            ); 
           adapter = (ProgrammeListAdapter)listview.getAdapter();
        }

        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
        
        if(isGrid) {
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Programme selectedItem = (Programme)items.get(position);
                    if(selectedItem.getCourses().size() > 0) {
                        Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }
                }  
            });
        } else {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Programme selectedItem = (Programme)items.get(position);
                    if(selectedItem.getCourses().size() > 0) {
                        Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                        myIntent.putExtra("id", selectedItem.getId());
                        startActivityForResult(myIntent, 0);
                    }
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
}
