package com.huxley.wiisample.page.github;

import com.huxley.wiisample.model.GitHubModel;
import com.huxley.wiitools.utils.logger.Logger;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;
import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class GithubContract {

    public interface View extends IMvpView {
        void updateUser();
    }

    public static class Present extends IMvpPresenter<View> {

        public void login(String userName, String password) {

            GitHubModel.getInstance().login(userName, password)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean items) {
                        getView().updateUser();
                    }
                });
        }
    }
}
