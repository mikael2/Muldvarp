/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.utility.FragmentUtils;

/**
 * This class defines a top-level activity for a given level. This activity-class
 * contains an ArrayList of Fragment classes named fragmentList which contains the
 * types and various fragments that comprise the main content of the Activity.
 *
 * @author johan
 */
public class TopActivity extends MuldvarpActivity{
    private Activity thisActivity = this;
    SpinnerAdapter mSpinnerAdapter = null;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //See if the Activity was started with an Intent that included a Domain object
        if(getIntent().hasExtra("Domain")) {
            domain = (Domain) getIntent().getExtras().get("Domain");
            activityName = domain.getName();
            
            //Add fragments to list if empty:
            if(fragmentList.isEmpty()) {
//                domain.populateList(fragmentList, this);
                domain.constructList(fragmentList, domain.getFragments());
            }

            //Get dropdown menu using standard menu
            mSpinnerAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    getDropDownMenuOptions(fragmentList));

            ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int position, long itemId) {
                    return FragmentUtils.changeFragment(thisActivity, fragmentList, position);
                }
            };
            getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
            
            // We use this to send broadcasts within our local process.
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
             // We are going to watch for interesting local broadcasts.
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_ALL_UPDATE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
//                    domain = mService.
                    domain.constructList(fragmentList, domain.getFragments());
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        } else {
            setUpFrontpage();
        }
    }
    
    public void setUpFrontpage() {
        activityName = getResources().getString(R.string.app_logo_top);
            // We use this to send broadcasts within our local process.
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
         // We are going to watch for interesting local broadcasts.
        IntentFilter filter = new IntentFilter();
        filter.addAction(MuldvarpService.ACTION_FRONTPAGE_UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                domain = mService.getFrontpage();
                
                domain.constructList(fragmentList, domain.getFragments());

                //Get dropdown menu using standard menu
                mSpinnerAdapter = new ArrayAdapter(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getDropDownMenuOptions(fragmentList));

                ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
                    @Override
                    public boolean onNavigationItemSelected(int position, long itemId) {
                        return FragmentUtils.changeFragment(thisActivity, fragmentList, position);
                    }
                };
                getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        setUpdated();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    /**
     * This function generates a List of String values based on the titles of
     * a List of MuldvapFragments.
     *
     * @param fragmentList List of MuldvarpFragments
     * @return List
     */
    public List getDropDownMenuOptions(List<MuldvarpFragment> fragmentList){
        List retVal = new ArrayList();
        for (int i = 0; i < fragmentList.size(); i++) {
            retVal.add(fragmentList.get(i).getFragmentTitle());
        }
        return retVal;
    }

    public Domain getDomain() {
        return domain;
    }
}
