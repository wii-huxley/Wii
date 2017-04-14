package com.acce.page_main;

import com.huxley.wii.yl.page.mvp.MvpPresenter;
import com.huxley.wii.yl.page.mvp.MvpView;

/**
 * Created by huxley on 2017/4/10.
 */

public class HomeContract {

    public interface View extends MvpView {

    }

    public static class Present extends MvpPresenter<View> {

    }
}
