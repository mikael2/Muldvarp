/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;

/**
 * Fragment defining the "My Videos" section.
 * 
 * @author johan
 */
public class VideoMyFragment extends Fragment {

    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        //Set View and layout, using LayoutInflater to inflate layout form a XML-resource
        //The XML is located in /layout/--XML
        View retVal = inflater.inflate(R.layout.layout_listview, container, false);
        
        
        

        
        return retVal;
        
    }
    

}
