/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.v2.MuldvarpActivity;

/**
 * This class defines a Fragment in the context of the Muldvarp application.
 * It contains global variables with get/set methods and logic common for all Fragments that are
 * used in the Muldvarp application.
 *
 * @author johan
 */
public class MuldvarpFragment extends Fragment {

    //Global variables
    MuldvarpActivity owningActivity;
    String fragmentTitle;
    int iconResourceID;
    String searchQuery;

    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.owningActivity = (MuldvarpActivity) activity;
    }

    public void queryText(String text){
        System.out.println("Not yet Implemented, override in Fragment");
    }

    public int getIconResourceID() {
        return iconResourceID;
    }

    public void setIconResourceID(int iconResourceID) {
        this.iconResourceID = iconResourceID;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }


}
