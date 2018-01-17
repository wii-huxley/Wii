package com.huxley.wiisample.model.netBean;

import com.huxley.wiitools.utils.GsonUtils;

import java.util.List;

/**
 *
 * Created by LeiJin01 on 2016/7/29.
 */
public class CodekkSearchListBean {

    public boolean isFullSearch;
    public List<CodekkProjectBean> projectArray;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }

    public boolean isEmpty() {
        return projectArray == null || projectArray.size() <= 0;
    }
}
