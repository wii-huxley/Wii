package com.huxley.wiisample.page.dytt.child;

import com.huxley.wiisample.model.DyttModel;
import com.huxley.wiisample.model.netBean.DyttListBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class DyttChildContract {

    public interface View extends IMvpView {
        void setRefreshing(boolean refreshing);

        void getMovieListSuccess(boolean isLoadMore, DyttListBean data);
    }

    public static class Present extends IMvpPresenter<View> {

        int mCount = 1;

        public void getMovieList(boolean isLoadMore, String url) {
            if (!isLoadMore) {
                mCount = 1;
            }
            if (mCount <= 0) {
                return;
            }
            getView().setRefreshing(true);
            DyttModel.getInstance().getMovieList(url)
                    .subscribe(new Subscriber<DyttListBean>() {
                        @Override
                        public void onCompleted() {
                            getView().setRefreshing(false);
                            mCount++;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            getView().setRefreshing(false);
                            throwable.printStackTrace();
                            WiiToast.error("加载失败");
                        }

                        @Override
                        public void onNext(DyttListBean data) {
                            getView().getMovieListSuccess(isLoadMore, data);
                        }
                    });
        }
    }
}
