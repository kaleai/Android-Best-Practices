package kale.toolbox.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import kale.ui.util.ActivityUtil;
import kale.ui.util.KeyBoardUtil;
import kale.ui.view.EasyToast;
import kale.util.IntentUtil;

/**
 * @author Jack Tony
 * @date 2015/8/19
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    
    protected B b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        attachUtil(); // 初始化工具类
        super.onCreate(savedInstanceState);

        findViews();
        setViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachUtil(); // 初始化工具类
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachUtils(); // 移除工具类
    }

    /**
     *
     * @return 当前activity布局的LayoutId
     */
    protected abstract
    @LayoutRes
    int getLayoutResId();

    /**
     * 找到所有的view
     */
    protected void findViews() {
        if (getLayoutResId() != 0) {
            b = DataBindingUtil.setContentView(this, getLayoutResId());
        }
    }

    /**
     * 设置所有的view
     */
    protected abstract void setViews();

    private void attachUtil() {
        ActivityUtil.attach(this); // Activity
        EasyToast.attach(this); // Toast
        IntentUtil.attach(this); // Intent
    }

    private void detachUtils() {
        ActivityUtil.detach(); // Activity
        EasyToast.detach(); // Toast
        IntentUtil.detach(); // Intent
    }

    /**
     * @return 当前的activity
     */
    protected Activity getActivity() {
        return this;
    }

    // KeyBoard -------------------------------->

    @Override
    public void finish() {
        super.finish();
        KeyBoardUtil.hide(getWindow().getDecorView());
    }
}
