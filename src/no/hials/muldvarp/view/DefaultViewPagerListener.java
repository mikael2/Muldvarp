package no.hials.muldvarp.view;

import android.app.ActionBar;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;

/**
 *
 * @author mikael
 */
public class DefaultViewPagerListener extends SimpleOnPageChangeListener {
    ActionBar bar;
    
    public DefaultViewPagerListener(ActionBar bar) {
        this.bar = bar;
    }    

    @Override
    public void onPageSelected(int position) {
        bar.getTabAt(position).select();
    }
}
