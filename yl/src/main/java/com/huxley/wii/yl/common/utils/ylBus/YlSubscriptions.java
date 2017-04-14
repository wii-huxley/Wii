package com.huxley.wii.yl.common.utils.ylBus;

import java.lang.reflect.Type;
import java.util.HashMap;

import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * 管理 CompositeSubscription
 * <p>
 * Created by YoKeyword on 16/7/19.
 */
public class YlSubscriptions {

    private static CompositeSubscription       mSubscriptions       = new CompositeSubscription();
    private static HashMap<Type, Subscription> mSubscriptionHashMap = new HashMap<>();

    public static boolean isUnsubscribed() {
        return mSubscriptions.isUnsubscribed();
    }

    public static<T> void registerEvent(final OnYlEventListener<T> listener) {
        Subscription subscription = mSubscriptionHashMap.get(listener.c);
        if (subscription != null) {
            mSubscriptions.remove(subscription);
        }
        subscription = YlBus.getDefault().toObservable(listener.c)
                .map(new Func1<T, T>() {
                    @Override
                    public T call(T event) {
                        return listener.onTransform(event);
                    }
                })
                .subscribe(new YlBusSubscriber<T>() {
                    @Override
                    protected void onEvent(T event) {
                        listener.onEvent(event);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent(listener);
                    }
                });
        if (subscription != null) {
            mSubscriptions.add(subscription);
        }
    }

    public static<T> void unRegisterEvent(Class<T> tClass) {
        Subscription subscription = mSubscriptionHashMap.get(tClass);
        if (subscription != null) {
            mSubscriptions.remove(subscription);
        }
    }

    public static void clear() {
        mSubscriptions.clear();
    }

    public static void unsubscribe() {
        mSubscriptions.unsubscribe();
    }

    public static boolean hasSubscriptions() {
        return mSubscriptions.hasSubscriptions();
    }
}
