package kale.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author Jack Tony
 * @date 2015/8/1
 */
public class PhoneUtil {

    /**
     * @return 得到手机唯一标识IMEI的值
     */
    public static String getIMEINumber(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
