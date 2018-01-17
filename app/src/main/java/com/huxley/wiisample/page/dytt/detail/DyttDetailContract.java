package com.huxley.wiisample.page.dytt.detail;

import com.huxley.wiisample.model.DyttModel;
import com.huxley.wiisample.model.netBean.DyttDetailInfo;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class DyttDetailContract {

    public interface View extends IMvpView {
        void loadMovieDetailSuccess(DyttDetailInfo movieDetailInfo);
    }

    public static class Present extends IMvpPresenter<View> {

        public void loadMovieDetail(String url){
            DyttModel.getInstance().getMovieDetailInfo(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DyttDetailInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DyttDetailInfo movieDetailInfo) {
                            getView().loadMovieDetailSuccess(movieDetailInfo);
                        }
                    });
        }

    }
}
