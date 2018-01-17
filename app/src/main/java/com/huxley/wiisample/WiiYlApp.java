package com.huxley.wiisample;

import cn.finalteam.rxgalleryfinal.utils.Logger;
import com.acce.page_main.HomeActivity;
import com.huxley.fragmentation.Fragmentation;
import com.huxley.wiitools.WiiTools;
import com.huxley.yl.common.YlApp;

/**
 * Created by huxley on 16/12/4.
 */
public class WiiYlApp extends YlApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(e -> {
                })
                .install();

        WiiTools.getInstance()
            .initStetho()
            .initBmob("3c2a47696d1d3e4709dba1cefd8a5545");

        Logger.DEBUG = true;
    }

    public Class<?> getHomeClass() {
        return HomeActivity.class;
    }
}
