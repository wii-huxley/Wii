package com.huxley.wiisample.page.dytt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.acce.page_main.ThemeModel;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.FragmentAdapter;
import com.huxley.wiisample.common.LoadMoreDelegate;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.DyttModel;
import com.huxley.wiisample.model.localBean.StartBrotherEvent;
import com.huxley.wiisample.model.netBean.DyttListBean;
import com.huxley.wiisample.page.dytt.child.DyttChildFragment;
import com.huxley.wiisample.page.dytt.detail.DyttDetailFragment;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.utils.SoftInputUtil;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class DyttFragment extends MvpSwipeBackFragment<DyttContract.Present> implements DyttContract.View {

    String                                query;
    boolean                               isOpen;
    CommonAdapter<DyttListBean.MovieInfo> mAdapter;
    String                                mNextUrl;

    @BindView(R.id.toolbar)
    Toolbar            mToolbar;
    @BindView(R.id.tabs)
    TabLayout          mTabs;
    @BindView(R.id.viewpager)
    ViewPager          mViewpager;
    @BindView(R.id.rv)
    RecyclerView       mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public static DyttFragment newInstance() {
        Bundle args = new Bundle();
        DyttFragment fragment = new DyttFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dytt_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, mRootView);
        EventBus.getDefault().register(this);
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, ThemeModel.getInstance().getTitle());
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_codekk_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("请输入搜索关键字");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DyttFragment.this.query = query;
                mPresenter.startSearch(false, query);
                SoftInputUtil.hideSoftInput(DyttFragment.this.getActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                closeSearch();
                isOpen = false;
                mNextUrl = null;
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                openSearch();
                isOpen = true;
                return true;
            }
        });
    }

    private void openSearch() {
        mTabs.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
        mSrl.setVisibility(View.VISIBLE);
    }

    private void closeSearch() {
        mTabs.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.VISIBLE);
        mSrl.setVisibility(View.GONE);
        mAdapter.getDatas().clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }

    public void init() {
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/gndy/dyzz/"), "最新电影");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/gndy/china/"), "国内电影");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/gndy/oumei/"), "欧美电影");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/gndy/rihan/"), "日韩电影");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/tv/hytv/"), "华语电视");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/tv/rihantv/"), "日韩电视");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/tv/oumeitv/"), "欧美电视");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/zongyi2013/"), "最新综艺");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/2009zongyi/"), "旧版综艺");
        adapter.addFragment(DyttChildFragment.newInstance(DyttModel.BaseUrl + "/html/dongman/"), "动漫资源");
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(10);
        mTabs.setupWithViewPager(mViewpager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mAdapter = new CommonAdapter<DyttListBean.MovieInfo>(_mActivity, R.layout.dytt_child_item_movie, new ArrayList<>()) {
            @Override
            protected void convert(ViewHolder holder, DyttListBean.MovieInfo movieInfo, int position) {
                holder.setText(R.id.tv_name, movieInfo.name);
                holder.setText(R.id.tv_content, movieInfo.content);
                holder.itemView.setOnClickListener(view -> start(DyttDetailFragment.newInstance(movieInfo)));
            }
        });
        new LoadMoreDelegate(new LoadMoreDelegate.LoadMoreSubject() {
            @Override
            public boolean isLoading() {
                return mSrl.isRefreshing();
            }
            @Override
            public void onLoadMore() {
                if (isOpen && mNextUrl != null) {
                    mPresenter.startSearch(true, DyttModel.SBaseUrl + mNextUrl);
                }
            }
        }).attach(mRv);

        mSrl.setSize(SwipeRefreshLayout.DEFAULT);
        mSrl.setColorSchemeResources(R.color.color_amber_500, R.color.color_blue_500,
                R.color.color_brown_500, R.color.color_cyan_500, R.color.color_green_500, R.color.color_grey_500,
                R.color.color_indigo_500, R.color.color_lime_500, R.color.color_orange_500, R.color.color_teal_500);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_amber_50);
        mSrl.setOnRefreshListener(() -> {
            if (isOpen) {
                mPresenter.startSearch(false, query);
            }
        });
    }

    @Subscribe
    public void openDetailEvent(StartBrotherEvent event) {
        start(event.targetFragment);
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
    public void searchMovieListSuccess(boolean isLoadMore, DyttListBean data) {
        mNextUrl = data.nextUrl;
        if (!isLoadMore) {
            mAdapter.getDatas().clear();
        }
        mAdapter.getDatas().addAll(data.mMovieInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected DyttContract.Present getPresenter() {
        return new DyttContract.Present();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
