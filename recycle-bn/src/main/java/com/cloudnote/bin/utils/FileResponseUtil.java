package com.cloudnote.bin.utils;

import com.cloudnote.bin.dto.FileResponseDto;


public class FileResponseUtil {

    public static FileResponseDto success(String url) {
        return new FileResponseDto(0,url,"");
    }

    public static FileResponseDto error(String msg){ return new FileResponseDto(1,"", msg); }


}
