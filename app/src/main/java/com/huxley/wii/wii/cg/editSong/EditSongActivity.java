package com.huxley.wii.wii.cg.editSong;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.UIHelper;
import com.huxley.wii.wii.common.WiiConstant;
import com.huxley.wii.wii.model.bean.Beat;
import com.huxley.wii.wii.model.bean.Note;
import com.huxley.wii.wii.model.bean.Song;
import com.huxley.wii.wii.page.harmonicaNotation.HarmonicaNotationContract;
import com.huxley.wii.yl.basePage.BaseActivity;
import com.huxley.wii.yl.common.YlUIHelper;
import com.huxley.wii.yl.common.utils.ToastHelper;
import com.huxley.wii.yl.common.utils.date.DateUtil;
import com.huxley.wii.yl.common.utils.doubleClick.OnTbMenuItemClickListener;
import com.huxley.wii.yl.common.utils.doubleClick.OnYlClickListener;
import com.huxley.wii.yl.common.utils.doubleClick.OnYlKeyListener;
import com.huxley.wii.yl.page.mvp.Mvp;
import com.huxley.wii.yl.page.mvp.MvpActivity;
import com.orhanobut.dialogplus.GridHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑曲谱
 * Created by huxley on 17/2/4.
 */
public class EditSongActivity extends MvpActivity<EditSongContract.Present> implements EditSongContract.View {

    CommonAdapter<Beat>    mAdapter;
    Song                   mSong;
    int                    currentPosition;
    String                 mSongId;
    YlUIHelper.BaseDialogs mBaseDialogs;
    ProgressDialog         mProgressDialog;

    @BindView(R.id.toolbar)
    Toolbar      mToolbar;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;

    @Override
    public int getLayoutId() {
        return R.layout.edit_song_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case WiiConstant.Code.EDIT_1:
                Note note = WiiConstant.getNoteList().get(currentPosition).copy();
                note.noteWord = intent.getStringExtra(WiiConstant.Key.CONTENT);
                setNote(note);
                mAdapter.notifyDataSetChanged();
                break;
            case WiiConstant.Code.EDIT_INFO:
                Song song = (Song) intent.getSerializableExtra(WiiConstant.Key.SONG);
                mSong.fuse(song);
                UIHelper.Toolbars.setTitleBySong(mToolbar, mSong);
                break;
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (getIntent().hasExtra(WiiConstant.Key.SONG)) {
            mSong = (Song) getIntent().getSerializableExtra(WiiConstant.Key.SONG);
            if (mSong == null) {
                mSong = new Song();
            }
            if (mSong.beatList == null) {
                mSong.beatList = new ArrayList<>();
            }
        } else if (getIntent().hasExtra(WiiConstant.Key.ID)) {
            mSongId = getIntent().getStringExtra(WiiConstant.Key.ID);
            mSong = mPresenter.getSongInfoById(mSongId);
            if (mSong == null) {
                mSong = new Song();
            }
            if (mSong.beatList == null) {
                mSong.beatList = new ArrayList<>();
            }
        }

        ButterKnife.bind(this);
        UIHelper.Toolbars.setTitleAndBackAndMenuBySong(mToolbar, this, mSong, R.menu.menu_edit_song, new OnTbMenuItemClickListener() {
            @Override
            public boolean onTbMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        mSong.time = DateUtil.getCurrentTime(DateUtil.YMDHMS_1);
                        mPresenter.editSong(mSong);
                        break;
                }
                return true;
            }
        });
        mToolbar.setOnClickListener(new OnYlClickListener() {
            @Override
            public void onYlClick(View view) {
                UIHelper.StartActivity.editInfoForResult(EditSongActivity.this, mSong);
            }
        });
        mRvCategory.setAdapter(mAdapter = new CommonAdapter<Beat>(this, R.layout.edit_song_item_beat, mSong.beatList) {
            @Override
            protected void convert(ViewHolder holder, Beat beat, int position) {
                UIHelper.ViewHolders.setBeat(EditSongActivity.this, holder, beat);
            }
        });
        if (mSongId != null) {
            mPresenter.loadSongDetailById(mSongId);
        }
    }

    @OnClick(R.id.fab_add_beat)
    public void addBeat() {
        getBaseDialogs().getBottom(this, "编辑音符", new GridHolder(7), new com.zhy.adapter.abslistview.CommonAdapter<Note>(this, R.layout.edit_song_dialog_item_note, WiiConstant.getNoteList()) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, Note item, int position) {
                UIHelper.ViewHolders.setNote(viewHolder, item);
            }
        }, (dialog, item, view, position) -> {
            dialog.dismiss();
            Note note = (Note) item;
            switch (note.noteType) {
                case WiiConstant.NoteType.NEXT:
                    mSong.beatList.add(new Beat());
                    break;
                case WiiConstant.NoteType.DELETE_BEAT:
                    if (mSong.beatList.isEmpty()) {
                        ToastHelper.showInfo("没有更多音阶");
                        return;
                    }
                    mSong.beatList.remove(mSong.beatList.size() - 1);
                    break;
                case WiiConstant.NoteType.DELETE_NOTE:
                    if (mSong.beatList.get(mSong.beatList.size() - 1).noteList.isEmpty()) {
                        ToastHelper.showInfo("没有更多音符");
                        return;
                    }
                    mSong.beatList.get(mSong.beatList.size() - 1).noteList.remove(mSong.beatList.get(mSong.beatList.size() - 1).noteList.size() - 1);
                    break;
                default:
                    setNote(note);
                    break;
            }
            mAdapter.notifyDataSetChanged();
        }, (dialog, item, view, position) -> {
            Note note = (Note) item;
            StringBuilder builder = new StringBuilder();
            switch (note.noteType) {
                case WiiConstant.NoteType.LOW:
                    builder.append("低音").append(note.noteNum);
                    break;
                case WiiConstant.NoteType.NORMAL:
                    builder.append("中音").append(note.noteNum);
                    break;
                case WiiConstant.NoteType.HIGH:
                    builder.append("高音").append(note.noteNum);
                    break;
                default:
                    return false;
            }
            currentPosition = position;
            UIHelper.StartActivity.edit(EditSongActivity.this, builder.toString() + "的词", note.noteWord, 1, WiiConstant.Code.EDIT_1);
            dialog.dismiss();
            return true;
        }).show();
    }


    @Override
    public void addSuccess(Song song) {
        finish();
        ((HarmonicaNotationContract.Present) Mvp.getInstance().getPresenter(HarmonicaNotationContract.Present.class)).addSong(song);
    }

    @Override
    public void loadSongDetailSuccess(List<Beat> beatList) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(beatList);
        mAdapter.notifyDataSetChanged();
    }

    public void setNote(Note note) {
        if (mSong.beatList.isEmpty()) {
            Beat beat = new Beat();
            beat.noteList.add(note);
            mSong.beatList.add(beat);
        } else {
            mSong.beatList.get(mSong.beatList.size() - 1).noteList.add(note);
        }
    }


    public YlUIHelper.BaseDialogs getBaseDialogs() {
        if (mBaseDialogs == null) {
            mBaseDialogs = new YlUIHelper.BaseDialogs();
        }
        return mBaseDialogs;
    }


    /**
     * 显示加载提示
     *
     * @param message   提示信息
     * @param shieldKey 是否保护该提示
     */
    public void show(String message, boolean shieldKey) {
        if (mProgressDialog == null) {
            synchronized (BaseActivity.class) {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setMessage(message);
                    if (shieldKey) {
                        mProgressDialog.setOnKeyListener(new OnYlKeyListener() {
                            @Override
                            public boolean onYlKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                                return keyCode == KeyEvent.KEYCODE_BACK;
                            }
                        });
                    }
                }
            }
        } else {
            mProgressDialog.setMessage(message);
            if (shieldKey) {
                mProgressDialog.setOnKeyListener(new OnYlKeyListener() {
                    @Override
                    public boolean onYlKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                        return keyCode == KeyEvent.KEYCODE_BACK;
                    }
                });
            }
            if (mProgressDialog.isShowing()) {
                return;
            }
        }
        mProgressDialog.show();
    }

    /**
     * 销毁加载提示
     */
    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
