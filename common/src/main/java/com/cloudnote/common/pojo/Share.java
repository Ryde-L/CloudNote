package com.cloudnote.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    private Integer id;

    private Integer userId;

    private Integer noteId;

    private String link;

    private Integer isHasPwd;

    private String pwd;

    private Integer limitType;

    private Integer limitContent;

    private Date createTime;

    private Integer status;

}