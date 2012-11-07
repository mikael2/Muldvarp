/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.os.Bundle;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;

/**
 * This class defines a Activity used for displaying information about an item,
 * as well as providing functionality for interacting with it.
 *
 * @author johan
 */
public class DetailActivity extends MuldvarpActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FragmentUtils.changeFragmentWithoutList(this, new DetailFragment());
        if(getIntent().hasExtra("Domain")) {
            domain = (Domain) getIntent().getExtras().get("Domain");
            activityName = domain.getName();
        } else {
            activityName = getResources().getString(R.string.app_logo_top);
            domain = new Domain(activityName);
            System.out.println("HEERP");
            //Should include more descriptions from Strings
        }
        if(fragmentList.isEmpty()) {
            domain.populateList(fragmentList, this);
        }
    }

}
