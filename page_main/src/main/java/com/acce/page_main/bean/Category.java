package com.acce.page_main.bean;

import com.huxley.fragmentation.base.SupportFragment;

import java.io.Serializable;

/**
 * Created by huxley on 2017/4/10.
 */
public class Category<F extends SupportFragment> implements Serializable {

    public String   title;
    public Theme    theme;
    public int      resId;
    public boolean  enable;
    public Class<F> clazz;

    public Category(Class<F> clazz, String title, Theme theme, int resId, boolean enable) {
        this.clazz = clazz;
        this.title = title;
        this.theme = theme;
        this.resId = resId;
        this.enable = enable;
    }
}
