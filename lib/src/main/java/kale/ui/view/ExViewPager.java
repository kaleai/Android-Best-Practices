package kale.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kale.toolbox.lib.R;

/**
 * @author http://blog.csdn.net/qiantujava/article/details/47168315
 * @date 2015/9/6
 */
public class ExViewPager extends ViewPager {

    private boolean isScrollable;

    public ExViewPager(Context context) {
        this(context, null);
    }

    public ExViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExViewPager);
        isScrollable = a.getBoolean(R.styleable.ExViewPager_scrollable, true);
        a.recycle();
        
        if (isInEditMode()) {
            preview(context, attrs);
        }
    }

    private void preview(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExViewPager);
        List<View> viewList = new ArrayList<>();
        
        int layoutResId;
        if ((layoutResId = a.getResourceId(R.styleable.ExViewPager_tools_layout0, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        if ((layoutResId = a.getResourceId(R.styleable.ExViewPager_tools_layout1, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        if ((layoutResId = a.getResourceId(R.styleable.ExViewPager_tools_layout2, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        if ((layoutResId = a.getResourceId(R.styleable.ExViewPager_tools_layout3, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        if ((layoutResId = a.getResourceId(R.styleable.ExViewPager_tools_layout4, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        a.recycle();
        
        setAdapter(new PreviewPagerAdapter(viewList));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollable && super.onInterceptTouchEvent(ev);

    }

    public void Scrollable(boolean scrollable) {
        this.isScrollable = scrollable;
    }

    /**
     * @author Jack Tony 
     * 这里传入一个list数组，从每个list中可以剥离一个view并显示出来
     * @date :2014-9-24
     */
    public static class PreviewPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public PreviewPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mViewList.get(position) != null) {
                container.removeView(mViewList.get(position));
            }
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

    }
}
