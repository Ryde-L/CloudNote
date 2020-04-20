package com.cloudnote.note.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteAttachment {
    private Integer id;

    private Integer noteId;

    private String name;

    private Integer type;

    private String postfix;

    private Float size;

    private String uri;

    private Integer status;

}