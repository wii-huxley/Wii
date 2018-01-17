package com.huxley.wiisample.model.api;

import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiisample.model.netBean.GankDayBean;
import com.huxley.wiisample.model.netBean.GankResultBean;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by huxley on 2017/8/23.
 */

public interface GankApi {

    String BASE_URL = "http://gank.io/api/";

    @GET("day/history")
    Observable<Object> getDatas();

    /** 每日数据 */
    @GET("day/{date}")
    Observable<Object> getGankDetailInfo(
            @Path("date") String date    //日期      年/月/日
    );

    /** 每日数据 */
    @GET("data/{type}/{count}/{page}")
    Observable<GankResultBean<ArrayList<GankBean>>> getPic(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

    @GET("day/{day}")
    Observable<GankResultBean<GankDayBean>> getDay(
            @Path("day") String day
    );
}
