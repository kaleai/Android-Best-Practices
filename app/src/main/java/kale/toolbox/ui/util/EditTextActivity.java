package kale.toolbox.ui.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import kale.toolbox.R;
import kale.ui.util.EditTextUtil;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext_test_activity);

        final EditText pswEt = (EditText) findViewById(R.id.psw_et);
        
        // 显示或隐藏密码内容
        Button showPswBtn = (Button) findViewById(R.id.show_psw_btn);
        showPswBtn.setOnClickListener(v -> EditTextUtil.passwordVisibleToggle(pswEt));

        // 限制输入的字符个数
        EditTextUtil.lengthFilter(pswEt, 8);

        // 限制输入的中文字符个数
        final EditText chineseEt = (EditText) findViewById(R.id.chinese_et);
        EditTextUtil.lengthChineseFilter(chineseEt, 8);

        final EditText pasteEt = (EditText) findViewById(R.id.paste_et);
        EditTextUtil.pasteUnable(pasteEt);
    }

}
