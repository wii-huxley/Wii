package com.huxley.wiisample.page.moban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class MobanFragment extends MvpSwipeBackFragment<MobanContract.Present> implements MobanContract.View {

    @BindView(R.id.toolbar)
    Toolbar            mToolbar;

    public static MobanFragment newInstance() {
        Bundle args = new Bundle();
        MobanFragment fragment = new MobanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.harmonica_notation_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return attachToSwipeBack(mRootView);
    }

    protected void init() {
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, "口琴简谱");
    }

    @Override
    protected MobanContract.Present getPresenter() {
        return new MobanContract.Present();
    }
}
