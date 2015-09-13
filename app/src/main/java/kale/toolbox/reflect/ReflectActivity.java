package kale.toolbox.reflect;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import kale.debug.log.L;
import kale.reflect.Reflect;
import kale.toolbox.R;
import kale.toolbox.base.BaseActivity;
import kale.toolbox.databinding.EmptyActivityBinding;

public class ReflectActivity extends BaseActivity<EmptyActivityBinding> {


    @Override
    public int getLayoutResId() {
        return R.layout.empty_activity;
    }

    @Override
    public void setViews() {
        createClass();
        callMethod();
        setgetParam();
    }

    TextView mTv;

    private void createClass() {
        // 有参数，建立类
        mTv = Reflect.on(TextView.class).create(this).get();

        mTv.setTextSize(30);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        b.rootLl.addView(mTv,params);

        // 通过类全名得到类
        String word = Reflect.on("java.lang.String").create("Reflect TextView").get();

        // 无参数，建立类
        Fragment fragment = Reflect.on(Fragment.class).create().get();
        if (fragment != null) {
            mTv.setText(word);
        }
    }

    private void callMethod() {
        // 调用无参数方法
        L.d("call getText() : " + Reflect.on(mTv).call("getText").toString());
        // 调用有参数方法
        Reflect.on(mTv).call("setTextColor", 0xffff0000);
    }

    private void setgetParam() {
        // 设置参数
        Reflect.on(mTv).set("mText", "---------- new Reflect TextView ----------");
        // 获得参数
        L.d("setgetParam is " + Reflect.on(mTv).get("mText"));
    }

}
