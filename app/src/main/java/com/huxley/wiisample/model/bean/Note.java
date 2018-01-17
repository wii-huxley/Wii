package com.huxley.wiisample.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by huxley on 17/2/3.
 */
public class Note extends DataSupport implements Serializable {

    public int noteId;

    public int noteNum;

    public int noteType;

    public String noteWord;

    public Note copy() {
        Note note = new Note();
        note.noteNum = this.noteNum;
        note.noteType = this.noteType;
        return note;
    }
}
