/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 * Fragment defining a list. 
 * 
 * @author johan
 */
public class CustomListView extends ListFragment {
    
    public String fragmentName = "";
    

        
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
        
        
        
        //Create test data array of strings
        String[] testData = new String[]{"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar" }; 
        
        
        //Array of vidya
        ArrayList<Video> videoArrayList = new ArrayList<Video>();
        
        for (int i = 0; i < 10; i++) {
            
            videoArrayList.add(new Video("Video" + i, "Herf", "Derf", null, "Hurf"));
            
        }
        
        
        
        
              
        setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.layout_listitem, testData));
        
        //Get ListView
        ListView listView = (ListView)returnFragmentView.findViewById(R.id.layoutlistview);       
                
        
        
        //The setTextFilterEnabled(boolean) method turns on text filtering for the ListView, so that when the user begins typing, the list will be filtered.
        listView.setTextFilterEnabled(true);
        

        
        return returnFragmentView;
        
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
