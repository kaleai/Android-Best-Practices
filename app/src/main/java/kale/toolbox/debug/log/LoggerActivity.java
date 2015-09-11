package kale.toolbox.debug.log;

import android.support.annotation.Keep;
import android.util.Log;

import kale.debug.log.L;
import kale.toolbox.base.BaseActivity;
import kale.util.TestUtil;

import static kale.util.IntentUtil.fromIntent;

/**
 * @author Jack Tony
 * @date 2015/8/25
 */
public class LoggerActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return 0; // 0表示采用默认的布局
    }

    @Override
    public void setViews() {
        // string
        String str = fromIntent(this,"key");
        L.d(str != null ? str : "hello world");
        
        // simple log
        L.ii("a");
        L.dd("b");
        L.ee("c");
        L.vv("d");
        L.ww("e");
        
        // json
        L.json("[{\"CityId\":18,\"CityName\":\"西安\",\"ProvinceId\":27,\"CityOrder\":1},{\"CityId\":53,\"CityName\":\"广州\",\"ProvinceId\":27,\"CityOrder\":1}]'");

        // object
        L.object(new User("jack", "f"));

        // list
        L.object(TestUtil.getLongStringList(this));

        // array
        L.object(TestUtil.getShortStringArr());

        // arrays
        double[][] doubles = {
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}
        };
        L.object(doubles);

        // sub class
        new User("name", "sex").log();

        Log.d(TAG, "类名 = " + getClassName());
        Log.d(TAG, "当前方法名+行数 = " + callMethodAndLine());
    }

    @Keep
    public class User {

        private String name;

        private String sex;

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public void log() {
            show();
        }

        private void show() {
            L.d("user");
        }
    }

    /**
     * @return 当前的类名（全名）
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result = thisMethodStack.getClassName();
        return result;
    }


    /**
     * log这个方法就可以显示超链
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result += thisMethodStack.getClassName()+ ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

}
