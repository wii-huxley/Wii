package com.huxley.page_login.model;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by huxley on 2017/9/1.
 */

public interface MyLoginApi {

    String BASE_URL = "http://wx.dwh027.com/";

    @FormUrlEncoded
    @POST("yirenhuan/api/android/androidApi.php")
    Observable<Object> sendVcode(
            @Field("apiType") String apiType,
            @Field("timeStamp") String timeStamp,
            @Field("uniqid") String uniqid,
            @Field("phone") String phone,
            @Field("mainStr") String mainStr
    );

    @FormUrlEncoded
    @POST("yirenhuan/api/android/androidApi.php")
    Observable<Object> register(
            @Field("apiType") String apiType,
            @Field("timeStamp") String timeStamp,
            @Field("uniqid") String uniqid,
            @Field("phone") String phone,
            @Field("mainStr") String mainStr,
            @Field("code") String code,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("yirenhuan/api/android/androidApi.php")
    Observable<Object> login(
            @Field("apiType") String apiType,
            @Field("timeStamp") String timeStamp,
            @Field("uniqid") String uniqid,
            @Field("phone") String phone ,
            @Field("mainStr") String mainStr,
            @Field("password") String password
    );

}
