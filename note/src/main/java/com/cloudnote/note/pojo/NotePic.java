package com.cloudnote.note.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotePic {
    private Integer id;

    private Integer noteId;

    private String name;

    private String postfix;

    private Float size;

    private String uri;

    private Integer status;

}