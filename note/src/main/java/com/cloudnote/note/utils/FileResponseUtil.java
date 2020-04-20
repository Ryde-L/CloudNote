package com.cloudnote.note.utils;

import com.cloudnote.note.dto.FileResponseDto;


public class FileResponseUtil {

    public static FileResponseDto success(String url) {
        return new FileResponseDto(0,url,"");
    }

    public static FileResponseDto error(String msg){ return new FileResponseDto(1,"", msg); }


}
