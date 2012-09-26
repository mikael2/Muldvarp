/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.os.Bundle;
import no.hials.muldvarp.v2.fragments.DetailFragment;
import no.hials.muldvarp.v2.utility.FragmentUtils;

/**
 * This class defines a Activity used for displaying information about an item, 
 * as well as providing functionality for interacting with it.
 * 
 * @author johan
 */
public class DetailActivity extends MuldvarpActivity {
    public enum Type {NEWS, VIDEO, DOCUMENTS}
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentUtils.changeFragmentWithoutList(this, new DetailFragment());
    }
}
