package com.huxley.wii.yl.page.mvp;

import com.huxley.wii.yl.common.utils.reflect.ClassTypeReflect;
import com.huxley.wii.yl.page.swipeBack.SwipeBackFragment;


/**
 * Created by huxley
 * Data: 2016/8/4.
 */
public abstract class MvpSwipeBackFragment<P extends MvpPresenter> extends SwipeBackFragment implements MvpView {

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
