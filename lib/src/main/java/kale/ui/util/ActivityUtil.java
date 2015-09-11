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
    
    public static void startAct(Activity activity, @NonNull Class cls) {
        activity.startActivity(new Intent(activity, cls));
    }

    public static void startAct(Activity activity, @NonNull Class cls, @NonNull Intent intent) {
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static void startActForResult(Activity activity, @NonNull Class cls, @NonNull Intent intent,int requestCode) {
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }
    
    public void addFragment(Activity activity, @IdRes int containerViewId, Fragment fragment) {
        activity.getFragmentManager().beginTransaction().add(containerViewId, fragment).commitAllowingStateLoss();
    }

}
