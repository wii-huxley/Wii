package com.huxley.wiisample.model;

import com.huxley.wiisample.model.api.GankApi;
import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiisample.model.netBean.GankDayBean;
import com.huxley.wiisample.model.netBean.GankResultBean;
import com.huxley.wiitools.WiiException;
import com.huxley.wiitools.retrofitUtils.HttpClient;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 2017/8/23.
 */
public class GankModel {

    private static GankModel instance;
    private        GankApi   mGankApi;

    private GankModel() {
        mGankApi = new HttpClient<>(GankApi.class).getApi(GankApi.BASE_URL);
    }

    public static GankModel getInstance() {
        if (instance == null) {
            synchronized (GankModel.class) {
                if (instance == null) {
                    instance = new GankModel();
                }
            }
        }
        return instance;
    }

    public Observable<ArrayList<GankBean>> getPic(int page) {
        return mGankApi.getPic("福利", 20, page)
                .compose(io_main())
                .compose(handleResult());
    }

    public Observable<GankDayBean> getDay(String day) {
        return mGankApi.getDay(day)
                .compose(io_main())
                .compose(handleResult());
    }


    public <T> Observable.Transformer<T, T> io_main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable.Transformer<GankResultBean<T>, T> handleResult() {
        return tObservable -> tObservable.flatMap(
                new Func1<GankResultBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(GankResultBean<T> result) {
                        if (!result.error) {
                            return Observable.just(result.results);
                        } else {
                            return Observable.error(new WiiException());
                        }
                    }
                }
        );
    }

}