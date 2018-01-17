package com.huxley.wiisample.page.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acce.page_main.ThemeModel;
import com.acce.page_main.utils.Tools;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.ImageLoaderUtils;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiisample.page.gank.detail.GankDetailFragment;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.yl.common.utils.doubleClick.OnRvClickListener;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class GankFragment extends MvpSwipeBackFragment<GankContract.Present> implements GankContract.View {

    private boolean mIsFirstTimeTouchBottom = true;
    private int     PRELOAD_SIZE            = 6;
    CommonAdapter<GankBean> mAdapter;

    @BindView(R.id.toolbar)
    Toolbar            mToolbar;
    @BindView(R.id.rv)
    RecyclerView       mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public static GankFragment newInstance() {
        Bundle args = new Bundle();
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.gank_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, ThemeModel.getInstance().getTitle());
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }

    public void init() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mAdapter = new CommonAdapter<GankBean>(_mActivity, R.layout.gank_item_pic, new ArrayList<>()) {
            @Override
            protected void convert(ViewHolder holder, GankBean gankBean, int position) {
                holder.setVisible(R.id.spacer, position == getItemCount() - 1);
                holder.setText(R.id.tvDate, gankBean.desc);
                ImageLoaderUtils.setGankImage(holder.getView(R.id.ivPhoto), gankBean.url + "?imageView2/0/w/" + (Tools.getScreenWidth() / 2 - ResUtils.dpToPx(10)));
            }
        });

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                if (!mSrl.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= mAdapter.getItemCount() - PRELOAD_SIZE) {
                    if (!mIsFirstTimeTouchBottom) {
                        mPresenter.getPicList(true);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnRvClickListener() {
            @Override
            public void onRvItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                GankDetailFragment gankDetailFragment = GankDetailFragment.newInstance(Tools.getDay(mAdapter.getDatas().get(position).publishedAt));
                start(gankDetailFragment);
            }
        });

        mSrl.setSize(SwipeRefreshLayout.DEFAULT);
        mSrl.setColorSchemeResources(R.color.color_amber_500, R.color.color_blue_500,
                R.color.color_brown_500, R.color.color_cyan_500, R.color.color_green_500, R.color.color_grey_500,
                R.color.color_indigo_500, R.color.color_lime_500, R.color.color_orange_500, R.color.color_teal_500);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_amber_50);
        mSrl.setOnRefreshListener(() -> mPresenter.getPicList(false));
        mPresenter.getPicList(false);
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
    public void getPicSuccess(boolean isLoadMore, ArrayList<GankBean> gankBean) {
        if (!isLoadMore) {
            mAdapter.getDatas().clear();
        }
        mAdapter.getDatas().addAll(gankBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected GankContract.Present getPresenter() {
        return new GankContract.Present();
    }
}
