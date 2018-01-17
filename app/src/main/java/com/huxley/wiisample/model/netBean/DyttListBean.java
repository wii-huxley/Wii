package com.huxley.wiisample.model.netBean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 电影列表bean
 * Created by huxley on 16/7/22.
 */
public class DyttListBean implements Serializable {

    public DyttListBean() {
        mMovieInfos = new ArrayList<>();
    }

    public List<MovieInfo> mMovieInfos;

    public String nextUrl;

    public static class MovieInfo implements Serializable {

        /** 电影介绍 */
        public String url;

        /** 电影名称 */
        public String name;

        /** 电影内容 */
        public String content;
    }

    @Override
    public String toString() {
        return new Gson().toString();
    }

    public boolean isEmpty() {
        return mMovieInfos == null || mMovieInfos.size() <= 0;
    }
}
