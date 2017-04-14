package com.huxley.wii.wii.common;

/**
 * Created by huxley on 2017/2/20.
 */

public interface CallBack<T> {

    void onSuccess(T result);

    void onError(String errCode, String errMsg);
}
