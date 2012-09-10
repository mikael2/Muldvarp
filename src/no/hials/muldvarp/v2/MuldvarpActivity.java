/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @author kristoffer
 */
public class MuldvarpActivity extends Activity {
    Bundle savedInstanceState;
    
    @Override
    public void onBackPressed() {
        if(getActionBar().getSelectedNavigationIndex() > 0)
            getActionBar().setSelectedNavigationItem(0);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(savedInstanceState != null)
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt("tab"));
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
