package com.huxley.wii.wii.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxley.wii.yl.common.YlApp;
import com.huxley.wii.yl.common.utils.SPUtils;

/**
 * Created by huxley on 17/1/20.
 */
public class SP extends SPUtils {

    private static final String NAME_CONFIG = "sp_config";
    private static final String NAME_SMS_BOMB = "sp_sms_bomb";

    public static class Config {
        private static SharedPreferences sp;

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = YlApp.getInstance().getSharedPreferences(NAME_CONFIG, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }

        public static void save(String key, Object object) {
            input(get(), key, object);
        }

        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }

    public static class SMSBomb {
        private static SharedPreferences sp;

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = YlApp.getInstance().getSharedPreferences(NAME_SMS_BOMB, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }

        public static void save(String key, Object object) {
            input(get(), key, object);
        }

        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }
}