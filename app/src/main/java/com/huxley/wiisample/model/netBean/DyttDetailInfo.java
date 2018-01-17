package com.huxley.wiisample.model.netBean;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 16/7/20.
 */
public class DyttDetailInfo {

    public DyttDetailInfo() {
        pics = new ArrayList<>();
        urls = new ArrayList<>();
    }

    public String content;

    public List<String> pics;

    public List<String> urls;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
