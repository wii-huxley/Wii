package com.huxley.wiisample.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huxley.fragmentation.base.SupportFragment;
import com.huxley.page_login.LoginActivity;
import com.huxley.wiisample.R;
import com.huxley.wiisample.model.bean.Beat;
import com.huxley.wiisample.model.bean.Note;
import com.huxley.wiisample.model.bean.Song;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.utils.StringUtils;
import com.huxley.yl.common.YlUIHelper;
import com.huxley.yl.common.utils.doubleClick.OnYlClickListener;

import java.text.MessageFormat;

/**
 * Created by huxley on 17/2/2.
 */
public class UIHelper extends YlUIHelper {

    public static class StartActivity {

        public static void login(final Activity activity) {
            activity.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                }
            });
        }
//
//        public static void editInfoForResult(@NonNull Activity activity, Song song) {
//            Intent intent = new Intent(activity, EditInfoActivity.class);
//            intent.putExtra(WiiConstant.Key.SONG, song.copy());
//            activity.startActivityForResult(intent, WiiConstant.Code.EDIT_INFO);
//        }
//        public static void editInfo(@NonNull Activity activity) {
//            activity.startActivity(new Intent(activity, EditInfoActivity.class));
//        }
//
//        public static void edit(@NonNull Activity activity, String title, String content, int code) {
//            Intent intent = new Intent(activity, EditTextActivity.class);
//            intent.putExtra(WiiConstant.Key.CONTENT, content);
//            intent.putExtra(WiiConstant.Key.TITLE, title);
//            activity.startActivityForResult(intent, code);
//        }
//
//        public static void edit(@NonNull Activity activity, String title, String content, int length, int code) {
//            Intent intent = new Intent(activity, EditTextActivity.class);
//            intent.putExtra(WiiConstant.Key.CONTENT, content);
//            intent.putExtra(WiiConstant.Key.LENGTH, length);
//            intent.putExtra(WiiConstant.Key.TITLE, title);
//            activity.startActivityForResult(intent, code);
//        }
//
//        public static void editSong(@NonNull Activity activity, Song song) {
//            Intent intent = new Intent(activity, EditSongActivity.class);
//            intent.putExtra(WiiConstant.Key.SONG, song.copy());
//            activity.startActivity(intent);
//        }
//
//        public static void editSong(@NonNull Activity activity, int songId) {
//            Intent intent = new Intent(activity, EditSongActivity.class);
//            intent.putExtra(WiiConstant.Key.ID, songId);
//            activity.startActivity(intent);
//        }
    }

    public static class OtherView extends BaseOtherView{

    }

    public static class Toolbars extends BaseToolbars{



        public static void setTitleAndBackAndMenu(@NonNull Toolbar toolbar, @NonNull final SupportFragment fragment, String title, @MenuRes int menuId, Toolbar.OnMenuItemClickListener listener) {
            fragment.setHasOptionsMenu(true);
            setMenu(toolbar, menuId, listener);
            setTitle(toolbar, (AppCompatActivity) fragment.getActivity(), title);
        }

        public static void setTitleAndBack(@NonNull Toolbar toolbar, @NonNull final SupportFragment fragment, String title) {
            setTitle(toolbar, (AppCompatActivity) fragment.getActivity(), title);
            setBack(toolbar, fragment);
        }


        public static void setBack(@NonNull Toolbar toolbar, @NonNull final SupportFragment fragment) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            toolbar.setNavigationOnClickListener(new OnYlClickListener() {
                @Override
                public void onYlClick(View view) {
                    fragment.pop();
                }
            });
        }

        public static void setTitleAndBackAndMenuBySong(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity, Song song, @MenuRes int menuId, Toolbar.OnMenuItemClickListener listener) {
            setMenu(toolbar, menuId, listener);
            setTitleBySong(toolbar, song);
            setBack(toolbar, activity);
        }

        public static void setTitleBySong(@NonNull Toolbar toolbar, Song song) {
            StringBuilder subtitle = new StringBuilder();
            subtitle.append(song.composer);
            if (!StringUtils.isEmpty(song.lyricist)) {
                subtitle.append(MessageFormat.format("（{0}）", song.lyricist));
            }
            if (!StringUtils.isEmpty(song.keySignature)) {
                subtitle.append(" ").append(song.keySignature);
            }
            if (!StringUtils.isEmpty(song.timeSignature)) {
                subtitle.append(" ").append(song.timeSignature);
            }
            toolbar.setTitle(song.songName);
            toolbar.setSubtitle(subtitle);
        }
    }

    public static class ViewHolders {

        public static void setBeat(Context context, ViewHolder holder, Beat beat) {
            LinearLayout view = holder.getView(R.id.ll_beats);
            view.removeAllViews();
            for (int i = 0; i < beat.noteList.size(); i++) {
                Note note = beat.noteList.get(i);
                View childView = UIHelper.getView(context, R.layout.edit_song_item_beat_child_view);
                ViewHolder childHolder = new ViewHolder(context, childView);
                setNote(childHolder, note);
                switch (note.noteType) {
                    case WiiConstant.NoteType.SEMITONE:
                    case WiiConstant.NoteType.SYLLABLE:
                        view.addView(childView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        break;
                    default:
                        view.addView(childView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        break;
                }
            }
        }

        public static void setNote(com.huxley.wiitools.commAdapter.adapter.ViewHolder holder, Note note) {
            switch (note.noteType) {
                case WiiConstant.NoteType.HIGH:
                    holder.getView(R.id.iv_high).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
                    break;
                case WiiConstant.NoteType.LOW:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
                    break;
                case WiiConstant.NoteType.NORMAL:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
                    break;
                case WiiConstant.NoteType.SEPARATOR:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_word, "，");
                    return;
                case WiiConstant.NoteType.BLANK:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.INVISIBLE);
                    break;
                case WiiConstant.NoteType.SEMITONE:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.getView(R.id.tv_word).setVisibility(View.INVISIBLE);
                    break;
                case WiiConstant.NoteType.SYLLABLE:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_word).setVisibility(View.INVISIBLE);
                    break;
            }
            if (note.noteNum > 0) {
                holder.setText(R.id.tv_note, String.valueOf(note.noteNum));
            }
            if (note.noteWord != null) {
                holder.setText(R.id.tv_word, note.noteWord);
            } else {
                holder.setText(R.id.tv_word, "");
            }
        }

        public static void setNote(ViewHolder holder, Note note) {
            switch (note.noteType) {
                case WiiConstant.NoteType.HIGH:
                    holder.getView(R.id.iv_high).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    break;
                case WiiConstant.NoteType.LOW:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    break;
                case WiiConstant.NoteType.NORMAL:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    break;
                case WiiConstant.NoteType.SEPARATOR:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.setText(R.id.tv_note, "，");
                    return;
                case WiiConstant.NoteType.BLANK:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.setText(R.id.tv_note, "空");
                    break;
                case WiiConstant.NoteType.SEMITONE:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    break;
                case WiiConstant.NoteType.SYLLABLE:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.VISIBLE);
                    break;
                case WiiConstant.NoteType.NEXT:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.setText(R.id.tv_note, "下");
                    break;
                case WiiConstant.NoteType.DELETE_BEAT:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.setText(R.id.tv_note, "删节");
                    break;
                case WiiConstant.NoteType.DELETE_NOTE:
                    holder.getView(R.id.iv_high).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.tv_note).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_low).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_semitone).setVisibility(View.GONE);
                    holder.getView(R.id.iv_other).setVisibility(View.GONE);
                    holder.setText(R.id.tv_note, "删音");
                    break;
            }
            if (note.noteNum > 0) {
                holder.setText(R.id.tv_note, String.valueOf(note.noteNum));
            }
        }

        public static void setSong(ViewHolder holder, Song song) {
            String key, songInfo;
            if (!StringUtils.isEmpty(song.keySignature) && !StringUtils.isEmpty(song.timeSignature)) {
                key = MessageFormat.format("{0} {1}", song.keySignature, song.timeSignature);
            } else if (!StringUtils.isEmpty(song.keySignature)) {
                key = song.keySignature;
            } else if (!StringUtils.isEmpty(song.timeSignature)) {
                key = song.timeSignature;
            } else {
                key = "";
            }
            if (!StringUtils.isEmpty(song.lyricist)) {
                songInfo = MessageFormat.format("{0}（{1}）", song.composer, song.lyricist);
            } else {
                songInfo = song.composer;
            }
            holder.setText(R.id.tv_song_name, song.songName);
            holder.setText(R.id.tv_key, key);
            holder.setText(R.id.tv_song_info, songInfo);
            holder.setText(R.id.tv_time, song.time);
        }
    }
}
