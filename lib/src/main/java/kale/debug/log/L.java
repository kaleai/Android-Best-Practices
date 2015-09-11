package kale.debug.log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

/**
 * @edit Jack Tony
 * @edit haoGe
 * @date 2015/8/25
 */
public class L {

    private L() {
    }

    public static Settings init() {
        return Logger.init();
    }

    public static Settings init(String tag) {
        return Logger.init(tag);
    }

    public static Printer t(String tag) {
        return Logger.t(tag);
    }

    public static Printer t(int methodCount) {
        return Logger.t(methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return Logger.t(tag, methodCount);
    }

    public static void d(String message, Object... args) {
        Logger.t(getClassName()).d(message, args);
    }

    public static void dd(String message, Object... args) {
        Logger.t(getClassName(),0).d(message, args);
    }

    public static void e(String message, Object... args) {
        Logger.t(getClassName()).e(message, args);
    }

    public static void ee(String message, Object... args) {
        Logger.t(getClassName(),0).e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.t(getClassName()).e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.t(getClassName()).i(message, args);
    }

    public static void ii(String message, Object... args) {
        Logger.t(getClassName(),0).i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.t(getClassName()).v(message, args);
    }

    public static void vv(String message, Object... args) {
        Logger.t(getClassName(),0).v(message, args);
    }
    
    public static void w(String message, Object... args) {
        Logger.t(getClassName()).w(message, args);
    }
    
    public static void ww(String message, Object... args) {
        Logger.t(getClassName(),0).w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.t(getClassName()).wtf(message, args);
    }

    public static void json(String json) {
        Logger.t(getClassName()).json(json);
    }

    public static void xml(String xml) {
        Logger.t(getClassName()).xml(xml);
    }

    public static void object(Object object) {
        Logger.t(getClassName()).object(object);
    }
    
    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }
    
}
