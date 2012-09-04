/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 *
 * @author kristoffer
 */
public class utils {
    
    public static Fragment changeFragment(Activity activity, Fragment currentFragment, Fragment newFragment, int layout) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();

        if (currentFragment != null) {
            ft.detach(currentFragment);
        }

        ft.attach(newFragment);
        ft.add(layout, newFragment);
        ft.commit();
        
        return newFragment;
    }
}
