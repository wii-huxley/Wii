package com.huxley.wiisample.model.netBean;


import com.huxley.wiitools.utils.GsonUtils;

/**
 *
 * Created by LeiJin01 on 2016/7/29.
 */
public class CodekkResultBean<D> {

    public int code;
    public String message;
    public D data;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
