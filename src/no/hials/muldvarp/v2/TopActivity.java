/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.utility.testUtils;
import no.hials.muldvarp.v2.utility.DummyDataProvider;

/**
 * This class defines a top-level activity for a given level. This activity-class
 * contains an ArrayList of Fragment classes named fragmentList which contains the
 * types and various fragments that comprise the main content of the Activity.
 * 
 * @author johan
 */
public class TopActivity extends MuldvarpActivity{
    
    //Global variables
    public ActionBar actionBar;
    private Activity thisActivity = this;
    public List<MuldvarpFragment> fragmentList = new ArrayList<MuldvarpFragment>();
    public String activityName;
        
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        
        super.onCreate(icicle);             
        //Set layout according to xml resource
        setContentView(R.layout.main);
                
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
       
        //Get action bar and make changes
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        
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
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);              
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        //Create Listener for the search bar in the top actionbar
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {                
                return false;
            }
            public boolean onQueryTextChange(String newText) {
                //Get current Fragment based on the currently selected index
                MuldvarpFragment tempFragment = fragmentList.get(getActionBar().getSelectedNavigationIndex());
                tempFragment.queryText(newText);                
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, no.hials.muldvarp.v2.TopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                Intent prefs = new Intent(this, MuldvarpPreferenceActivity.class);
                prefs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefs);
                return true;
            case R.id.test:
                intent = new Intent(this, QuizActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;        
            default:
                return super.onOptionsItemSelected(item);
        }
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
