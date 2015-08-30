package kale.net;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import kale.reflect.Reflect;

/**
 * 与网络相关的工具类
 *
 * @author Jack Tony
 * @date 2015/8/1
 */
public class NetUtil {

    private NetUtil() {
                /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @return 是否已经联网
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo ni : info) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 操作数据流量(GPRS网络开关)
     * 反射ConnectivityManager中hide的方法setMobileDataEnabled 可以开启和关闭GPRS网络
     *
     * @param open 是否打开GPRS
     */
    public static void setGprsStatus(Context context, boolean open) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = mConnectivityManager.getClass();
        Class<?>[] argClasses = new Class[1];
        argClasses[0] = boolean.class;

        Reflect.on(mConnectivityManager.getClass()).call("setMobileDataEnabled", open);
       /* // ����ConnectivityManager��hide�ķ���setMobileDataEnabled�����Կ����͹ر�GPRS����  
        Method method;
        try {
            method = cmClass.getMethod("setMobileDataEnabled", argClasses);
            method.invoke(mConnectivityManager, open);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetSettingActivity(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
