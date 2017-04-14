package com.huxley.wii.wii.model;

import com.huxley.wii.wii.common.CallBack;
import com.huxley.wii.wii.model.bean.Beat;
import com.huxley.wii.wii.model.bean.Song;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 歌曲model
 * Created by huxley on 2017/3/1.
 */
public class SongModel {

    private static SongModel instance;

    private SongModel() {
    }

    public static SongModel getInstance() {
        if (instance == null) {
            synchronized (SongModel.class) {
                if (instance == null) {
                    instance = new SongModel();
                }
            }
        }
        return instance;
    }

    /**
     * 通过songId加载Song信息
     */
    public Song loadSongInfoById(String songId) {
        return DataSupport.where("songId = ?", songId).findFirst(Song.class);
    }

    /**
     * 编辑song
     */
    public void editSong(final Song song, final CallBack<Boolean> callBack) {
        Flowable.fromCallable(song::save).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::onSuccess, (Throwable t) -> callBack.onError("100", "加载失败"));
    }

    /**
     * 通过songId加载song详情
     */
    public void loadSongDetailById(final String songId, final CallBack<List<Beat>> callBack) {
        Flowable.fromCallable(() -> DataSupport.where("song_id = ?", String.valueOf(songId)).find(Beat.class, true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::onSuccess, (Throwable t) -> callBack.onError("100", "加载失败"));
    }

    /**
     * 加载歌曲列表
     */
    public void loadLocalSongList(final CallBack<List<Song>> callBack) {
        Flowable.fromCallable(() -> DataSupport.order("time desc").find(Song.class, false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::onSuccess, (Throwable t) -> callBack.onError("100", "加载失败"));
    }
}