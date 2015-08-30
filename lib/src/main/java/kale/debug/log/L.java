package kale.debug.log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

import android.util.Pair;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @edit Jack Tony
 * @edit haoGe
 * @date 2015/8/25
 */
public final class L {

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

    public static void e(String message, Object... args) {
        Logger.t(getClassName()).e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.t(getClassName()).e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.t(getClassName()).i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.t(getClassName()).v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.t(getClassName()).w(message, args);
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

    /**
     * support list、map、array
     * 
     * @see "https://github.com/pengwei1024/LogUtils"
     */
    public static void Object(Object object) {
        if (object != null) {
            final String simpleName = object.getClass().getSimpleName();
            if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtil.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtil.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtil.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                Logger.t(getClassName()).d(msg + "}");
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %d [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator<Object> iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%d]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, SystemUtil.objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                Logger.t(getClassName()).d(msg + "\n]");
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map<Object, Object> map = (Map<Object, Object>) object;
                Set<Object> keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, SystemUtil.objectToString(key),
                            SystemUtil.objectToString(value));
                }
                Logger.t(getClassName()).d(msg + "}");
            } else {
                Logger.t(getClassName()).d(SystemUtil.objectToString(object));
            }
        } else {
            Logger.t(getClassName()).d(SystemUtil.objectToString(object));
        }
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
