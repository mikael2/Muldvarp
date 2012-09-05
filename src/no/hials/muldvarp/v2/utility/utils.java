/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import java.util.List;

/**
 *
 * @author kristoffer
 */
public class utils {
    
    public static boolean changeFragment(Activity activity, List<Fragment> fragmentList, String[] strings, int layout, int position) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            if(fragmentList.get(position) == null)
                position = 0;
            ft.replace(layout, fragmentList.get(position), strings[position]);
            ft.commit();
        } catch(IndexOutOfBoundsException e) {
            Log.e(activity.getLocalClassName(), e.getMessage());
            return false;
        }
        return true;
    }
}
