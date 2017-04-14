package com.huxley.wii.yl.page;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huxley.wii.yl.common.BaseException;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * activity 基类
 * Created by huxley on 17/1/15.
 */
public abstract class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (getLayoutId() != 0) {
                setContentView(getLayoutId());
                ButterKnife.bind(this);
                init(savedInstanceState);
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

    public abstract void init(Bundle savedInstanceState);
}
