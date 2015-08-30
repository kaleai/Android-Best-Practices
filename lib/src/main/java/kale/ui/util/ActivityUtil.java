package kale.ui.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

/**
 * @author Jack Tony
 * @date 2015/8/1
 */
public class ActivityUtil {
    
    private static Activity mActivity;

    public static void attach(@NonNull Activity activity) {
        mActivity = activity;
    }

    public static void detach() {
        mActivity = null;
    }

    public static void startAct(@NonNull Class cls) {
        mActivity.startActivity(new Intent(mActivity, cls));
    }

    public static void startAct(@NonNull Class cls, @NonNull Intent intent) {
        intent.setClass(mActivity, cls);
        mActivity.startActivity(intent);
    }

    public static void startActForResult(@NonNull Class cls, @NonNull Intent intent,int requestCode) {
        intent.setClass(mActivity, cls);
        mActivity.startActivityForResult(intent, requestCode);
    }
    
    public void addFragment(@IdRes int containerViewId, Fragment fragment) {
        mActivity.getFragmentManager().beginTransaction().add(containerViewId, fragment).commitAllowingStateLoss();
    }
    
    

}
