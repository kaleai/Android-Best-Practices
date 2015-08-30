package kale.util;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import kale.file.util.FileUtil;
import kale.toolbox.lib.R;

/**
 * @author Jack Tony
 * @date 2015/8/2
 */
public class TestUtil {

    /**
     * @return 测试用的较长的list
     */
    public static List<String> getLongStringList(Context context) {
        return Arrays.asList(getLongStringArr(context));
    }

    /**
     * @return 测试用的较长的string[]
     */
    public static String[] getLongStringArr(Context context) {
        return FileUtil.getStringArr(context, R.array.country_names);
    }

    /**
     * @return 测试用的较短的string[]
     */
    public static String[] getShortStringArr() {
        return new String[]{"android", "ios", "wp", "linux", "window"};
    }
}
