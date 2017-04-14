package com.huxley.wii.wii.cg.editInfo;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.UIHelper;
import com.huxley.wii.wii.common.WiiConstant;
import com.huxley.wii.wii.model.bean.Song;
import com.huxley.wii.yl.basePage.mvp.MvpActivity;
import com.huxley.wii.yl.common.utils.ResUtil;
import com.huxley.wii.yl.common.utils.StringUtils;
import com.huxley.wii.yl.common.utils.ToastHelper;
import com.huxley.wii.yl.common.utils.doubleClick.OnTbMenuItemClickListener;
import com.orhanobut.dialogplus.ListHolder;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑歌曲信息
 * Created by huxley on 17/1/14.
 */
public class EditInfoActivity extends MvpActivity<EditInfoContract.Present> implements EditInfoContract.View {

    Song mSong;

    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.tv_song_name)
    TextView mTvSongName;
    @BindView(R.id.tv_composer)
    TextView mTvComposer;
    @BindView(R.id.tv_lyricist)
    TextView mTvLyricist;
    @BindView(R.id.tv_key_signature)
    TextView mTvKeySignature;
    @BindView(R.id.tv_time_signature)
    TextView mTvTimeSignature;

    @Override
    public int getLayoutId() {
        return R.layout.edit_info_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case WiiConstant.Code.EDIT_1:
                mTvSongName.setText(data.getStringExtra(WiiConstant.Key.CONTENT));
                break;
            case WiiConstant.Code.EDIT_2:
                mTvComposer.setText(data.getStringExtra(WiiConstant.Key.CONTENT));
                break;
            case WiiConstant.Code.EDIT_3:
                mTvLyricist.setText(data.getStringExtra(WiiConstant.Key.CONTENT));
                break;
        }
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);
        if (intent.hasExtra(WiiConstant.Key.SONG)) {
            mSong = (Song) intent.getSerializableExtra(WiiConstant.Key.SONG);
        }
        if (mSong != null) {
            UIHelper.setTextView(mTvSongName, mSong.songName);
            UIHelper.setTextView(mTvComposer, mSong.composer);
            UIHelper.setTextView(mTvLyricist, mSong.lyricist);
            UIHelper.setTextView(mTvKeySignature, mSong.keySignature);
            UIHelper.setTextView(mTvTimeSignature, mSong.timeSignature);
        }
        UIHelper.Toolbars.setTitleAndBackAndMenu(mToolbar, this, "编辑曲谱信息", mSong != null ?
                R.menu.menu_edit_info_1 : R.menu.menu_edit_info, new OnTbMenuItemClickListener() {
            @Override
            public boolean onTbMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_next:
                        next();
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.ll_song_name, R.id.ll_composer, R.id.ll_lyricist, R.id.ll_key_signature, R.id.ll_time_signature})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_song_name:
                UIHelper.StartActivity.edit(EditInfoActivity.this, "曲谱名", mTvSongName.getText().toString(), WiiConstant.Code.EDIT_1);
                break;
            case R.id.ll_composer:
                UIHelper.StartActivity.edit(EditInfoActivity.this, "作曲", mTvComposer.getText().toString(), WiiConstant.Code.EDIT_2);
                break;
            case R.id.ll_lyricist:
                UIHelper.StartActivity.edit(EditInfoActivity.this, "作词", mTvLyricist.getText().toString(), WiiConstant.Code.EDIT_3);
                break;
            case R.id.ll_key_signature:
                editKeySignature();
                break;
            case R.id.ll_time_signature:
                editTimeSignature();
                break;
        }
    }

    /**
     * 编辑拍号
     */
    private void editTimeSignature() {
        getBaseDialogs().getBottom(EditInfoActivity.this, "编辑拍号", new ListHolder(), new CommonAdapter<String>(EditInfoActivity.this,
                R.layout.edit_info_dialog_item, ResUtil.getStrAry(R.array.array_time_signatures)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv, item);
            }
        }, (dialog, item, view12, position) -> {
            mTvTimeSignature.setText((CharSequence) item);
            dialog.dismiss();
        }).show();
    }

    /**
     * 编辑调号
     */
    private void editKeySignature() {
        getBaseDialogs().getBottom(EditInfoActivity.this, "编辑调号", new ListHolder(), new CommonAdapter<String>(EditInfoActivity.this,
                R.layout.edit_info_dialog_item, ResUtil.getStrAry(R.array.array_key_signatures)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv, item);
            }
        }, (dialog, item, view1, position) -> {
            mTvKeySignature.setText((CharSequence) item);
            dialog.dismiss();
        }).show();
    }

    private void next() {
        String songName = mTvSongName.getText().toString();
        String composer = mTvComposer.getText().toString();
        String lyricist = mTvLyricist.getText().toString();
        String keySignature = mTvKeySignature.getText().toString();
        String timeSignature = mTvTimeSignature.getText().toString();
        if (StringUtils.isEmpty(songName)) {
            ToastHelper.showInfo("曲谱名为空");
            return;
        }
        if (StringUtils.isEmpty(composer)) {
            ToastHelper.showInfo("作曲为空");
            return;
        }
        if (mSong != null) {
            Intent intent = new Intent();
            mSong = new Song(songName, composer, lyricist, keySignature, timeSignature);
            intent.putExtra(WiiConstant.Key.SONG, mSong);
            setResult(RESULT_OK, intent);
        } else {
            Song song = new Song(songName, composer, lyricist, keySignature, timeSignature);
            UIHelper.StartActivity.editSong(this, song);
        }
        finish();
    }
}
