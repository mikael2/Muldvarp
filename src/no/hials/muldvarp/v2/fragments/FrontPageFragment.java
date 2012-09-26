/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpActivity;
import no.hials.muldvarp.v2.TopActivity;

/**
 * This class defines a MuldvarpFragment which purpose is to store data and provide logic
 * to display a gridview of Fragment selections in a MuldvarpActivity.
 * This Fragment does not actually handle any switching of fragments.
 * 
 * @author johan
 */
public class FrontPageFragment extends MuldvarpFragment {
    
    FrontPageFragment.ImageAdapter adapter;
    List<MuldvarpFragment> currentFragmentList;
    List<MuldvarpFragment> fragmentList;
    
    public FrontPageFragment(){
        
    }
    
    /**
     * Constructor for the FrontPageFragment class.
     * 
     * @param iconIesourceID 
     */
    public FrontPageFragment(String fragmentTitle, int iconResourceID) {
        super.fragmentTitle = fragmentTitle;
        super.iconResourceID = iconResourceID;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        //set some stuff, neato burrito
        currentFragmentList = getFragments();
        fragmentList = currentFragmentList;
        
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);
        
        GridView gridview = (GridView) retVal.findViewById(R.id.gridview);
        TextView headerTitle = (TextView) retVal.findViewById(R.id.textview);   
        
        fragmentTitle = owningActivity.activityName;
        headerTitle.setText(fragmentTitle);      
    
        adapter = new FrontPageFragment.ImageAdapter(getActivity());
        gridview.setAdapter(adapter);
        
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                owningActivity.getActionBar().setSelectedNavigationItem(position+1);
            }
        });
        
        return retVal;
    }
    
    public ArrayList<MuldvarpFragment> getFragments() {
        
        ArrayList<MuldvarpFragment> retVal = new ArrayList<MuldvarpFragment>();
        
        for (int i = 1; i < owningActivity.fragmentList.size(); i++) {
            
            retVal.add(owningActivity.fragmentList.get(i));
        }
        
        return retVal;
    }
    
    @Override
    public void queryText(String text){
        
        if(adapter!= null) {
            
            adapter.filter(text);
        }
        
    }

    class ImageAdapter extends BaseAdapter {
        
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
                
            return currentFragmentList.size();
        }

        public Object getItem(int position) {
            
            MuldvarpFragment muldvarpFragment = (MuldvarpFragment) currentFragmentList.get(position);
                        
            return muldvarpFragment.getIconResourceID();

        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View retVal = null;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                retVal = li.inflate(R.layout.desktop_icon, null);
                ImageView imageView = (ImageView) retVal.findViewById(R.id.grid_item_image);
                TextView  textView  = (TextView) retVal.findViewById(R.id.grid_item_label);
             
                imageView.setImageResource(currentFragmentList.get(position).getIconResourceID());
                textView.setText(currentFragmentList.get(position).getFragmentTitle());
            } else {
                retVal = convertView;
            }

            return retVal;
        }
        
        public void filter(CharSequence filterText) {
                        
            ArrayList filtered = new ArrayList();
        
            for (MuldvarpFragment m : (ArrayList<MuldvarpFragment>) fragmentList) {
                if (m.getFragmentTitle().toLowerCase().contains(filterText.toString().toLowerCase())) {
                    filtered.add(m);
                }
            }

            currentFragmentList = filtered;

            if("".equals(filterText.toString())) {
                currentFragmentList = fragmentList;
            }

            notifyDataSetChanged();   
        }
    }
}
