package com.cloudnote.notebook.utils;

import com.cloudnote.notebook.dto.FileResponseDto;


public class FileResponseUtil {

    public static FileResponseDto success(String url) {
        return new FileResponseDto(0,url,"");
    }

    public static FileResponseDto error(String msg){ return new FileResponseDto(1,"", msg); }


}
