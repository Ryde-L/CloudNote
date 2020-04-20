package com.cloudnote.note.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String pwd;

    private Integer gender;

    private Integer status;

    private List<NoteBook> noteBookList;

}