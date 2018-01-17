package com.huxley.wiisample.model.api;

import com.huxley.wiisample.model.netBean.GitHubAuthorizationBean;
import com.huxley.wiisample.model.netBean.GitHubUserBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by huxley on 2017/8/23.
 */

public interface GitHubApi {

    String BASE_URL = "https://api.github.com/";

    @POST("/authorizations")
    Observable<GitHubAuthorizationBean> authorization(
        @Header("Authorization") String Authorization,
        @Body Object createAuthorization
    );


    @GET("users/{user}")
    Observable<GitHubUserBean> getUserInfo(
        @Header("Authorization") String Authorization,
        @Path("user") String user
    );

}