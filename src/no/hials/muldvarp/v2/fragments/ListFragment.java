/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import no.hials.muldvarp.v2.TopActivity;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Task;
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
            if(owningActivity.domain == null) {
                items.addAll(DummyDataProvider.getFromDatabase(owningActivity));
            } else if(owningActivity.domain instanceof Programme) {
                List<Course> courseList = new ArrayList<Course>();
                courseList.add(new Course("Matte"));
                items.addAll(courseList);
            } else if(owningActivity.domain instanceof Course) {
                List<Task> taskList = new ArrayList<Task>();
                taskList.add(new Task("Integrering"));
                items.addAll(taskList);
            }
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
                
                if(selectedItem.getActivity() != null) {
                    destination = selectedItem.getActivity();
                    Intent myIntent = new Intent(view.getContext(), destination);
                    myIntent.putExtra("Domain", selectedItem);
                    startActivityForResult(myIntent, 0);
                } else {
                    Intent myIntent = new Intent(view.getContext(), TopActivity.class);
                    myIntent.putExtra("Domain", selectedItem);
                    startActivityForResult(myIntent, 0);
                    //Burde erstattes med en feilbeskjed fra en string i xml-fil
//                    Toast show = Toast.makeText(owningActivity, "Muldvarp vet ikke hvordan det skal åpne dette innlegget.", Toast.LENGTH_SHORT);
//                    show.show();
                }
                
                
            }
        });
        
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {   //triggers if a listitem is pressed and held.
                Domain selectedItem = items.get(pos);                                       //Saves the clicked item in the selectedItem variable.
                if(owningActivity.getLoggedIn() && selectedItem instanceof Course | true){  //TEST: remove the "| true" when the course level is working. - If the long-clicked item is a course, it is added to the users list of courses.
     //               Course course = (Course) selectedItem;                                //TEST: remove this comment line when the course level is working.
                    createDialog(selectedItem);                                             //Creates a new alertDialog asking whether the user wants to add the course to his/her favourites.
                    return true;                                                            //Tells the activity that the click has been "consumed", meaning that onItemClick should not be triggered.
                }
                else{                                                                       //If the clicked item isn't a course
                    return false;                                                           //Tells the activity that the click has not been "consumed", meaning that onItemClick will be triggered.
                }   
            }
        });
    }
    
    @Override
    public void queryText(String text){
        
        listAdapter.filter(text);
    }
    
    public void createDialog(final Domain c){                                                        //TEST: Change the argument type for this method to Course when the course level works
        AlertDialog.Builder builder = new AlertDialog.Builder(owningActivity);
        builder.setMessage("vil du legge til " + c.getName() + " i mine fag?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        //owningActivity.getService().getUser().addCourse(c);                        //TEST: uncomment this line when the course level works.
                        Toast toast = Toast.makeText(fragmentView.getContext(),                      //Shows a short toast to the user as feedback, telling him/her that the course has been added to the user list.
                        "Faget " + c.getName() + " er lagt til i mine fag.", Toast.LENGTH_SHORT);
                        toast.show();
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();                                                              //Dismisses the dialog without doing anything.
                   }
               });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
