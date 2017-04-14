package com.acce.page_main.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.acce.page_main.R;
import com.acce.page_main.R2;
import com.acce.page_main.model.bean.Category;
import com.huxley.wii.yl.common.RecyclerViewHelper;
import com.huxley.wii.yl.common.YlUIHelper;
import com.huxley.wii.yl.common.utils.doubleClick.OnRvClickListener;
import com.huxley.wii.yl.page.mvp.MvpMainFragment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by huxley on 2017/4/8.
 */
public class HomeFragment extends MvpMainFragment<HomeContract.Present> implements HomeContract.View {

    @BindView(R2.id.toolbar)
    Toolbar      mToolbar;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<Category>     mCategories;
    private CommonAdapter<Category> mAdapter;

    public static HomeFragment newInstance(ArrayList<Category> categories) {
        Bundle args = new Bundle();
        args.putSerializable("categories", categories);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void init() {
        super.init();

        YlUIHelper.BaseToolbars.setTitle(mToolbar, (AppCompatActivity) getActivity(), "我的宝库");
        mCategories = (ArrayList<Category>) getArguments().getSerializable("categories");
        RecyclerViewHelper.setGrid(mRecyclerView, mAdapter = new CommonAdapter<Category>(_mActivity, R.layout.home_item_category, mCategories) {
            @Override
            protected void convert(ViewHolder holder, Category category, int position) {
                holder.setText(R.id.tv_name, category.title);
            }
        }, _mActivity, 2);
        mAdapter.setOnItemClickListener(new OnRvClickListener() {
            @Override
            public void onRvItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                start(getFragment(mAdapter.getDatas().get(holder.getAdapterPosition()).clazz));
            }
        });
    }

    public SupportFragment getFragment(Class<?> c) {
        try {
            Object yourObj = c.newInstance();
            Method newInstance = c.getMethod("newInstance");
            return (SupportFragment) newInstance.invoke(yourObj);
        } catch (Exception e) {
            throw new RuntimeException(c.getSimpleName() + " must has newInstance() Method!");
        }
    }
}
