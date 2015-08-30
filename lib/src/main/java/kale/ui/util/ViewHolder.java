package kale.ui.util;

import android.util.SparseArray;
import android.view.View;

/**
 * 比较规范独立的的ViewHolder.
 * @author Jack Tony
 * 
 * @see "http://www.cnblogs.com/tianzhijiexian/p/4157889.html"
 * 
 * @date 2015/4/28
 */
public class ViewHolder {

    // I added a generic return type to reduce the casting noise in client code  
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
