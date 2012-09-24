/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments.deprecated;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import no.hials.muldvarp.v2.deprecated.MuldvarpActivity;

/**
 *
 * @author kristoffer
 */
public class MuldvarpFragment extends Fragment {
    MuldvarpActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MuldvarpActivity)activity;
    }
}
