package com.huxley.wiisample.page.harmonicaNotation;

import com.huxley.wiisample.common.CallBack;
import com.huxley.wiisample.model.SongModel;
import com.huxley.wiisample.model.bean.Song;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;
import com.huxley.wiitools.view.WiiToast;

import java.util.List;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class HarmonicaNotationContract {

    public interface View extends IMvpView {

        void setRefreshing(boolean refreshing);

        void loadSuccess(List<Song> songs);

        void addSong(Song song);
    }

    public static class Present extends IMvpPresenter<View> {

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
                            WiiToast.error(errMsg);
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
