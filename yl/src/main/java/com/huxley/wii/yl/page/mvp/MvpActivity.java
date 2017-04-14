package com.huxley.wii.yl.page.mvp;

import android.os.Bundle;

import com.huxley.wii.yl.common.utils.reflect.ClassTypeReflect;
import com.huxley.wii.yl.page.BaseActivity;

/**
 * Created by huxley
 * Data: 2016/8/4.
 */
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {

    public P mPresenter;

    @Override
    public void init(Bundle savedInstanceState) {
        mPresenter = (P) Mvp.getInstance().getPresenter(ClassTypeReflect.getGenericClass(this, 0));
        mPresenter.initPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
