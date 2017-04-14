package com.acce.page_main;

import android.content.Intent;
import android.os.Bundle;

import com.acce.page_main.home.HomeFragment;
import com.acce.page_main.model.bean.Category;
import com.huxley.wii.yl.page.mvp.MvpActivity;

import java.util.ArrayList;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by huxley on 2017/4/10.
 */
public class HomeActivity extends MvpActivity<HomeContract.Present> implements HomeContract.View {

    private ArrayList<Category> categories;

    @Override
    public int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categories")) {
            categories = (ArrayList<Category>) intent.getSerializableExtra("categories");
        }
        if (categories == null || categories.isEmpty()) {
            throw new RuntimeException(getClass().getSimpleName() + " intent must has categories!");
        }
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, HomeFragment.newInstance(categories));
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
