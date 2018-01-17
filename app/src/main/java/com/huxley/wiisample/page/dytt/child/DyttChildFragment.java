package com.huxley.wiisample.page.dytt.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wiisample.R;
import com.huxley.wiisample.common.LoadMoreDelegate;
import com.huxley.wiisample.model.localBean.StartBrotherEvent;
import com.huxley.wiisample.model.netBean.DyttListBean;
import com.huxley.wiisample.page.dytt.detail.DyttDetailFragment;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.yl.page.mvp.MvpModelFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class DyttChildFragment extends MvpModelFragment<DyttChildContract.Present> implements DyttChildContract.View {

    String                                mUrl;
    String                                mNextUrl;
    CommonAdapter<DyttListBean.MovieInfo> mAdapter;

    @BindView(R.id.rv)
    RecyclerView       mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public static DyttChildFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        DyttChildFragment fragment = new DyttChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dytt_child_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        init();
    }

    public void init() {
        mUrl = getArguments().getString("url");
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mAdapter = new CommonAdapter<DyttListBean.MovieInfo>(_mActivity, R.layout.dytt_child_item_movie, new ArrayList<>()) {
            @Override
            protected void convert(ViewHolder holder, DyttListBean.MovieInfo movieInfo, int position) {
                holder.setText(R.id.tv_name, movieInfo.name);
                holder.setText(R.id.tv_content, movieInfo.content);
                holder.itemView.setOnClickListener(view ->
                        EventBus.getDefault().post(new StartBrotherEvent(DyttDetailFragment.newInstance(movieInfo))));
            }
        });
        new LoadMoreDelegate(new LoadMoreDelegate.LoadMoreSubject() {
            @Override
            public boolean isLoading() {
                return mSrl.isRefreshing();
            }
            @Override
            public void onLoadMore() {
                mPresenter.getMovieList(true, mUrl + mNextUrl);
            }
        }).attach(mRv);

        mSrl.setSize(SwipeRefreshLayout.DEFAULT);
        mSrl.setColorSchemeResources(R.color.color_amber_500, R.color.color_blue_500,
                R.color.color_brown_500, R.color.color_cyan_500, R.color.color_green_500, R.color.color_grey_500,
                R.color.color_indigo_500, R.color.color_lime_500, R.color.color_orange_500, R.color.color_teal_500);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_amber_50);
        mSrl.setOnRefreshListener(() -> mPresenter.getMovieList(false, mUrl));
        mPresenter.getMovieList(false, mUrl);
    }

    Runnable openRunnable;
    Runnable closeRunnable;

    @Override
    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            if (openRunnable == null) {
                openRunnable = () -> {
                    if (mSrl != null) {
                        mSrl.setRefreshing(true);
                    }
                };
            }
            getActivity().getWindow().getDecorView().post(openRunnable);
        } else {
            if (closeRunnable == null) {
                closeRunnable = () -> {
                    if (mSrl != null) {
                        mSrl.setRefreshing(false);
                    }
                };
            }
            getActivity().getWindow().getDecorView().postDelayed(closeRunnable, 300);
        }
    }

    @Override
    public void getMovieListSuccess(boolean isLoadMore, DyttListBean data) {
        mNextUrl = data.nextUrl;
        if (!isLoadMore) {
            mAdapter.getDatas().clear();
        }
        mAdapter.getDatas().addAll(data.mMovieInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected DyttChildContract.Present getPresenter() {
        return new DyttChildContract.Present();
    }
}
