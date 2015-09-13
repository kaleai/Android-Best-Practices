package kale.toolbox;

import android.content.Intent;
import android.widget.ArrayAdapter;

import kale.toolbox.base.BaseActivity;
import kale.toolbox.databinding.ActivityMainBinding;
import kale.toolbox.debug.log.LoggerActivity;
import kale.toolbox.reflect.ReflectActivity;

import static kale.ui.util.ActivityUtil.startAct;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    String[] demoStr = {"Android Log","Java反射"};
    
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void setViews() {
        // fast jump
        //b.mainLv.post(() -> startAct(getActivity(), ReflectActivity.class));

        b.mainLv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, demoStr));
        b.mainLv.setOnItemClickListener((parent, view, position, id) -> {
            switch ((int) id) {
                case 0:
                    startAct(getActivity(), LoggerActivity.class, new Intent().putExtra("key", "value--->"));
                    break;
                case 1:
                    startAct(getActivity(), ReflectActivity.class);
                    break;
                default:
            }
        });
    }
   
}
