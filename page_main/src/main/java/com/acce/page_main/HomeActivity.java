package com.acce.page_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.acce.page_main.bean.Category;
import com.acce.page_main.wiiHome.WiiHomeFragment;
import com.huxley.fragmentation.anim.DefaultHorizontalAnimator;
import com.huxley.fragmentation.anim.FragmentAnimator;
import com.huxley.yl.page.mvp.MvpActivity;

import java.util.ArrayList;


/**
 * Created by huxley on 2017/4/10.
 */
public class HomeActivity extends MvpActivity<HomeContract.Present> implements HomeContract.View {

    private ArrayList<Category> categories;
    private Class<?> clazz;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categories") && intent.hasExtra("clazz")) {
            categories = (ArrayList<Category>) intent.getSerializableExtra("categories");
            clazz = (Class<?>) getIntent().getSerializableExtra("clazz");
        }
        if (categories == null || categories.isEmpty()) {
            throw new RuntimeException(getClass().getSimpleName() + " intent must has categories!");
        }
        if (savedInstanceState == null) {
            WiiHomeFragment homeFragment = WiiHomeFragment.newInstance(clazz, categories);
            loadRootFragment(R.id.main_container, homeFragment);
        }
    }

    @Override
    protected HomeContract.Present getPresenter() {
        return new HomeContract.Present();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
