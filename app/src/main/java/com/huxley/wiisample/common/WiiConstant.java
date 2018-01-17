package com.huxley.wiisample.common;

import com.huxley.wiisample.model.bean.Note;
import com.huxley.yl.common.utils.BaseConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 17/1/14.
 */
public class WiiConstant extends BaseConstant {

    public static class GitHub {

        public static final String CLIENT_ID = "e41d32194f7c3d8ac311";
        public static final String CLIENT_SECRET
            = "fe3abe517b084f9e926e3061022761ba0e3891ed";
        public static final String[] SCOPES = { "user", "repo" };
        public static final String NOTE = "Wii工具箱";
    }


    private static List<Note> sNoteList;


    public static List<Note> getNoteList() {
        if (sNoteList == null) {
            sNoteList = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                Note note = new Note();
                note.noteNum = i % 7 + 1;
                note.noteType = i / 7;
                sNoteList.add(note);
            }
            Note separatorNote = new Note();
            separatorNote.noteType = NoteType.SEPARATOR;
            sNoteList.add(separatorNote);
            Note blankNote = new Note();
            blankNote.noteType = NoteType.BLANK;
            sNoteList.add(blankNote);
            Note semitoneNote = new Note();
            semitoneNote.noteType = NoteType.SEMITONE;
            sNoteList.add(semitoneNote);
            Note syllableNote = new Note();
            syllableNote.noteType = NoteType.SYLLABLE;
            sNoteList.add(syllableNote);
            Note nextNote = new Note();
            nextNote.noteType = NoteType.NEXT;
            sNoteList.add(nextNote);
            Note deleteBeatNote = new Note();
            deleteBeatNote.noteType = NoteType.DELETE_BEAT;
            sNoteList.add(deleteBeatNote);
            Note deleteNoteNote = new Note();
            deleteNoteNote.noteType = NoteType.DELETE_NOTE;
            sNoteList.add(deleteNoteNote);
        }
        return sNoteList;
    }


    public static class NoteType {
        /**
         * 低音
         */
        public static final int LOW = 0;
        /**
         * 中音
         */
        public static final int NORMAL = 1;
        /**
         * 高音
         */
        public static final int HIGH = 2;
        /**
         * 逗号
         */
        public static final int SEPARATOR = 3;
        /**
         * 空格
         */
        public static final int BLANK = 4;
        /**
         * 半个音阶
         */
        public static final int SEMITONE = 5;
        /**
         * 一个音阶
         */
        public static final int SYLLABLE = 6;
        /**
         * 下一节
         */
        public static final int NEXT = 7;
        /**
         * 删除音节
         */
        public static final int DELETE_BEAT = 8;
        /**
         * 删除音符
         */
        public static final int DELETE_NOTE = 9;
    }


    public static class Code {
        public static final int EDIT_INFO = 1001;
        public static final int EDIT_1 = 1002;
        public static final int EDIT_2 = 1003;
        public static final int EDIT_3 = 1004;
    }


    public static class Key {
        public static final String SONG = "song";
        public static final String CONTENT = "content";
        public static final String TITLE = "title";
        public static final String LENGTH = "length";
        public static final String BEAT_LIST = "beatList";
        public static final String POSITION = "position";
        public static final String ID = "id";
    }
}
