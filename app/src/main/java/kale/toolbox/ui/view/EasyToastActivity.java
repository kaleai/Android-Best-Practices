package kale.toolbox.ui.view;

import android.view.Gravity;

import kale.toolbox.R;
import kale.toolbox.base.BaseActivity;
import kale.toolbox.databinding.EasytoastActivityBinding;
import kale.ui.view.EasyToast;

import static kale.ui.view.EasyToast.*;

/**
 * @author Jack Tony
 * @date 2015/8/19
 */
public class EasyToastActivity extends BaseActivity<EasytoastActivityBinding>{

    @Override
    protected int getLayoutResId() {
        return R.layout.easytoast_activity;
    }

    @Override
    protected void setViews() {
        b.showShortBtn.setOnClickListener(view -> showToast(R.string.hello_world));
        b.showLongBtn.setOnClickListener(view -> showToastLong(R.string.hello_world));
        b.showCustomBtn.setOnClickListener(view -> {
            new EasyToast(this).setText("Hello World").setGravity(Gravity.CENTER, 0, 0).show(3000);
            //EasyToast.getToast().setView();
        });
        b.showInThread.setOnClickListener(view-> new Thread(() -> showToastInThread("in thread")).start());
    }
}
