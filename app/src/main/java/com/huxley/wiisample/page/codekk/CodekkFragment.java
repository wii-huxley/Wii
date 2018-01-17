package com.huxley.wiisample.page.codekk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.acce.page_main.ThemeModel;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.netBean.CodekkProjectBean;
import com.huxley.wiisample.page.codekk.viewBinder.CodekkProjectTitleViewBinder;
import com.huxley.wiisample.page.codekk.viewBinder.CodekkProjectViewBinder;
import com.huxley.wiitools.utils.SoftInputUtil;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class CodekkFragment extends MvpSwipeBackFragment<CodekkContract.Present> implements CodekkContract.View {

    private boolean mIsFirstTimeTouchBottom = true;
    private int     PRELOAD_SIZE            = 6;
    private Items   items                   = new Items();
    boolean isOpen = false;
    String           query;
    MultiTypeAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar            mToolbar;
    @BindView(R.id.rv)
    RecyclerView       mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public static CodekkFragment newInstance() {
        Bundle args = new Bundle();
        CodekkFragment fragment = new CodekkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.codekk_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, mRootView);
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, ThemeModel.getInstance().getTitle());
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_codekk_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("请输入标题、标签、作者、关键词、描述等…");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.startSearch(false, CodekkFragment.this.query = query);
                SoftInputUtil.hideSoftInput(CodekkFragment.this.getActivity());
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
                mPresenter.closeSearch();
                isOpen = false;
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mPresenter.openSearch();
                isOpen = true;
                return true;
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }

    public void init() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        adapter = new MultiTypeAdapter(items);
        adapter.register(String.class, new CodekkProjectTitleViewBinder());
        adapter.register(CodekkProjectBean.class, new CodekkProjectViewBinder());
        mRv.setAdapter(adapter);

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                if (!mSrl.isRefreshing() && adapter.getItemCount() > 0 && layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= adapter.getItemCount() - PRELOAD_SIZE) {
                    if (!mIsFirstTimeTouchBottom) {
                        if (isOpen) {
                            mPresenter.startSearch(true, query);
                        } else {
                            mPresenter.getProjectList(true);
                        }
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });

        mSrl.setSize(SwipeRefreshLayout.DEFAULT);
        mSrl.setColorSchemeResources(R.color.color_amber_500, R.color.color_blue_500,
                R.color.color_brown_500, R.color.color_cyan_500, R.color.color_green_500, R.color.color_grey_500,
                R.color.color_indigo_500, R.color.color_lime_500, R.color.color_orange_500, R.color.color_teal_500);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_amber_50);
        mSrl.setOnRefreshListener(() -> {
            if (isOpen) {
                mPresenter.startSearch(false, query);
            } else {
                mPresenter.getProjectList(false);
            }
        });
        mPresenter.getProjectList(false);
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
    public void getProjectListSuccess(Items items) {
        this.items = items;
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected CodekkContract.Present getPresenter() {
        return new CodekkContract.Present();
    }
}
