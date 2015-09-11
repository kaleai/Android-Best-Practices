package kale.toolbox.base;

import android.support.annotation.LayoutRes;

/**
 * @author Jack Tony
 * @date 2015/9/8
 */
public interface BaseUI {

    @LayoutRes 
    int getLayoutResId();

    void findViews();

    void setViews();
}
