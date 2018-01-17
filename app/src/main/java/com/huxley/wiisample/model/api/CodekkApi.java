package com.huxley.wiisample.model.api;

import com.huxley.wiisample.model.netBean.CodekkHomeListBean;
import com.huxley.wiisample.model.netBean.CodekkResultBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * Created by huxley on 16/7/28.
 */

public interface CodekkApi {

    String BASE_URL = "http://p.codekk.com/api/op/";

    @GET("page/{num}")
    Observable<CodekkResultBean<CodekkHomeListBean>> getProjectList(
            @Path("num") int num
    );

    @GET("search")
    Observable<CodekkResultBean<CodekkHomeListBean>> searchProjectList(
            @Query("text") String text,
            @Query("page") int page
    );
}
