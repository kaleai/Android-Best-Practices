package kale.ui.util;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.TextView;

/**
 * @author Jack Tony
 * @date 2015/8/10
 * @see "http://blog.csdn.net/wulianghuan/article/details/24421179"
 *
 * setCompoundDrawables的drawable必须先设置过bound信息，否则会无法显示
 * setCompoundDrawablesWithIntrinsicBounds的drawable不用设置bound，它会自动通过getIntrinsicWidth和getIntrinsicHeight获取
 */
public class TextViewUtil {

    public static void setDrawableTop(TextView textView, @DrawableRes int drawableResId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, drawableResId, 0, 0);
    }

    public static void setDrawableBottom(TextView textView, @DrawableRes int drawableResId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableResId);
    }

    public static void setDrawableLeft(TextView textView, @DrawableRes int drawableResId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
    }

    public static void setDrawableRight(TextView textView, @DrawableRes int drawableResId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableResId, 0);
    }

    public static void setDrawableTop(TextView textView, Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public static void setDrawableBottom(TextView textView, Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
    }

    public static void setDrawableLeft(TextView textView, Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public static void setDrawableRight(TextView textView, Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public static Drawable getDrawableTop(TextView textView) {
        return textView.getCompoundDrawables()[1];
    }

    public static Drawable getDrawableBottom(TextView textView) {
        return textView.getCompoundDrawables()[3];
    }

    public static Drawable getDrawableLeft(TextView textView) {
        return textView.getCompoundDrawables()[0];
    }

    public static Drawable getDrawableRight(TextView textView) {
        return textView.getCompoundDrawables()[2];
    }
    
    public static Rect getDrawableTopBound(TextView textView) {
        return textView.getCompoundDrawables()[1].getBounds();
    }

    public static Rect getDrawableBottomBound(TextView textView) {
        return textView.getCompoundDrawables()[3].getBounds();
    }

    public static Rect getDrawableLeftBound(TextView textView) {
        return textView.getCompoundDrawables()[0].getBounds();
    }

    public static Rect getDrawableRightBound(TextView textView) {
        return textView.getCompoundDrawables()[2].getBounds();
    }
}
