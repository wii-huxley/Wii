package com.acce.page_main.wiiHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acce.page_main.R;
import com.acce.page_main.R2;
import com.acce.page_main.ThemeModel;
import com.acce.page_main.bean.Category;
import com.huxley.fragmentation.base.SupportFragment;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.StringUtils;
import com.huxley.yl.common.RecyclerViewHelper;
import com.huxley.yl.common.YlUIHelper;
import com.huxley.yl.common.utils.doubleClick.OnRvClickListener;
import com.huxley.yl.common.utils.doubleClick.OnYlClickListener;
import com.huxley.yl.page.mvp.MvpMainFragment;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huxley on 2017/4/8.
 */
public class WiiHomeFragment extends MvpMainFragment<WiiHomeContract.Present> implements WiiHomeContract.View {

    @BindView(R2.id.toolbar)
    Toolbar        mToolbar;
    @BindView(R2.id.recyclerView)
    RecyclerView   mRecyclerView;
    @BindView(R2.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R2.id.drawer_layout)
    DrawerLayout   mDrawerLayout;

    ImageView mIvUserPhoto;
    TextView  mTvUserName;

    private ArrayList<Category>     mCategories;
    private CommonAdapter<Category> mAdapter;
    private Class<?>                mClazz;

    public static WiiHomeFragment newInstance(Class<?> clazz, ArrayList<Category> categories) {
        Bundle args = new Bundle();
        args.putSerializable("categories", categories);
        args.putSerializable("clazz", clazz);
        WiiHomeFragment fragment = new WiiHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.wii_home_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return mRootView;
    }

    protected void init() {
        YlUIHelper.BaseToolbars.setTitleAndNav(mDrawerLayout, mToolbar, (AppCompatActivity) getActivity(), "我的宝库");
        mCategories = (ArrayList<Category>) getArguments().getSerializable("categories");
        mClazz = (Class<?>) getArguments().getSerializable("clazz");
        RecyclerViewHelper.setGrid(mRecyclerView, mAdapter = new CommonAdapter<Category>(_mActivity, R.layout.wii_home_item_category, mCategories) {
            @Override
            protected void convert(ViewHolder holder, Category category, int position) {
                ((CardView) holder.getView(R.id.card_view)).setCardBackgroundColor(ResUtils.getColor(category.theme.colorPrimaryId));
                holder.setBackgroundColor(R.id.tv_name, ResUtils.getColor(category.theme.colorPrimaryId));
                holder.setText(R.id.tv_name, category.title);
                holder.getView(R.id.tv_name).setTransitionName(category.title);
            }
        }, _mActivity, 2);
        mAdapter.setOnItemClickListener(new OnRvClickListener() {
            @Override
            public void onRvItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Category category = mAdapter.getDatas().get(holder.getAdapterPosition());
                ThemeModel.getInstance().setCategory(category);
                start(getFragment(category.clazz));
            }
        });
        View headerView = mNavigationView.getHeaderView(0);
        OnYlClickListener onYlClickListener = new OnYlClickListener() {
            @Override
            public void onYlClick(View view) {
                startLogin();
            }
        };
        mTvUserName = (TextView) headerView.findViewById(R.id.tv_user_name);
        mIvUserPhoto = (ImageView) headerView.findViewById(R.id.iv_user_photo);
        mIvUserPhoto.setOnClickListener(onYlClickListener);
        mTvUserName.setOnClickListener(onYlClickListener);
    }

    private void startLogin() {
        if (!"未登录".equals(mTvUserName.getText().toString())) {
            return;
        }
        startActivityForResult(new Intent(getContext(), mClazz), 0);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 0:
                setUserName(data.getStringExtra("userName"));
                break;
        }
    }

    private void setUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            userName = "未登录";
        }
        mTvUserName.setText(userName);
    }

    @Override
    protected WiiHomeContract.Present getPresenter() {
        return new WiiHomeContract.Present();
    }
}
