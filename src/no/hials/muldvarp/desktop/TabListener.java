package no.hials.muldvarp.desktop;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;


/**
 *
 * @author mikael
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment fragment;
    private Activity activity;
    private String   tag;
    private Class<T> type;

    
    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag       The identifier tag for the fragment
      * @param type      The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> type) {
        this.activity = activity;
        this.tag      = tag;
        this.type     = type;
    }


    /* The following are each of the ActionBar.TabListener callbacks */
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (fragment == null) {
            // If not, instantiate and add it to the activity
            fragment = Fragment.instantiate(activity, type.getName());
            ft.add(android.R.id.content, fragment, tag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(fragment);
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}
