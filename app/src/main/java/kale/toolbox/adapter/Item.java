package kale.toolbox.adapter;

import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Jack Tony
 * @date 2015/9/1
 */
public class Item {

    private Button btn;
    private int mPosition;
    private String data;
    
    View.OnClickListener btnListener = v -> Toast.makeText(v.getContext(), "pos = " + mPosition + " data = " + data, Toast.LENGTH_SHORT).show();


    private void doSomeThing(int position) {
        
    }
    
    public void findViews(View root) {
        btn = (Button) root.findViewById(android.R.id.button1);
        btn.setOnClickListener(btnListener);
    }

    public void setViews(String itemData, int position) {
        data = itemData;
        mPosition = position;
    }

    public int getRootItemPosition(View v,AbsListView absListView){
        ViewParent parent;

        while(! ((parent=v.getParent()) instanceof AbsListView) ){
            v=(View) parent;
        }

        int f=absListView.getFirstVisiblePosition();
        int l=absListView.getLastVisiblePosition();
        for (int i = f; i <= l; i++) {

            if (absListView.getChildAt(i-f)==v) {
                return i;
            }
        }
        return -1;
    }

}
