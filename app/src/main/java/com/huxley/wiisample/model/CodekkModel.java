package com.huxley.wiisample.model;

import com.huxley.wiisample.model.api.CodekkApi;
import com.huxley.wiisample.model.netBean.CodekkHomeListBean;
import com.huxley.wiisample.model.netBean.CodekkResultBean;
import com.huxley.wiitools.WiiException;
import com.huxley.wiitools.retrofitUtils.HttpClient;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 2017/8/23.
 */
public class CodekkModel {

    private static CodekkModel instance;
    private        CodekkApi   mCodekkApi;

    private CodekkModel() {
        mCodekkApi = new HttpClient<>(CodekkApi.class).getApi(CodekkApi.BASE_URL);
    }

    public static CodekkModel getInstance() {
        if (instance == null) {
            synchronized (CodekkModel.class) {
                if (instance == null) {
                    instance = new CodekkModel();
                }
            }
        }
        return instance;
    }

    public Observable<CodekkHomeListBean> getProjectList(int num) {
        return mCodekkApi.getProjectList(num)
                .compose(io_main())
                .compose(handleResult());
    }

    public Observable<CodekkHomeListBean> searchProjectList(String query, int page) {
        return mCodekkApi.searchProjectList(query, page)
                .compose(io_main())
                .compose(handleResult());
    }


    public <T> Observable.Transformer<T, T> io_main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable.Transformer<CodekkResultBean<T>, T> handleResult() {
        return tObservable -> tObservable.flatMap(
                new Func1<CodekkResultBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(CodekkResultBean<T> result) {
                        if (result.code == 0) {
                            return Observable.just(result.data);
                        } else {
                            return Observable.error(new WiiException());
                        }
                    }
                }
        );
    }

}