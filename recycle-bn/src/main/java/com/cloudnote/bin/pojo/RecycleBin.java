package com.cloudnote.bin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecycleBin {
    private Integer id;

    private Integer userId;

    private Integer noteId;

    private Integer noteBookId;

    private String noteTitle;

    private Date throwAwayTime;

    private Date clearTime;

}