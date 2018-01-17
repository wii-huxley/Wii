package com.huxley.wiisample.page.dytt.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huxley.wiisample.R;
import com.huxley.wiisample.common.ImageLoaderUtils;
import com.huxley.wiisample.common.Tools;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.DyttModel;
import com.huxley.wiisample.model.netBean.DyttDetailInfo;
import com.huxley.wiisample.model.netBean.DyttListBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.wiitools.view.dialog.DialogFragmentHelper;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class DyttDetailFragment extends MvpSwipeBackFragment<DyttDetailContract.Present> implements DyttDetailContract.View {

    DyttListBean.MovieInfo mMovieInfo;
    @BindView(R.id.toolbar)
    Toolbar   mToolbar;
    @BindView(R.id.iv_movie)
    ImageView mIvMovie;

    public static DyttDetailFragment newInstance(DyttListBean.MovieInfo movieInfo) {
        Bundle args = new Bundle();
        args.putSerializable("movieInfo", movieInfo);
        DyttDetailFragment fragment = new DyttDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dytt_detail_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        mMovieInfo = (DyttListBean.MovieInfo) getArguments().getSerializable("movieInfo");
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, mMovieInfo.name);
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        init();
    }

    public void init() {
        mPresenter.loadMovieDetail(mMovieInfo.url);
    }

    @Override
    public void loadMovieDetailSuccess(DyttDetailInfo data) {
        List<String> pics = data.pics;
        if (pics != null && pics.size() > 0) {
            String remove = pics.remove(0);
            ImageLoaderUtils.setMovieImage(mIvMovie, remove);
        }
        mIvMovie.setOnLongClickListener(view -> {
            DialogFragmentHelper.showListDialog(
                    getChildFragmentManager(),
                    "下载地址",
                    data.urls.toArray(new String[data.urls.size()]),
                    result -> onClickDownload(data.urls.get(result)),
                    true
            );
            return true;
        });
    }


    public void onClickDownload(String ftpUrl) {
        if (TextUtils.isEmpty(ftpUrl)) {
            WiiToast.warn("尚未提供下载地址~稍后再试哦");
            return;
        }
        if (Tools.checkIsInstall(getContext(), "com.xunlei.downloadprovider")) {
            getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DyttModel.getInstance().encode(ftpUrl))));
        } else {
            WiiToast.error("该影片链接为迅雷P2P资源，须安装迅雷下载工具才能进行下载！");
        }
    }

    @Override
    protected DyttDetailContract.Present getPresenter() {
        return new DyttDetailContract.Present();
    }
}
