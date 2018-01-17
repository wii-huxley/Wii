package com.huxley.page_login.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxley.wiitools.utils.SPUtils;
import com.huxley.yl.common.YlApp;


/**
 * Created by huxley on 17/1/20.
 */
public class UserSP extends SPUtils {

    private static final String NAME_USER = "sp_user";
    public static final  String KEY_USER  = "key_user";

    public static class User {
        private static SharedPreferences sp;

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (User.class) {
                    if (sp == null) {
                        sp = YlApp.getInstance().getSharedPreferences(NAME_USER, Context.MODE_PRIVATE);
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