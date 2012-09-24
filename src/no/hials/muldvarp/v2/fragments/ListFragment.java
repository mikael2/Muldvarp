/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.utility.DummyDataProvider;
import no.hials.muldvarp.v2.utility.ListAdapter;

/**
 * This class defines a fragment containing a list of specified ListItems.
 * 
 * @author johan
 */
public class ListFragment extends MuldvarpFragment {
    
    //Global variables
    ListAdapter listAdapter;
    ListView listView;
    View fragmentView;
    List<Domain> items = new ArrayList<Domain>();
    Class destination;
    Fragment fragment;

    public ListFragment(String fragmentTitle, int iconResourceID) {
        super.fragmentTitle = fragmentTitle;
        super.iconResourceID = iconResourceID;
    }
    
    public ListFragment(String fragmentTitle, int iconResourceID, List<Domain> items) {
        super.fragmentTitle = fragmentTitle;
        super.iconResourceID = iconResourceID;
        this.items = items;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.layout_listview, container, false);
            listView = (ListView)fragmentView.findViewById(R.id.layoutlistview);
        }
        itemsReady();
        return fragmentView;
    }
    
    public void setListItems(List<Domain> items){
        
        this.items = items;
    }
    
    public void setDestinationClass(Class destinationClass) {
        
        this.destination = destinationClass;
    }
    
    public void itemsReady() {        

        //If the items are empty, add temporary dummydata from database
        if(items.isEmpty()) {
            
            items.addAll(DummyDataProvider.getFromDatabase(owningActivity));                    
        }
        
        listView.setAdapter(new ListAdapter(
                    fragmentView.getContext(), 
                    R.layout.layout_listitem, 
                    R.id.text, 
                    items,
                    true)
            ); 
        listAdapter = (ListAdapter) listView.getAdapter();
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                //The idea is that one click should start a corresponding activity.
                Domain selectedItem = items.get(position);
                
                if(selectedItem.getActivity() != null){
                    destination = selectedItem.getActivity();
                    Intent myIntent = new Intent(view.getContext(), destination);
                    myIntent.putExtra("Domain", selectedItem);
                    startActivityForResult(myIntent, 0);
                } else {
                    
                    //Burde erstattes med en feilbeskjed fra en string i xml-fil
                    Toast show = Toast.makeText(owningActivity, "Muldvarp vet ikke hvordan det skal åpne dette innlegget.", Toast.LENGTH_SHORT);
                    show.show();
                }
                
                
            }  
        });
    }
    
    @Override
    public void queryText(String text){
        
        listAdapter.filter(text);
    }

}
