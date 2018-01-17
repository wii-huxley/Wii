package com.huxley.wiisample.page.timeRhythm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiitools.utils.logger.Logger;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class TimeRhythmFragment extends MvpSwipeBackFragment<TimeRhythmContract.Present>
    implements TimeRhythmContract.View {

    @BindView(R.id.toolbar)    Toolbar            mToolbar;
    @BindView(R.id.tr)         TimeRhythmView     mTr;
    @BindView(R.id.rv_song)    RecyclerView       mRvSong;
    @BindView(R.id.srl)        SwipeRefreshLayout mSrl;
    @BindView(R.id.ll_content) LinearLayout       mLlContent;


    public static TimeRhythmFragment newInstance() {
        Bundle             args     = new Bundle();
        TimeRhythmFragment fragment = new TimeRhythmFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.time_rhythm_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return attachToSwipeBack(mRootView);
    }


    protected void init() {
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, "时间节奏");
    }


    @Override
    protected TimeRhythmContract.Present getPresenter() {
        return new TimeRhythmContract.Present();
    }


    @OnClick({ R.id.btn_num, R.id.btn_start, R.id.btn_reset })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_num:
                mTr.showCycleTimesDialog();
                break;
            case R.id.btn_start:
                Logger.i("btn_start");
                mTr.start();
                break;
            case R.id.btn_reset:
                break;
        }
    }
}
