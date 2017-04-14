package com.huxley.wii.wii.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 歌曲
 * Created by huxley on 17/2/3.
 */
public class Song extends DataSupport implements Serializable {

    public int songId;
    /**
     * 曲谱名
     */
    public String songName;

    /**
     * 作曲者
     */
    public String composer;

    /**
     * 作词者
     */
    public String lyricist;

    /**
     * 调号
     */
    public String keySignature;

    /**
     * 拍号
     */
    public String timeSignature;

    public String time;

    public List<Beat> beatList;

    public Song() {
    }

    public Song(String songName, String composer, String lyricist, String keySignature, String timeSignature) {
        this();
        this.songName = songName;
        this.composer = composer;
        this.lyricist = lyricist;
        this.keySignature = keySignature;
        this.timeSignature = timeSignature;
    }

    @Override
    public String toString() {
        return songName;
    }

    public Song copy() {
        Song song = new Song();
        song.songName = this.songName;
        song.composer = this.composer;
        song.lyricist = this.lyricist;
        song.keySignature = this.keySignature;
        song.timeSignature = this.timeSignature;
        song.time = this.time;
        return song;
    }

    public void fuse(Song song) {
        this.songName = song.songName;
        this.composer = song.composer;
        this.lyricist = song.lyricist;
        this.keySignature = song.keySignature;
        this.timeSignature = song.timeSignature;
    }
}
