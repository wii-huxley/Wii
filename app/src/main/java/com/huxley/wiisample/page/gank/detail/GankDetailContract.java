package com.huxley.wiisample.page.gank.detail;

import com.huxley.wiisample.model.GankModel;
import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiisample.model.netBean.GankDayBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class GankDetailContract {

    public interface View extends IMvpView {

        void getDaySuccess(ArrayList<GankBean> gankDetailBean);
    }

    public static class Present extends IMvpPresenter<View> {

        public void getDay(String day) {
            GankModel.getInstance().getDay(day).subscribe(new Subscriber<GankDayBean>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable throwable) {
                    WiiToast.error("加载失败");
                }
                @Override
                public void onNext(GankDayBean gankBean) {
                    if (gankBean != null) {
                        ArrayList<GankBean> gankDetailBean = new ArrayList<>();
                        gankDetailBean = addGank(gankDetailBean, gankBean.Android);
                        gankDetailBean = addGank(gankDetailBean, gankBean.App);
                        gankDetailBean = addGank(gankDetailBean, gankBean.iOS);
//                        gankDetailBean = addGank(gankDetailBean, gankBean.restVideo);
                        gankDetailBean = addGank(gankDetailBean, gankBean.frontEnd);
                        gankDetailBean = addGank(gankDetailBean, gankBean.recommend);
//                        gankDetailBean = addGank(gankDetailBean, gankBean.photo);
                        gankDetailBean = addGank(gankDetailBean, gankBean.expandResources);
                        getView().getDaySuccess(gankDetailBean);
                    }
                }
            });
        }

        public ArrayList<GankBean> addGank(ArrayList<GankBean> gankDetailBean, ArrayList<GankBean> gankList) {
            if (gankList != null && !gankList.isEmpty()) {
                gankDetailBean.addAll(gankList);
            }
            return gankDetailBean;
        }
    }
}
