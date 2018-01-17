package com.huxley.page_guide.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.page_guide.R;
import com.huxley.page_guide.bean.GuideBean;
import com.huxley.yl.page.base.BaseFragment;

/**
 * Created by huxley on 2017/9/15.
 */

public class GuideSimpleFragment extends BaseFragment {

    GuideBean mGuideData;

    public static GuideSimpleFragment newInstance(GuideBean guideData) {
        Bundle args = new Bundle();
        args.putSerializable("guideData", guideData);
        GuideSimpleFragment fragment = new GuideSimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.guide_simple_fragment, container, false);
        init();
        return mRootView;
    }

    private void init() {
        mGuideData = (GuideBean) getArguments().getSerializable("guideData");
    }
}
