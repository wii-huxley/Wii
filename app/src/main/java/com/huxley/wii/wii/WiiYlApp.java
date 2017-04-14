package com.huxley.wii.wii;

import com.huxley.wii.yl.common.YlApp;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by huxley on 16/12/4.
 */
public class WiiYlApp extends YlApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                .stackViewMode(Fragmentation.SHAKE)
                .debug(BuildConfig.DEBUG)
                .handleException(e -> {
                })
                .install();
    }
}
