package com.huxley.wiisample.page.gank;

import com.huxley.wiisample.model.GankModel;
import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class GankContract {

    public interface View extends IMvpView {


        void setRefreshing(boolean refreshing);
        void getPicSuccess(boolean isLoad, ArrayList<GankBean> gankBean);
    }

    public static class Present extends IMvpPresenter<View> {

        public int mCount = 1;

        public void getPicList(boolean isLoadMore) {
            if (!isLoadMore) {
                mCount = 1;
            }
            if (mCount <= 0) {
                return;
            }
            getView().setRefreshing(true);
            GankModel.getInstance().getPic(mCount).subscribe(new Subscriber<ArrayList<GankBean>>() {
                @Override
                public void onCompleted() {
                    if (getView() != null) {
                        getView().setRefreshing(false);
                        mCount++;
                    }
                }
                @Override
                public void onError(Throwable throwable) {
                    if (getView()!= null) {
                        getView().setRefreshing(false);
                        WiiToast.error("加载失败");
                    }
                }
                @Override
                public void onNext(ArrayList<GankBean> gankBean) {
                    if (getView() != null) {
                        getView().getPicSuccess(isLoadMore, gankBean);
                    }
                }
            });
        }
    }
}
