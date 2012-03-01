package no.hials.muldvarp.view;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 *
 * @author mikael
 */
public class DefaultViewPagerTabListener implements ActionBar.TabListener {
    private ViewPager viewPager;

    public DefaultViewPagerTabListener(ViewPager pager) {
        viewPager = pager;
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}
