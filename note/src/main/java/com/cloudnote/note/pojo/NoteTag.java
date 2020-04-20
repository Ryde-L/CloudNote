package com.cloudnote.note.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTag {
    private Integer id;

    private Integer noteId;

    private String tag;

}