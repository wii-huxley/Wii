package com.huxley.wii.yl.common;

import android.app.Application;

import org.litepal.LitePal;


/**
 * App 基类
 * Created by huxley on 16/10/31.
 */
public class YlApp extends Application {

    private static YlApp sYlApp;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
        sYlApp = this;
    }

    private void init() {
        LitePal.initialize(this);
    }

    public static YlApp getInstance() {
        return sYlApp;
    }
}
