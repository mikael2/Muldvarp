/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import no.hials.muldvarp.v2.adapter.SectionedListAdapter;

/**
 *
 * @author johan
 */
public class SectionHeaderView extends ListView {
	
    View listFooter;
    boolean footerViewAttached = false;
    private View mHeaderView;
    private boolean mHeaderViewVisible;
    private int mHeaderViewWidth;
    private int mHeaderViewHeight;
    private SectionedListAdapter adapter;
    
    public void setPinnedHeaderView(View view) {
        mHeaderView = view;
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            configureHeaderView(getFirstVisiblePosition());
        }
    }

    public void configureHeaderView(int position) {
        if (mHeaderView == null) {
            return;
        }

        int state = adapter.getPinnedHeaderState(position);
        System.out.println("LOLOLOLO");
        switch (state) {
            case SectionedListAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }

            case SectionedListAdapter.PINNED_HEADER_VISIBLE: {
            	adapter.configurePinnedHeader(mHeaderView, position, 255);
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderViewVisible = true;
                break;
            }

            case SectionedListAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                if (firstView != null) {
	                int bottom = firstView.getBottom();
	                int headerHeight = mHeaderView.getHeight();
	                int y;
	                int alpha;
	                if (bottom < headerHeight) {
	                    y = (bottom - headerHeight);
	                    alpha = 255 * (headerHeight + y) / headerHeight;
	                } else {
	                    y = 0;
	                    alpha = 255;
	                }
	                adapter.configurePinnedHeader(mHeaderView, position, alpha);
	                if (mHeaderView.getTop() != y) {
	                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
	                }
	                mHeaderViewVisible = true;
                }
                break;
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }
    
    
    public SectionHeaderView(Context context) {
        super(context);
    }

    public SectionHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SectionHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setLoadingView(View listFooter) {
		this.listFooter = listFooter;
	}
    
    public View getLoadingView() {
		return listFooter;
	}
    
    @Override
    public void setAdapter(ListAdapter adapter) {
    	if (this.adapter != null) {
    		this.setOnScrollListener(null);
    	}
        this.adapter = (SectionedListAdapter) adapter;
        this.setOnScrollListener((SectionedListAdapter) adapter);
        View dummy = new View(getContext());
    	super.addFooterView(dummy);
    	super.setAdapter(adapter);
    	super.removeFooterView(dummy);
    }
    
    @Override
    public SectionedListAdapter getAdapter() {
    	return adapter;
    }

	
    public boolean isLoadingViewVisible() {
		return footerViewAttached;
	}
}