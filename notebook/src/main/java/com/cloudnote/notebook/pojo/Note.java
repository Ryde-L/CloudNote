package com.cloudnote.notebook.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Integer id;

    private Integer noteBookId;

    private String title;

    private Integer isHasPwd;

    private String pwd;

    private Integer isHasRemind;

    private Date remind;

    private NoteBook noteBook;

    private String content;

    private List<NoteTag> noteTagList;

    public Note(Integer id, Integer noteBookId, String title, Integer isHasPwd, String pwd, Integer isHasRemind, Date remind) {
        this.id = id;
        this.noteBookId = noteBookId;
        this.title = title;
        this.isHasPwd = isHasPwd;
        this.pwd = pwd;
        this.isHasRemind = isHasRemind;
        this.remind = remind;
    }
}