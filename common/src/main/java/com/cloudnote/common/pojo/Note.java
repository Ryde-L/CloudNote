package com.cloudnote.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note implements Serializable {
    private Integer id;

    private Integer noteBookId;

    private String title;

    private Integer isHasPwd;

    private String pwd;

    private NoteBook noteBook;

    private String content;

    private List<NoteTag> noteTagList;

    public Note(Integer id, Integer noteBookId, String title, Integer isHasPwd, String pwd) {
        this.id = id;
        this.noteBookId = noteBookId;
        this.title = title;
        this.isHasPwd = isHasPwd;
        this.pwd = pwd;
    }
}