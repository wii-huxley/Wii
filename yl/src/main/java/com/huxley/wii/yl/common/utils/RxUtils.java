package com.huxley.wii.yl.common.utils;

/**
 * Created by huxley on 17/1/20.
 */
public class RxUtils {

//    public static <T> Observable.Transformer<T, T> applySchedulers(final BaseView view) {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Action0() {
//                            @Override
//                            public void call() {//显示进度条
//                                view.showLoading();
//                            }
//                        })
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doAfterTerminate(new Action0() {
//                            @Override
//                            public void call() {
//                                view.hideLoading();//隐藏进度条
//                            }
//                        }).compose(RxUtils.<T>bindToLifecycle(view));
//            }
//        };
//    }
//
//
//    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
//        if (view instanceof BaseActivity) {
//            return ((BaseActivity) view).<T>bindToLifecycle();
//        } else if (view instanceof BaseFragment) {
//            return ((BaseFragment) view).<T>bindToLifecycle();
//        } else {
//            throw new IllegalArgumentException("view isn't activity or fragment");
//        }
//
//    }

}