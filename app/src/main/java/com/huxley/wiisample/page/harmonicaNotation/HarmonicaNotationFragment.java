package com.huxley.wiisample.page.harmonicaNotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.bean.Song;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.common.RecyclerViewHelper;
import com.huxley.yl.common.utils.doubleClick.OnRvClickListener;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class HarmonicaNotationFragment extends MvpSwipeBackFragment<HarmonicaNotationContract.Present> implements HarmonicaNotationContract.View {

    CommonAdapter<Song> mAdapter;

    @BindView(R.id.toolbar)
    Toolbar            mToolbar;
    @BindView(R.id.rv_song)
    RecyclerView       mRvSong;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.ll_content)
    LinearLayout       mLlContent;

    public static HarmonicaNotationFragment newInstance() {
        Bundle args = new Bundle();
        HarmonicaNotationFragment fragment = new HarmonicaNotationFragment();
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
//        mHandler = UIHelper.OtherView.setSwipeRefreshLayout(mSrl);
        RecyclerViewHelper.setList(mRvSong, mAdapter = new CommonAdapter<Song>(_mActivity, R.layout.harmonica_notation_item_song, new ArrayList<>()) {
            @Override
            protected void convert(ViewHolder holder, Song song, int position) {
                UIHelper.ViewHolders.setSong(holder, song);
            }
        }, _mActivity);
        mAdapter.setOnItemClickListener(new OnRvClickListener() {
            @Override
            public void onRvItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                UIHelper.StartActivity.editSong(_mActivity, mAdapter.getDatas().get(position).songId);
            }
        });
        mPresenter.loadSongList();
    }

    @Override
    public void loadSuccess(List<Song> songs) {
        mAdapter.getDatas().clear();
        if (songs == null || songs.isEmpty()) {
            WiiToast.warn("没有查到曲谱");
        } else {
            mAdapter.getDatas().addAll(songs);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addSong(Song song) {
        mAdapter.getDatas().add(0, song);
        mAdapter.notifyDataSetChanged();
    }

    public void setRefreshing(boolean refreshing) {
//        setRefreshing(refreshing, mSrl);
    }

    @Override
    protected HarmonicaNotationContract.Present getPresenter() {
        return new HarmonicaNotationContract.Present();
    }
}
