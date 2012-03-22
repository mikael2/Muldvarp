/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.video;

import android.app.ActionBar;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Not yet in use.
 * 
 * @author johan
 */
public class VideoViewPagerListener implements OnPageChangeListener {
    
    ActionBar bar;
    OnPageChangeListener userListener;

    public VideoViewPagerListener(ActionBar bar) {
        this.bar = bar;
    }

    @Override
    public void onPageSelected(int position) {
        bar.getTabAt(position).select();
        if (userListener != null) {
            userListener.onPageSelected(position);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (userListener != null) {
            userListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

    }

    public void onPageScrollStateChanged(int state) {
        if (userListener != null) {
            userListener.onPageScrollStateChanged(state);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        userListener = listener;
    }
}
