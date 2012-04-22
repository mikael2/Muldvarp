package no.hials.muldvarp.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 *
 * @author mikael
 */
public class FragmentPager extends ViewPager {
    ActionBar bar;
    DefaultFragmentPagerAdapter adapter;
    DefaultViewPagerListener pageListener;
    
    public FragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentPager(Context context) {
        super(context);
    }
    
    public void initializeAdapter(FragmentManager manager, ActionBar bar) {
        this.bar = bar;
        adapter = new DefaultFragmentPagerAdapter(getContext(),manager,bar);
        adapter.setActionBar(bar);
        setAdapter(adapter);
        pageListener = new DefaultViewPagerListener(bar);
        super.setOnPageChangeListener(pageListener);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        pageListener.setOnPageChangeListener(listener);
    }
    

    /**
     * This method adds a tab to the FragmentPager's ActionBar associated with a Fragment. 
     * If a TabListener is not supplied, DefaultViewPagerTabListener will be used.
     * 
     * 
     * @param title The title of the Tab
     * @param fragment The fragment the tab is to be associated with
     * @param tabListener The tablistener to be used
     * @return 
     */
    public Tab addTab(String title, Class<? extends Fragment> fragment, TabListener tabListener) {
        
        
        if(adapter != null) {
            adapter.addView(fragment);
        }
        
        Tab retVal = bar.newTab();
                
        retVal.setText(title);
        
        //Check if tabListener is supplied
        if(tabListener != null){
            
            retVal.setTabListener(tabListener);
            
        } else {
            
            retVal.setTabListener(new DefaultViewPagerTabListener(this));
        }
        
        bar.addTab(retVal);
        
        
        return retVal;
    }
    
    /**
     * This function returns a Fragment based on it's position in the adapter view list.
     * 
     * @param position int
     * @return Fragment
     */
    public Fragment getFragmentInTab(int position){
        
        return adapter.getItem(position);
    }
    
    /**
     * Returns the size of the list of Views in the FragmentAdapter.
     * 
     * @return int
     */
    public int getTabListSize(){
        
        return adapter.getCount();
    }
    
    public DefaultFragmentPagerAdapter getAdapter(){
                
        return adapter;
    }
}
