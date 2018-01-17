package com.huxley.wiisample.page.github;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.acce.page_main.ThemeModel;
import com.bumptech.glide.Glide;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.GitHubModel;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.view.WiiInput;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class GithubFragment extends MvpSwipeBackFragment<GithubContract.Present>
    implements GithubContract.View {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_github_id) TextView mTvGithubId;
    @BindView(R.id.input_repositories) WiiInput mInputRepositories;
    @BindView(R.id.input_stars) WiiInput mInputStars;
    @BindView(R.id.input_followers) WiiInput mInputFollowers;
    @BindView(R.id.input_following) WiiInput mInputFollowing;
    @BindView(R.id.srl) SwipeRefreshLayout mSrl;
    @BindView(R.id.iv_avatar_url) ImageView mIvAvatarUrl;


    public static GithubFragment newInstance() {
        Bundle args = new Bundle();
        GithubFragment fragment = new GithubFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.github_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, ThemeModel.getInstance().getTitle());
        return attachToSwipeBack(mRootView);
    }


    @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }


    public void init() {
        String userName = "wii-huxley";
        String password = "huang19920505";
        mPresenter.login(userName, password);
    }


    @Override protected GithubContract.Present getPresenter() {
        return new GithubContract.Present();
    }


    @Override public void updateUser() {
        mTvName.setText(GitHubModel.getInstance().getUserInfo().name);
        mTvGithubId.setText(GitHubModel.getInstance().getUserInfo().login);
        mInputRepositories.setContent(
            String.valueOf(GitHubModel.getInstance().getUserInfo().public_repos));
        mInputFollowers.setContent(
            String.valueOf(GitHubModel.getInstance().getUserInfo().followers));
        mInputFollowing.setContent(
            String.valueOf(GitHubModel.getInstance().getUserInfo().following));

        Glide.with(_mActivity)
            .load(GitHubModel.getInstance().getUserInfo().avatar_url)
            .centerCrop()
            .override(ResUtils.dpToPx(50), ResUtils.dpToPx(50))
            .into(mIvAvatarUrl);
    }
}
