package kale.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kale.toolbox.lib.R;


/**
 * @author Jack Tony
 * @date 2015/9/6
 */
public class ExTabLayout extends TabLayout {

    private int mTabViewLayoutResId;

    public ExTabLayout(Context context) {
        this(context, null);
    }

    public ExTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExTabLayout);
        // custom
        mTabViewLayoutResId = a.getResourceId(R.styleable.ExTabLayout_tabViewLayout, 0);
        // preview
        if (isInEditMode()) {
            preview(context, a);
        }
        a.recycle();
    }

    private void preview(Context context, TypedArray a) {
        final String tabStrArr = a.getString(R.styleable.ExTabLayout_tools_tabStrArray);

        final String[] tabRealStrArr = getTabRealStrArr(tabStrArr);
        ViewPager viewPager = new ViewPager(context);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return tabRealStrArr.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabRealStrArr[position];
            }
        });
        viewPager.setCurrentItem(0);
        this.setupWithViewPager(viewPager);
    }

    private String[] getTabRealStrArr(String tabStrArr) {
        if (tabStrArr == null) {
            return new String[]{""};
        } else {
            return tabStrArr.split(",");
        }
    }

    @Override
    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        if (mTabViewLayoutResId != 0) {
            for (int i = 0; i < getTabCount(); i++) {
                TabLayout.Tab tab = getTabAt(i);
                if (tab != null) {
                    tab.setCustomView(View.inflate(getContext(), mTabViewLayoutResId, null));
                }
            }
            // 这里根据选中的位置得到自定义的tab，所以不会出现异常
            getTabAt(getSelectedTabPosition()).getCustomView().setSelected(true);
        }
    }

    public List<Tab> getTabList() {
        List<Tab> tabList = new ArrayList<>();
        for (int i = 0; i < getTabCount(); i++) {
            tabList.add(getTabAt(i));
        }
        return tabList;
    }

}
