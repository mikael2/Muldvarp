/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

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
import no.hials.muldvarp.utility.ListViewAdapter;

/**
 * Fragment defining a list. 
 * 
 * @author johan
 */
public class CustomListView extends Fragment {
    
    //Global variables
    String viewName = "";
    public String fragmentName = "";
    ListView listView;
    ArrayList<Object> currentListItems;
    

        
    /**
     * Called when the activity is first created.
     *  
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        //Set View and layout, using LayoutInflater to inflate layout form a XML-resource
        //The XML is located in /layout/--XML
        View returnFragmentView = inflater.inflate(R.layout.layout_listview, container, false);
        
         //Get ListView
        listView = (ListView)returnFragmentView.findViewById(R.id.layoutlistview);  
               
        
        //Set onItemClickListener
        //Defines what happens when an item in the list is "clicked"        
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int itemPosition, long id) {
                
                if (viewName.equalsIgnoreCase("Courses")) {
                    
                    Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoCourseActivity.class);
                    startActivityForResult(newIntent, 0);
                    
                    
                } else {
                
                
                Video selectedItem = (Video) currentListItems.get(itemPosition);
                
                
                //Create new Intent along with data to be passed on
                Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoActivity.class);
                newIntent.putExtra("videoID", selectedItem.getVideoID());
                newIntent.putExtra("videoName", selectedItem.getItemType());
                newIntent.putExtra("smallDetail", selectedItem.getSmallDetail());
                newIntent.putExtra("itemDescription", selectedItem.getItemDescription());
                
                //Start Activity
                startActivityForResult(newIntent, 0);
                
                }
            }
        });
                 
        
        
        //The setTextFilterEnabled(boolean) method turns on text filtering for the ListView, so that when the user begins typing, the list will be filtered.
        listView.setTextFilterEnabled(true);
               
        return returnFragmentView;
        
    }
    
    /**
     * Set the name of the view.
     * 
     * @param tabViewName String value of the view's name.
     */
    public void setViewName(String viewName) {
        
        this.viewName = viewName;
    }
    
    
    
    public void getAdapter(ArrayList itemList) {
        
        currentListItems = itemList;
                        
        ListViewAdapter listViewAdapter = new ListViewAdapter(this.getActivity().getApplicationContext(),
                                                              R.layout.course_list_item,
                                                              R.id.courselisttext,
                                                              itemList, true);        
        
        //Set ListViewAdapter
        listView.setAdapter(listViewAdapter);  
        
    }
    
    

    /**
     * Function which returns the fragment name.
     * 
     * @return Returns the fragment name.
     */
    public String getFragmentName() {
        return fragmentName;
    }

    
    /**
     * Function which sets the fragment name.
     * 
     * @param fragmentName The name of the fragment to be displayed in the title.
     */
    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
    

}
