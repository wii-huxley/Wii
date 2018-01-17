package com.huxley.wiisample.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxley.wiisample.model.netBean.GitHubAuthorizationBean;
import com.huxley.wiisample.model.netBean.GitHubUserBean;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.StringUtils;
import com.huxley.yl.common.YlApp;
import com.huxley.wiitools.utils.SPUtils;


/**
 * Created by huxley on 17/1/20.
 */
public class SP extends SPUtils {

    private static final String NAME_CONFIG = "sp_config";
    private static final String NAME_SMS_BOMB = "sp_sms_bomb";
    private static final String NAME_GITHUB = "sp_github";

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


    public static class GitHub {
        private static SharedPreferences sp;
        private static String KEY_TOKEN = "token";
        private static String KEY_USER = "user";

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = YlApp.getInstance().getSharedPreferences(NAME_GITHUB, Context.MODE_PRIVATE);
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


        public static void saveAuthorizationInfo(GitHubAuthorizationBean authorizationBean) {
            save(KEY_TOKEN, authorizationBean);
        }


        public static GitHubAuthorizationBean readAuthorizationInfo() {
            String authorizationString = (String) read(KEY_TOKEN, "");
            if (StringUtils.isEmpty(authorizationString)) {
                return null;
            }
            return GsonUtils.fromJson(authorizationString, GitHubAuthorizationBean.class);
        }


        public static void saveUser(GitHubUserBean userBean) {
            save(KEY_USER, userBean);
        }


        public static GitHubUserBean readUser() {
            String userString = (String) read(KEY_USER, "");
            if (StringUtils.isEmpty(userString)) {
                return null;
            }
            return GsonUtils.fromJson(userString, GitHubUserBean.class);
        }
    }
}