package com.huxley.wii.wii.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huxley on 2017/2/11.
 */
public class Beat extends DataSupport implements Serializable{

    public int beatId;

    public List<Note> noteList;

    public Beat() {
    }

    public List<Note> getNoteList() {
        return DataSupport.where("song_id = ?", String.valueOf(beatId)).find(Note.class);
    }
}
