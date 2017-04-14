package com.huxley.wii.wii.cg.editText;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.UIHelper;
import com.huxley.wii.wii.common.WiiConstant;
import com.huxley.wii.yl.basePage.BaseActivity;
import com.huxley.wii.yl.common.utils.ToastHelper;
import com.huxley.wii.yl.common.utils.doubleClick.OnTbMenuItemClickListener;

import java.text.MessageFormat;

import butterknife.BindView;

import static com.huxley.wii.wii.R.id.et;

/**
 * 编辑文本界面
 * Created by huxley on 17/2/4.
 */
public class EditTextActivity extends BaseActivity {

    String content;
    String title;
    int    length;

    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(et)
    EditText mEt;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    public int getLayoutId() {
        return R.layout.edit_text_activity;
    }

    @Override
    public void init(Intent intent) {
        content = intent.getStringExtra(WiiConstant.Key.CONTENT);
        title = intent.getStringExtra(WiiConstant.Key.TITLE);
        length = intent.getIntExtra(WiiConstant.Key.LENGTH, 0);
        UIHelper.Toolbars.setTitleAndBackAndMenu(mToolbar, this, MessageFormat.format("更改{0}", title), R.menu.menu_edit_text, new OnTbMenuItemClickListener() {
            @Override
            public boolean onTbMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        save();
                        break;
                }
                return true;
            }
        });
        mTv.setText(MessageFormat.format("请输入{0}", title));
        if (content != null) {
            mEt.setText(content);
            mEt.setSelection(content.length());
        }
        if (length > 0) {
            mEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        }
    }

    private void save() {
        String content = mEt.getText().toString();
        if (content.isEmpty()) {
            ToastHelper.showInfo(MessageFormat.format("请输入{0}", this.title));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(WiiConstant.Key.CONTENT, content);
        setResult(RESULT_OK, intent);
        finish();
    }
}
