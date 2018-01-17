package com.huxley.wiisample.model;

import android.util.Base64;
import com.huxley.wiisample.common.SP;
import com.huxley.wiisample.common.WiiConstant;
import com.huxley.wiisample.model.api.GitHubApi;
import com.huxley.wiisample.model.localBean.GitHubLoginBean;
import com.huxley.wiisample.model.netBean.GitHubAuthorizationBean;
import com.huxley.wiisample.model.netBean.GitHubUserBean;
import com.huxley.wiitools.retrofitUtils.HttpClient;
import com.huxley.wiitools.retrofitUtils.RxHelper;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by huxley on 2017/8/23.
 */
public class GitHubModel {

    private static GitHubModel instance;
    private GitHubApi mGitHubApi;
    private GitHubUserBean mUserBean;
    private GitHubAuthorizationBean mAuthorizationBean;


    private GitHubModel() {
        mGitHubApi = new HttpClient<>(GitHubApi.class).getApi(GitHubApi.BASE_URL);
    }


    public static GitHubModel getInstance() {
        if (instance == null) {
            synchronized (GitHubModel.class) {
                if (instance == null) {
                    instance = new GitHubModel();
                }
            }
        }
        return instance;
    }


    public Observable<Boolean> login(String userName, String password) {
        String userCredentials = userName + ":" + password;

        String basicAuth = "Basic " +
            new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));

        GitHubLoginBean createAuthorization = new GitHubLoginBean();
        createAuthorization.note = WiiConstant.GitHub.NOTE;
        createAuthorization.client_id = WiiConstant.GitHub.CLIENT_ID;
        createAuthorization.client_secret = WiiConstant.GitHub.CLIENT_SECRET;
        createAuthorization.scopes = WiiConstant.GitHub.SCOPES;

        return mGitHubApi.authorization(basicAuth.trim(), createAuthorization)
            .flatMap(new Func1<GitHubAuthorizationBean, Observable<GitHubUserBean>>() {
                @Override
                public Observable<GitHubUserBean> call(GitHubAuthorizationBean authorization) {
                    setAuthorizationInfo(authorization);
                    return mGitHubApi.getUserInfo(basicAuth.trim(), userName);
                }
            }).map(userInfo -> {
                if (userInfo != null) {
                    setUserInfo(userInfo);
                    return true;
                }
                return false;
            })
            .compose(RxHelper.io_main());
    }


    public void setAuthorizationInfo(GitHubAuthorizationBean authorizationBean) {
        this.mAuthorizationBean = authorizationBean;
        SP.GitHub.saveAuthorizationInfo(authorizationBean);
    }


    public void setUserInfo(GitHubUserBean userBean) {
        this.mUserBean = userBean;
        SP.GitHub.saveUser(userBean);
    }


    public GitHubUserBean getUserInfo() {
        if (mUserBean == null) {
            mUserBean = SP.GitHub.readUser();
        }
        return mUserBean;
    }
}