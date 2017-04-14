package com.huxley.wii.yl.basePage.mvp;

import com.huxley.wii.yl.basePage.BaseFragment;
import com.huxley.wii.yl.common.utils.reflect.ClassTypeReflect;


/**
 * Created by huxley
 * Data: 2016/8/4.
 */
public abstract class MvpFragment <P extends MvpPresenter> extends BaseFragment implements MvpView{

    public P mPresenter;

    @Override
    protected void init() {
        mPresenter = (P) Mvp.getInstance().getPresenter(ClassTypeReflect.getGenericClass(this));
        mPresenter.initPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Mvp.getInstance().unRegister(this.getClass());
        mPresenter.destroy();
    }
}
