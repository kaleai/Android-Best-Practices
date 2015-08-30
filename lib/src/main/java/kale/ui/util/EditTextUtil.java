package kale.ui.util;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import kale.code.CharacterUtil;

/**
 * 参考：http://www.jianshu.com/p/1f05bb1fde3e
 *
 * @edit Jack Tony
 * @date 2015/7/31
 */
public class EditTextUtil {

    /**
     * 限制内容长度
     */
    public static void lengthFilter(final EditText editText, final int length) {
        InputFilter[] filters = new InputFilter[1];

        filters[0] = new InputFilter.LengthFilter(length) {
            public CharSequence filter(@NonNull CharSequence source, int start, int end, @NonNull Spanned dest, int dstart, int dend) {
                if (dest.toString().length() >= length) {
                    return "";
                }
                return source;
            }
        };
        // Sets the list of input filters that will be used if the buffer is Editable. Has no effect otherwise.
        editText.setFilters(filters);
    }

    /**
     * 限制中文字符的长度
     */
    public static void lengthChineseFilter(final EditText editText, final int length) {
        InputFilter[] filters = new InputFilter[1];

        filters[0] = new InputFilter.LengthFilter(length) {
            public CharSequence filter(@NonNull CharSequence source, int start, int end, @NonNull Spanned dest, int dstart, int dend) {
                // 可以检查中文字符
                boolean isChinese = CharacterUtil.checkNameChese(source.toString());
                if (!isChinese || dest.toString().length() >= length) {
                    return "";
                }
                return source;
            }
        };
        // Sets the list of input filters that will be used if the buffer is Editable. Has no effect otherwise.
        editText.setFilters(filters);
    }


    /**
     * 设置密码是否可见
     */
    public static void passwordVisibleToggle(EditText editText) {
        Log.d("input", "passwordVisibleToggle " + editText.getInputType());
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        } else {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    /**
     * 屏蔽复制、粘贴功能
     */
    public static void pasteUnable(EditText editText) {
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        editText.setLongClickable(false);
    }

    public static class TextWatcherAdapter implements TextWatcher {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
