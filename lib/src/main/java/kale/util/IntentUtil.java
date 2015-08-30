package kale.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author Jack Tony
 * @date 2015/4/24
 */
public class IntentUtil {

    private static Activity mActivity;

    public static void attach(@NonNull Activity activity) {
        mActivity = activity;
    }

    public static void detach() {
        mActivity = null;
    }
    
    /**
     * 判断intent和它的bundle是否为空
     */
    public static boolean isEmpty(Intent intent) {
        return !(intent == null || intent.getExtras() == null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromIntent(String key) {
        Intent intent;
        if ((intent = mActivity.getIntent()) != null) {
            return (T) intent.getExtras().get(key);
        } else {
            throw new NullPointerException("Intent's key is null");
        } 
    }
}
