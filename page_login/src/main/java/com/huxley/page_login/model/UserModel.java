package com.huxley.page_login.model;

import com.huxley.wiitools.utils.GsonUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huxley on 2017/8/23.
 */
public class UserModel {

    private static UserModel instance;
    private BmobUser mBmobUser;

    private UserModel() {
    }

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    public void register(String userId, String pwd, SaveListener listener) {
        BmobUser bu = new BmobUser();
        bu.setUsername(userId);
        bu.setPassword(pwd);
        bu.setEmail(userId);
        bu.signUp(listener);
    }

    public void login(String userId, String pwd, SaveListener listener) {
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(userId);
        bu2.setPassword(pwd);
        bu2.login(listener);
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.mBmobUser = bmobUser;
        UserSP.User.save(UserSP.KEY_USER, GsonUtils.toJson(bmobUser));
    }
}