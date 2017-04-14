package com.huxley.wii.yl.page;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.yl.R;
import com.huxley.wii.yl.common.utils.doubleClick.OnYlKeyListener;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by huxley on 2017/3/28.
 */
public abstract class BaseFragment extends SupportFragment {

    protected View rootView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            rootView.setBackgroundResource(R.color.color_f3);
        }
        init();
        return rootView;
    }

    public View getRootView() {
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void init();


    /**
     * 显示加载提示
     *
     * @param message   提示信息
     * @param shieldKey 是否保护该提示
     */
    public void show(String message, boolean shieldKey) {
        if (mProgressDialog == null) {
            synchronized (BaseActivity.class) {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(_mActivity);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setMessage(message);
                    if (shieldKey) {
                        mProgressDialog.setOnKeyListener(new OnYlKeyListener() {
                            @Override
                            public boolean onYlKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                                return keyCode == KeyEvent.KEYCODE_BACK;
                            }
                        });
                    }
                }
            }
        } else {
            mProgressDialog.setMessage(message);
            if (shieldKey) {
                mProgressDialog.setOnKeyListener(new OnYlKeyListener() {
                    @Override
                    public boolean onYlKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                        return keyCode == KeyEvent.KEYCODE_BACK;
                    }
                });
            }
            if (mProgressDialog.isShowing()) {
                return;
            }
        }
        mProgressDialog.show();
    }

    /**
     * 销毁加载提示
     */
    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
