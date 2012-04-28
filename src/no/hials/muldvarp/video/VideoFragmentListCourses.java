/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.Course;
import no.hials.muldvarp.entities.ListItem;
import no.hials.muldvarp.entities.Programme;
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.utility.ListViewAdapter;

/**
 * Fragment defining a list. The difference between this class and CustomListFragement is the version of Fragment that is extended, in
 * addition to other changes to make swiping possible.
 * 
 * @author johan
 */
public class VideoFragmentListCourses extends Fragment {
    
    //Global variables
     //Activity Context
    VideoProgrammeActivity owningActivity;
    String listItemType = "";
    public String fragmentName = "";
    ListView listView;
    ArrayList<ListItem> currentListItems;
    
    //Switches
    public boolean enableFilter = true;
    
    //FilterText solution
    //private EditText filterText = null;
    ListViewAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setRetainInstance(true);
    }
        
    /**
     * Creates the view when called.
     *  
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        
        //Set the Activity that owns this Fragment as a global variable
        owningActivity = (VideoProgrammeActivity) this.getActivity();
        
        //Set View and layout, using LayoutInflater to inflate layout form a XML-resource
        //The XML is located in /layout/--XML
        View returnFragmentView = inflater.inflate(R.layout.layout_listview, container, false);
        
         //Get ListView
        listView = (ListView)returnFragmentView.findViewById(R.id.layoutlistview);          
        
        //Set filterText and add listener
//        filterText = (EditText)returnFragmentView.findViewById(R.id.search_box);
//        filterText.addTextChangedListener(filterTextWatcher);               
        
        //Set onItemClickListener
        //Defines what happens when an item in the list is "clicked"        
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int itemPosition, long id) {
                
                Bundle newBundle = new Bundle();
                
                 ListItem selectedItem = (Course) currentListItems.get(itemPosition);
                    Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoCourseActivity.class);
                    newBundle.putSerializable("courseListItem", selectedItem);
                    newIntent.putExtras(newBundle);
                    startActivityForResult(newIntent, 0);
            }
        });          
               
        return returnFragmentView;
    }       

    /**
     * This function is called after the activity's oncreate is called.
     * 
     */
    @Override
    public void onResume(){
        
        super.onResume();
        System.out.println("onResume called by " + fragmentName + "Fragment");
        requestContent();      
    }    
    
    public boolean requestContent(){
        
        if(currentListItems == null && owningActivity != null){

            System.out.println(getFragmentName() + "Fragment: Requesting items:" + listItemType);
            if(owningActivity.requestItems(listItemType)){
                System.out.println(getFragmentName() + "Fragment: Request successful:" + listItemType);
                return false;
            } else {
                System.out.println(getFragmentName() + "Fragment: Request failed:" + listItemType);
                //Do something to compensate.
                return false;
            }        
        } else {
            
            updateContent(currentListItems);
            return true;
        } 
               
    }
       
    
    /**
     * This function updates the listView in the fragment by creating a new ListViewAdapter and setting it.
     * 
     * @param itemList 
     */
    public void updateContent(ArrayList<ListItem> listItems) {
        
        if(owningActivity != null){
            
            currentListItems = listItems;
            adapter = new ListViewAdapter(owningActivity.getApplicationContext(),
                                                                R.layout.course_list_item,
                                                                R.id.courselisttext,
                                                                listItems, true);     
            //Set ListViewAdapter
            listView.setAdapter(adapter);     
        }        
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
    
    /**
     * Returns the string value of the ListItem type in the Fragment.
     * 
     * @return String 
     */
    public String getListItemType() {
        
        return listItemType;
    }

    /**
     * Sets the listItemType String in the Fragment.
     * 
     * @param listItemType 
     */
    public void setListItemType(String listItemType) {
        
        this.listItemType = listItemType;
    }
    
    
    
//    /**
//     * TextWatcher used to update the list.
//     */
//    private TextWatcher filterTextWatcher = new TextWatcher() {
//
//        /**
//         * Defines what happens after a text change.
//         */
//        public void afterTextChanged(Editable s) {
//        }
//
//        /**
//         * Name says it all
//         */
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        /**
//         * Defines what should happen when the text changes.
//         */
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            
//            //Set adapter for use by the Filter function
//            adapter = (ListViewAdapter) listView.getAdapter();
//            if(adapter != null){
//                adapter.filter(s);
//            }
//        }
//    };
    

}
