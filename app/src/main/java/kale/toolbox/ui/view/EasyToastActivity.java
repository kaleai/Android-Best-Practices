package kale.toolbox.ui.view;

import android.view.Gravity;

import kale.toolbox.R;
import kale.toolbox.base.BaseActivity;
import kale.toolbox.databinding.EasytoastActivityBinding;
import kale.ui.view.EasyToast;

import static kale.ui.view.EasyToast.showToast;
import static kale.ui.view.EasyToast.showToastInThread;
import static kale.ui.view.EasyToast.showToastLong;

/**
 * @author Jack Tony
 * @date 2015/8/19
 */
public class EasyToastActivity extends BaseActivity<EasytoastActivityBinding>{

    @Override
    public int getLayoutResId() {
        return R.layout.easytoast_activity;
    }

    @Override
    public void setViews() {
        b.showShortBtn.setOnClickListener(view -> showToast(this,R.string.hello_world));
        b.showLongBtn.setOnClickListener(view -> showToastLong(this,R.string.hello_world));
        b.showCustomBtn.setOnClickListener(view -> {
            new EasyToast(this).setText("Hello World").setGravity(Gravity.CENTER, 0, 0).show(3000);
            //EasyToast.getToast().setView();
        });
        b.showInThread.setOnClickListener(view-> new Thread(() -> showToastInThread(this,"in thread")).start());
    }
}
