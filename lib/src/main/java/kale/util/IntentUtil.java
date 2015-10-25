package kale.util;

import android.app.Activity;
import android.content.Intent;

/**
 * @author Jack Tony
 * @date 2015/4/24
 */
public class IntentUtil {

    public static final String KEY_FIRST = "KEY_FIRST";

    public static final String KEY_SECOND = "KEY_SECOND";

    public static final String KEY_THIRD = "KEY_THIRD";

    public static final String KEY_FOURTH = "KEY_FOURTH";

    public static final String KEY_FIFTH = "KEY_FIFTH";

    public static final String KEY_SIXTH = "KEY_SIXTH";
    

    /**
     * 判断intent和它的bundle是否为空
     */
    public static boolean isEmpty(Intent intent) {
        return !(intent == null || intent.getExtras() == null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromIntent(Activity activity, String key) {
        Intent intent;
        if ((intent = activity.getIntent()) != null) {
            return (T) intent.getExtras().get(key);
        } else {
            throw new NullPointerException("Intent is null");
        }
    }
}
