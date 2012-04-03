package no.hials.muldvarp.view;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mikael
 */
public class DefaultFragmentPagerAdapter extends FragmentPagerAdapter {
    ActionBar actionBar;
    Context   context;
    List<Fragment> views;
    

    public DefaultFragmentPagerAdapter(Context ctx, FragmentManager fm, ActionBar bar) {
        super(fm);
        actionBar = bar;
        context   = ctx;
        views = new ArrayList<Fragment>();
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    public void setActionBar(ActionBar bar) {
        actionBar = bar;
    }
    
    public void addView(Class<? extends Fragment> type) {    
        views.add(Fragment.instantiate(context, type.getName()));       
    }

}
