package com.huxley.wii.yl.basePage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.huxley.wii.yl.common.BaseException;
import com.huxley.wii.yl.common.YlUIHelper;
import com.huxley.wii.yl.common.utils.doubleClick.OnYlKeyListener;

import butterknife.ButterKnife;

/**
 * activity 基类
 * Created by huxley on 17/1/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog         mProgressDialog;
    public Handler                mHandler;
    public Runnable               openRunnable;
    public Runnable               closeRunnable;
    public YlUIHelper.BaseDialogs mBaseDialogs;

    public void setRefreshing(boolean refreshing, final SwipeRefreshLayout srl) {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (refreshing) {
            if (openRunnable == null) {
                openRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (srl != null) {
                            srl.setRefreshing(true);
                        }
                    }
                };
            }
            mHandler.post(openRunnable);
        } else {
            if (closeRunnable == null) {
                closeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (srl != null) {
                            srl.setRefreshing(false);
                        }
                    }
                };
            }
            mHandler.postDelayed(closeRunnable, 300);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (getLayoutId() != 0) {
                setContentView(getLayoutId());
                ButterKnife.bind(this);
                init(getIntent());
            }
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBaseDialogs != null) {
            mBaseDialogs = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public abstract int getLayoutId();

    public abstract void init(Intent intent);

    public YlUIHelper.BaseDialogs getBaseDialogs() {
        if (mBaseDialogs == null) {
            mBaseDialogs = new YlUIHelper.BaseDialogs();
        }
        return mBaseDialogs;
    }

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
                    mProgressDialog = new ProgressDialog(this);
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
