package com.huxley.page_login.acceLogin;

import com.huxley.page_login.model.MyUserModel;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import rx.Subscriber;

/**
 * Created by huxley on 2017/4/8.
 */

public class AcceLoginContract {

    public interface View extends IMvpView {

        void loginSuccess();
        void showProgress(String msg, boolean isCancel);
        void dismissProgress();
    }

    public static class Present extends IMvpPresenter<View> {

        public void login(String userId, String pwd) {
//            UserModel.getInstance().login(userId, pwd, new SaveListener<BmobUser>() {
//                @Override
//                public void done(BmobUser bmobUser, BmobException e) {
//                    if(e==null){
//                        WiiToast.success("登录成功");
//                        UserModel.getInstance().setBmobUser(bmobUser);
//                        getView().loginSuccess();
//                    }else{
//                        WiiToast.error("登录失败：" + e.getErrorCode());
//                    }
//                }
//            });
            MyUserModel.getInstance().login(userId, pwd).subscribe(new Subscriber<Object>() {
                @Override
                public void onStart() {
                    getView().showProgress("登录中", false);
                }
                @Override
                public void onCompleted() {
                    getView().dismissProgress();
                }
                @Override
                public void onError(Throwable throwable) {
                    WiiToast.error("加载失败");
                    getView().dismissProgress();
                }
                @Override
                public void onNext(Object userBean) {
                    getView().loginSuccess();
                }
            });
        }

        public void LoginWechat() {

        }

        public void loginSina() {

        }
    }
}
