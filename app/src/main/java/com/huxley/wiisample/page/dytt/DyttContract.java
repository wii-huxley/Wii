package com.huxley.wiisample.page.dytt;

import com.huxley.wiisample.model.DyttModel;
import com.huxley.wiisample.model.netBean.DyttListBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import rx.Observable;
import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class DyttContract {

    public interface View extends IMvpView {
        void setRefreshing(boolean refreshing);

        void searchMovieListSuccess(boolean isLoadMore, DyttListBean data);
    }

    public static class Present extends IMvpPresenter<View> {

        public void startSearch(boolean isLoadMore, String query) {
            getView().setRefreshing(true);
            if (isLoadMore) {
                DyttModel.getInstance().search_1(query)
                        .subscribe(new Subscriber<DyttListBean>() {
                            @Override
                            public void onCompleted() {
                                if (getView() != null) {
                                    getView().setRefreshing(false);
                                }
                            }
                            @Override
                            public void onError(Throwable throwable) {
                                if (getView() != null) {
                                    getView().setRefreshing(false);
                                    throwable.printStackTrace();
                                    WiiToast.error("加载失败");
                                }
                            }
                            @Override
                            public void onNext(DyttListBean data) {
                                if (getView() != null) {
                                    getView().searchMovieListSuccess(true, data);
                                }
                            }
                        });
            } else {
                Observable<DyttListBean> search = DyttModel.getInstance().search(query);
                search.subscribe(new Subscriber<DyttListBean>() {
                            @Override
                            public void onCompleted() {
                                getView().setRefreshing(false);
                            }
                            @Override
                            public void onError(Throwable throwable) {
                                if (getView() != null) {
                                    getView().setRefreshing(false);
                                    throwable.printStackTrace();
                                    WiiToast.error("加载失败");
                                }
                            }
                            @Override
                            public void onNext(DyttListBean data) {
                                if (getView() != null) {
                                    getView().searchMovieListSuccess(false, data);
                                }
                            }
                        });
            }
        }
    }
}
