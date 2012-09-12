/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

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
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.CourseActivity;
import no.hials.muldvarp.v2.ProgrammeActivity;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.utility.ListAdapter;

/**
 *
 * @author kristoffer
 */
public class ListFragment extends MuldvarpFragment {
    private EditText filterText;
    ListAdapter adapter;
    ListView listview;
    View fragmentView;
    List<Domain> items = new ArrayList<Domain>();
    public enum Type {PROGRAMME, VIDEO, COURSES, DOCUMENTS, NEWS}
    Type type;
    Class destination;
    boolean destinationIsFragment;
    Fragment fragment;

    public ListFragment(Type type) {
        this.type = type;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.course_list, container, false);
            listview = (ListView)fragmentView.findViewById(R.id.listview);
        }
        itemsReady();
        return fragmentView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(items != null) {
//            itemsReady();
//        }
//    }
    
    public void itemsReady() {
        if(items.isEmpty()) {
            switch(type) {
                case PROGRAMME:
                    items.addAll(activity.getProgrammeList());
                    destination = ProgrammeActivity.class;
                    break;
                case COURSES:
                    items.addAll(activity.getCourseList());
                    destination = CourseActivity.class;
                    break;
                case NEWS:
                    items.addAll(activity.getNewsList());
                    break;
                case VIDEO:
                    items.addAll(activity.getVideoList());
                    break;
                case DOCUMENTS:
                    items.addAll(activity.getDocumentList());
                    break;
            }
        }
        
        listview.setAdapter(new ListAdapter(
                    fragmentView.getContext(), 
                    R.layout.course_list_item, 
                    R.id.courselisttext, 
                    items,
                    true)
            ); 
        adapter = (ListAdapter)listview.getAdapter();

        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Domain selectedItem = items.get(position);
                Intent myIntent = new Intent(view.getContext(), destination);
                myIntent.putExtra("id", selectedItem.getId());
                startActivityForResult(myIntent, 0);
            }  
        });
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
