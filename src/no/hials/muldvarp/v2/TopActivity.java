/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;
import no.hials.muldvarp.v2.utility.testUtils;

/**
 * This class defines a top-level activity for a given level. This activity-class
 * contains an ArrayList of Fragment classes named fragmentList which contains the
 * types and various fragments that comprise the main content of the Activity.
 * 
 * @author johan
 */
public class TopActivity extends MuldvarpActivity{
    
    //Global variables
    private Activity thisActivity = this;
        
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        
        super.onCreate(icicle);             
        //Set layout according to xml resource
//        setContentView(R.layout.main);
                
        //See if the Activity was started with an Intent that included a Domain object
        if(getIntent().hasExtra("Domain")){
            
            Domain domain = (Domain) getIntent().getExtras().get("Domain");            
            activityName = domain.getName();
            
        } else {
            activityName = getResources().getString(R.string.app_logo_top);
        }
        
        //Add fragments to list if not empty:
        if(fragmentList.isEmpty()){            
            setupContent();
        }
        
        //Get dropdown menu
        SpinnerAdapter mSpinnerAdapter = new ArrayAdapter(this, 
                android.R.layout.simple_spinner_dropdown_item,
                getDropDownMenuOptions(fragmentList));
        
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                
                return testUtils.changeFragment(thisActivity, fragmentList, position);
            }
        };        
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);              
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    public void setupContent() {
        //Add main fragment ("home fragment").
        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
        //Then the rest.        
        fragmentList.add(new ListFragment("Informasjon", R.drawable.stolen_contacts));
        fragmentList.add(new ListFragment("Nyheter", R.drawable.stolen_tikl));
        ListFragment gridFragmentList = new ListFragment("Studier", R.drawable.stolen_smsalt);
        gridFragmentList.setListItems(DummyDataProvider.getProgrammeList(this));        
        fragmentList.add(gridFragmentList);
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube));        
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList()));
        fragmentList.add(new ListFragment("Dokumenter", R.drawable.stolen_dictonary));
        fragmentList.add(new ListFragment("Opptak", R.drawable.stolen_notes));
        fragmentList.add(new ListFragment("Datoer", R.drawable.stolen_calender));
        fragmentList.add(new ListFragment("Hjelp", R.drawable.stolen_help));
    }
    
    public List getDropDownMenuOptions(List<MuldvarpFragment> fragmentList){
        
        List retVal = new ArrayList();        
        for (int i = 0; i < fragmentList.size(); i++) {
                        
            retVal.add(fragmentList.get(i).getFragmentTitle());
        }        
        return retVal;
    }
    
}
