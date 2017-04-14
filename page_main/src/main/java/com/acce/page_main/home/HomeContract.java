package com.acce.page_main.home;

import com.huxley.wii.yl.page.mvp.MvpPresenter;
import com.huxley.wii.yl.page.mvp.MvpView;

/**
 * Created by huxley on 2017/4/8.
 */

public class HomeContract {

    public interface View extends MvpView {

    }

    public static class Present extends MvpPresenter<View> {

    }
}
