package com.huxley.wii.wii.page.harmonicaNotation;

import com.huxley.wii.wii.common.CallBack;
import com.huxley.wii.wii.model.SongModel;
import com.huxley.wii.wii.model.bean.Song;
import com.huxley.wii.yl.page.mvp.MvpPresenter;
import com.huxley.wii.yl.page.mvp.MvpView;
import com.huxley.wii.yl.common.utils.ToastHelper;

import java.util.List;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class HarmonicaNotationContract {

    public interface View extends MvpView {

        void setRefreshing(boolean refreshing);

        void loadSuccess(List<Song> songs);

        void addSong(Song song);
    }

    public static class Present extends MvpPresenter<View> {

        public void loadSongList() {
            if (mViewRef != null) {
                getView().setRefreshing(true);
                SongModel.getInstance().loadLocalSongList(new CallBack<List<Song>>() {
                    @Override
                    public void onSuccess(List<Song> result) {
                        if (mViewRef != null) {
                            getView().setRefreshing(false);
                            getView().loadSuccess(result);
                        }
                    }

                    @Override
                    public void onError(String errCode, String errMsg) {
                        if (mViewRef != null) {
                            getView().setRefreshing(false);
                            ToastHelper.showInfo(errMsg);
                        }
                    }
                });
            }
        }

        public void addSong(Song song) {
            if (mViewRef != null) {
                getView().addSong(song);
            }
        }
    }
}
