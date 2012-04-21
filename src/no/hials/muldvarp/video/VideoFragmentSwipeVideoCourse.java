/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import no.hials.muldvarp.asyncvideo.VideoMainActivity;
import android.app.Activity;
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
import no.hials.muldvarp.entities.Video;
import no.hials.muldvarp.utility.ListViewAdapter;

/**
 * Fragment defining a list. The difference between this class and CustomListFragement is the version of Fragment that is extended, in
 * addition to other changes to make swiping possible.
 * 
 * @author johan
 */
public class VideoFragmentSwipeVideoCourse extends Fragment {
    
    //Global variables    
    //Activity Context
    VideoMainActivity owningActivity;
    
    String listItemType = "";
    public String fragmentName = "";
    ListView listView;
    ArrayList<Object> contentList; //ArrayList of Objects representing content
    
    //Switches
    public boolean enableFilter = true;
    
    ListViewAdapter listViewAdapter;
    
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
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
        owningActivity = (VideoMainActivity) this.getActivity();
        
        //Set View and layout, using LayoutInflater to inflate layout form a XML-resource
        //The XML is located in /layout/--XML
        View returnFragmentView = inflater.inflate(R.layout.layout_listview, container, false);
        
         //Get ListView
        listView = (ListView)returnFragmentView.findViewById(R.id.layoutlistview);    
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long id) {
                
                if (listItemType.equalsIgnoreCase("Courses")) {
                    
                    Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoCourseActivity.class);
                    startActivityForResult(newIntent, 0);                    
                    
                } else {               
                
                    Video selectedItem = (Video) contentList.get(itemPosition);
                    
                    //Create new Intent along with data to be passed on
                    //Should be changed to only supply ID and let the VideoActivity take care of the rest
                    Intent newIntent = new Intent(view.getContext().getApplicationContext(), VideoActivity.class);
                    newIntent.putExtra("videoID", selectedItem.getVideoID());
                    newIntent.putExtra("videoName", selectedItem.getItemName());
                    newIntent.putExtra("smallDetail", selectedItem.getSmallDetail());
                    newIntent.putExtra("itemDescription", selectedItem.getItemDescription());
                    newIntent.putExtra("videoURL", selectedItem.getVideoURL());

                    //Start Activity
                    startActivityForResult(newIntent, 0);
                
                }
            }
        }); //END of new OnItemClickListener
               
        return returnFragmentView;
        
    }       
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        
        super.onSaveInstanceState(outState);
    }
    
    /**
     * This function is called after the activity's oncreate is called.
     * 
     */
    @Override
    public void onResume(){
        
        super.onResume();
        requestItems();
    }
    
    public void requestItems(){
        
        System.out.println("VideoFragment: Requesting Items");
        owningActivity.updateFragments();
    }
       
    /**
     * This function updates the content model in the fragment.
     * 
     * @param contentList ArrayList of contents
     */
    public void updateContents(ArrayList contentList) {
        
        //Set global content list variable
        this.contentList = contentList;
                        
        //Create and set new adapter in the global variable
        listViewAdapter = new ListViewAdapter(this.getActivity().getApplicationContext(),
                                              R.layout.layout_listitem,
                                              R.id.text,
                                              contentList,
                                              true);        
        
        //Set ListViewAdapter with new adapter
        listView.setAdapter(listViewAdapter);  
        
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

    /**
     * Set the name of the view.
     * 
     * @param tabViewName String value of the view's name.
     */
    public void setViewName(String viewName) {
        
        this.listItemType = viewName;
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
