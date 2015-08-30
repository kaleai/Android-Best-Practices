package kale.toolbox.base;


import com.orhanobut.logger.LogLevel;

import android.app.Application;

import kale.debug.log.L;
import kale.toolbox.BuildConfig;

/**
 * @author Jack Tony
 * @date 2015/8/25
 */
public class BaseApplication extends Application {

    // 定义是否是强制显示log的模式
    protected static final boolean LOG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        L.init()               // default PRETTYLOGGER or use just init()
                //.setMethodCount(2);            // default 2
                //.hideThreadInfo()             // default shown
                .setMethodOffset(1);           // default 0
        // 在debug下，才显示log
        L.init().setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE); // default LogLevel.FULL
        // 如果是强制显示log，那么无论在什么模式下都显示log
        if (BaseApplication.LOG) {
            L.init().setLogLevel(LogLevel.FULL);
        }
    }
}
