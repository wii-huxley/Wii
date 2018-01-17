package com.huxley.page_login.model;

import com.huxley.page_login.LoginTools;
import com.huxley.wiitools.companyUtils.acce.AcceTools;
import com.huxley.wiitools.retrofitUtils.HttpClient;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 2017/9/1.
 */
public class MyUserModel {

    private static MyUserModel instance;
    private        MyLoginApi  mLoginApi;

    private MyUserModel() {
        mLoginApi = new HttpClient<>(MyLoginApi.class).getApi(MyLoginApi.BASE_URL);
    }

    public static MyUserModel getInstance() {
        if (instance == null) {
            synchronized (MyUserModel.class) {
                if (instance == null) {
                    instance = new MyUserModel();
                }
            }
        }
        return instance;
    }

    public Observable<Object> sendVcode(String phoneNum) {
        String timeStamp = LoginTools.getTimeStamp();
        String uniqid = LoginTools.getUniqid();
        String mainStr = LoginTools.getMainStr(timeStamp, uniqid, phoneNum);
        return mLoginApi.sendVcode("sendCode", timeStamp, uniqid, phoneNum, mainStr)
                .compose(this.io_main());
    }

    public Observable<Object> register(String phoneNum, String vcode, String password) {
        String timeStamp = LoginTools.getTimeStamp();
        String uniqid = LoginTools.getUniqid();
        String mainStr = LoginTools.getMainStr(timeStamp, uniqid, phoneNum);
        return mLoginApi.register("register", timeStamp, uniqid, phoneNum, mainStr, vcode, AcceTools.getEnPassword(password))
                .compose(this.io_main());
    }

    public Observable<Object> login(String phoneNum, String password) {
        String timeStamp = "1504604297787";
//                LoginTools.getTimeStamp();
        String uniqid = "ooodijrdda";
//                LoginTools.getUniqid();
        String mainStr = LoginTools.getMainStr(timeStamp, uniqid, phoneNum);
        return mLoginApi.login("login", timeStamp, uniqid, phoneNum, mainStr, AcceTools.getEnPassword(password))
                .compose(this.io_main());
    }

    public void isLogin(boolean isLogin) {

    }

    public <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}