package com.huxley.wii.wii.cg.editSong;

import com.huxley.wii.wii.common.CallBack;
import com.huxley.wii.wii.model.SongModel;
import com.huxley.wii.wii.model.bean.Beat;
import com.huxley.wii.wii.model.bean.Song;
import com.huxley.wii.yl.page.IProgressDialogView;
import com.huxley.wii.yl.page.mvp.MvpPresenter;
import com.huxley.wii.yl.page.mvp.MvpView;
import com.huxley.wii.yl.common.utils.ToastHelper;

import java.util.List;

/**
 *
 * Created by huxley on 17/1/30.
 */
public class EditSongContract {

    public interface View extends MvpView, IProgressDialogView {

        void addSuccess(Song song);

        void loadSongDetailSuccess(List<Beat> beatList);
    }

    public static class Present extends MvpPresenter<View> {

        public void editSong(final Song song) {
            if (mViewRef != null) {
                getView().show("添加中", false);
                SongModel.getInstance().editSong(song, new CallBack<Boolean> (){
                    @Override
                    public void onSuccess(Boolean result) {
                        if (mViewRef != null) {
                            getView().dismiss();
                            if (result) {
                                ToastHelper.showInfo("添加成功");
                                getView().addSuccess(song);
                            } else {
                                ToastHelper.showInfo("添加失败");
                            }
                        }
                    }

                    @Override
                    public void onError(String errCode, String errMsg) {
                        if (mViewRef != null) {
                            getView().dismiss();
                            ToastHelper.showInfo(errMsg);
                        }
                    }
                });
            }
        }

        public Song getSongInfoById(String songId) {
            if (songId == null) {
                ToastHelper.showInfo("没有查到该信息");
                return null;
            }
            return SongModel.getInstance().loadSongInfoById(songId);
        }

        public void loadSongDetailById(String songId) {

            if (mViewRef != null) {
                getView().show("加载中", false);
                SongModel.getInstance().loadSongDetailById(songId, new CallBack<List<Beat>>(){
                    @Override
                    public void onSuccess(List<Beat> result) {
                        if (mViewRef != null) {
                            getView().dismiss();
                            getView().loadSongDetailSuccess(result);
                        }
                    }

                    @Override
                    public void onError(String errCode, String errMsg) {
                        if (mViewRef != null) {
                            getView().dismiss();
                            ToastHelper.showInfo(errMsg);
                        }
                    }
                });
            }
        }
    }
}
