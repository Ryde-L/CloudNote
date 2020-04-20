package com.cloudnote.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {
    private int error;
    private String url;
    private String msg;
}
