package com.huxley.page_login.acceRegister;

import com.huxley.page_login.model.MyUserModel;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import rx.Subscriber;

/**
 * Created by huxley on 2017/4/8.
 */

public class AcceRegisterContract {

    public interface View extends IMvpView {

        void registerSuccess();

        void showProgress(String msg, boolean isCancel);
        void dismissProgress();

        void sendVcodeSuccess();
    }

    public static class Present extends IMvpPresenter<View> {

//        public void register(String userId, String pwd) {
//            UserModel.getInstance().register(userId, pwd, new SaveListener<BmobUser>() {
//                @Override
//                public void done(BmobUser s, BmobException e) {
//                    if(e==null){
//                        WiiToast.success("注册成功");
//                        getView().registerSuccess();
//                    }else{
//                        WiiToast.error("注册失败：" + e.getErrorCode());
//                    }
//                }
//            });
//        }

        public void register(String userId, String vcode, String pwd) {
            MyUserModel.getInstance().register(userId, vcode, pwd).subscribe(new Subscriber<Object>() {
                @Override
                public void onStart() {
                    getView().showProgress("注册中", false);
                }

                @Override
                public void onCompleted() {
                    getView().dismissProgress();
                }

                @Override
                public void onError(Throwable throwable) {
                    getView().dismissProgress();
                }

                @Override
                public void onNext(Object o) {
                    getView().registerSuccess();
                }
            });
        }

        public void sendVcode(String userId) {
            MyUserModel.getInstance().sendVcode(userId).subscribe(new Subscriber<Object>() {
                @Override
                public void onStart() {
                    getView().showProgress("发送中", false);
                }
                @Override
                public void onCompleted() {
                    getView().dismissProgress();
                }

                @Override
                public void onError(Throwable throwable) {
                    getView().dismissProgress();
                }

                @Override
                public void onNext(Object o) {
                    getView().sendVcodeSuccess();
                }
            });
        }
    }
}
