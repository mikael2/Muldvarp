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
import java.util.Arrays;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.ListViewAdapter;

/**
 * Fragment defining a list. 
 * 
 * @author johan
 */
public class CustomListView extends Fragment {
    
    //Global variables
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
        
        
        //Create test data array of strings
        //String[] testData = new String[]{"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar" }; 
        //setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.layout_listitem, testData));
        
        
        //Array of vidya
        //Simulates a list of Videos generated from an external resource
        ArrayList<Video> videoArrayList = new ArrayList<Video>();
        
        for (int i = 0; i < 30; i++) {
            
            videoArrayList.add(new Video("VID" + (1000 + i),"Video" + i, "test", "test", "test", null, "test"));
            System.out.println(videoArrayList.get(i).getItemName()); //TEST
            
        }       
        
        
        //Set ListViewAdapter
        //currently using test data
        getAdapter(videoArrayList);
        
        
        
        
        //Set onItemClickListener
        //Defines what happens when an item in the list is "clicked"
        
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int itemPosition, long id) {
                
                //Create new Intent along with data to be passed on
                Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoActivity.class);
                newIntent.putExtra("videoID", itemPosition);
                
                //Start Activity
                startActivityForResult(newIntent, 0);
            }
        });
                 
        
        
        //The setTextFilterEnabled(boolean) method turns on text filtering for the ListView, so that when the user begins typing, the list will be filtered.
        listView.setTextFilterEnabled(true);
               
        return returnFragmentView;
        
    }
    
    public void getAdapter(ArrayList itemList) {
        
        
                        
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
