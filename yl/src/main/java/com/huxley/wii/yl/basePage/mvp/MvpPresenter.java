package com.huxley.wii.yl.basePage.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * Created by huxley
 * Data: 2016/8/4.
 */
public class MvpPresenter<V extends MvpView> {

    public Reference<V> mViewRef;

    void initPresenter(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public V getView() {
        return mViewRef.get();
    }

    void destroy() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
