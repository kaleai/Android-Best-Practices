package kale.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.view.View;
import android.widget.Toast;

/**
 * 让toast变得更简易的封装类，可以提供线程中使用的toast。还可以通过showByDuration()来设置toast的显示时间.
 *
 * @author Jack Tony
 * @date 2015/4/29
 */
public class EasyToast {

    private Toast mToast = null;

    private Handler mHandler = null;

    private int duration = 0;

    private int currDuration = 0;

    private final int DEFAULT = 2000;

    public EasyToast(Context context) {
        currDuration = DEFAULT;
        mHandler = new Handler(context.getMainLooper());
        mToast = Toast.makeText(context, null, Toast.LENGTH_LONG);
    }

    private Runnable mToastThread = new Runnable() {

        public void run() {
            mToast.show();
            mHandler.postDelayed(mToastThread, DEFAULT);// 每隔2秒显示一次
            if (duration != 0) {
                if (currDuration <= duration) {
                    currDuration += DEFAULT;
                } else {
                    cancel();
                }
            }
        }
    };

    /**
     * 返回内部的toast对象。可以进行多样化的设置
     */
    @CheckResult
    public Toast getToast() {
        return mToast;
    }

    /**
     * 设置toast的文字
     */
    @CheckResult
    public EasyToast setText(String text) {
        mToast.setText(text);
        return this;
    }

    /**
     * 设置toast显示的位置
     *
     * @param gravity 位置，可以是Gravity.CENTER等
     * @param xOffset x轴的偏移量
     * @param yOffset y轴的偏移量
     */
    @CheckResult
    public EasyToast setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    /**
     * 显示toast
     *
     * @param duration toast显示的时间（单位：ms）
     */
    public void show(int duration) {
        this.duration = duration;
        mHandler.post(mToastThread);
    }

    /**
     * 设置toast的view
     */
    public void setView(View view) {
        mToast.setView(view);
    }

    /**
     * 让toast消失的方法
     */
    public void cancel() {
        mHandler.removeCallbacks(mToastThread);// 先把显示线程删除
        mToast.cancel();// 把最后一个线程的显示效果cancel掉，就一了百了了
        currDuration = DEFAULT;
    }

    //// ---------------------  封装的静态方法  --------------------- ////


    /**
     * 短暂显示toast
     */
    @UiThread
    public static void showToast(Context context, @StringRes int msg) {
        show(context, context.getString(msg), Toast.LENGTH_SHORT);
    }

    /**
     * 短暂显示toast
     */
    @UiThread
    public static void showToast(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的toast
     */
    @UiThread
    public static void showToastLong(Context context, @StringRes int msg) {
        show(context, context.getString(msg), Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的toast
     */
    @UiThread
    public static void showToastLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * @param length @param length toast的显示的时间长度：{Toast.LENGTH_SHORT, Toast.LENGTH_LONG}
     */
    private static void show(Context context, String msg, int length) {
        Toast.makeText(context, msg, length).show();
    }

    //////////////////////////// In other thread ////////////////////////////

    /**
     * 当你在线程中显示短暂toast时，请使用这个方法
     */
    @WorkerThread
    public static void showToastInThread(Activity activity, @StringRes int msg) {
        showInThread(activity, activity.getString(msg), Toast.LENGTH_SHORT);
    }

    /**
     * 当你在线程中显示短暂toast时，请使用这个方法
     */
    @WorkerThread
    public static void showToastInThread(Activity activity, @NonNull String msg) {
        showInThread(activity, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 当你在线程中显示长期toast时，请使用这个方法
     */
    @WorkerThread
    public static void showToastLongInThread(Activity activity, @StringRes int msg) {
        showInThread(activity, activity.getString(msg), Toast.LENGTH_LONG);
    }

    /**
     * 当你在线程中显示长期toast时，请使用这个方法
     */
    @WorkerThread
    public static void showToastLongInThread(Activity activity, @NonNull String msg) {
        showInThread(activity, msg, Toast.LENGTH_LONG);
    }

    /**
     * 当你在线程中使用toast时，请使用这个方法(可以控制显示多长时间)
     */
    @WorkerThread
    private static void showInThread(final Activity activity, final String msg, final int length) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(activity, msg, length);
            }
        });
    }
}
