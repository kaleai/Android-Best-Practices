package kale.toolbox;

import android.content.Intent;
import android.widget.ArrayAdapter;

import kale.debug.log.L;
import kale.toolbox.base.BaseActivity;
import kale.toolbox.databinding.ActivityMainBinding;
import kale.toolbox.debug.log.LoggerActivity;
import kale.toolbox.ui.view.EasyToastActivity;

import static kale.ui.util.ActivityUtil.startAct;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    String[] demoStr = {"Log"};
    
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViews() {
        L.d("This is MainActivity");
        b.mainLv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, demoStr));
        b.mainLv.setOnItemClickListener((parent, view, position, id) -> {
            switch ((int) id) {
                case 0:
                    startAct(LoggerActivity.class, new Intent().putExtra("key", "value--->"));
                    break;
                case 1:
                    startAct(EasyToastActivity.class);
                    break;
                default:
            }
        });
    }
   
}
