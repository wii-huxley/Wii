package com.huxley.wii.wii.page.harmonicaNotation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.UIHelper;
import com.huxley.wii.wii.model.bean.Song;
import com.huxley.wii.yl.common.RecyclerViewHelper;
import com.huxley.wii.yl.common.utils.ToastHelper;
import com.huxley.wii.yl.common.utils.doubleClick.OnRvClickListener;
import com.huxley.wii.yl.common.utils.doubleClick.OnTbMenuItemClickListener;
import com.huxley.wii.yl.page.mvp.MvpSwipeBackFragment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    public static HarmonicaNotationFragment newInstance() {

        Bundle args = new Bundle();

        HarmonicaNotationFragment fragment = new HarmonicaNotationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.harmonica_notation_fragment;
    }

    @Override
    public void init() {
        super.init();

        UIHelper.Toolbars.setTitleAndMenu(mToolbar, "口琴简谱", R.menu.menu_main, new OnTbMenuItemClickListener() {
            @Override
            public boolean onTbMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_song:
                        UIHelper.StartActivity.editInfo(_mActivity);
                        break;
                    case R.id.action_logout:
                        break;
                }
                return true;
            }
        });
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
                UIHelper.StartActivity.editSong(_mActivity, mAdapter.getDatas().get(position).songId);
            }
        });
        mPresenter.loadSongList();
    }

    @Override
    public void loadSuccess(List<Song> songs) {
        mAdapter.getDatas().clear();
        if (songs == null || songs.isEmpty()) {
            ToastHelper.showInfo("没有查到曲谱");
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
}
