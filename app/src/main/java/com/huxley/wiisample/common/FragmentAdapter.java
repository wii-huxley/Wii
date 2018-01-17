package com.huxley.wiisample.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huxley.fragmentation.base.SupportFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 2017/9/16.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<SupportFragment> mFragments      = new ArrayList<>();
    private final List<String>          mFragmentTitles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(SupportFragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public SupportFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}