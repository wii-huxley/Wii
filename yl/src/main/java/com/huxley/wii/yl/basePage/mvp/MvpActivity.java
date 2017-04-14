package com.huxley.wii.yl.basePage.mvp;

import android.content.Intent;

import com.huxley.wii.yl.basePage.BaseActivity;
import com.huxley.wii.yl.common.utils.reflect.ClassTypeReflect;


/**
 * Created by huxley
 * Data: 2016/8/4.
 */
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {

    public P mPresenter;

    @Override
    public void init(Intent intent) {
        mPresenter = (P) Mvp.getInstance().getPresenter(ClassTypeReflect.getGenericClass(this, 0));
        mPresenter.initPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
