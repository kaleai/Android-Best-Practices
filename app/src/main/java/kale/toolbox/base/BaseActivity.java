package kale.toolbox.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import kale.ui.util.KeyBoardUtil;

/**
 * @author Jack Tony
 * @date 2015/8/19
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements BaseUI{

    protected String TAG = getClass().getSimpleName();
    
    protected B b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViews();
        setViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     *
     * @return 当前activity布局的LayoutId
     */
    @Override
    public abstract
    @LayoutRes
    int getLayoutResId();

    /**
     * 找到所有的view
     */
    @Override
    public void findViews() {
        if (getLayoutResId() != 0) {
            b = DataBindingUtil.setContentView(this, getLayoutResId());
        }
    }
    
    /**
     * 设置所有的view
     */
    @Override
    public abstract void setViews();

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
