package com.cloudnote.bin.utils;

import com.cloudnote.bin.dto.ResponseDto;

public class ResultUtil {

    public static ResponseDto success(String msg,Object data) {
        return new ResponseDto("200", msg, data, "1");
    }
    public static ResponseDto success(String msg) { return new ResponseDto("200", msg, null, "1"); }
    public static ResponseDto success(Object data) { return new ResponseDto("200", "OK", data, "1"); }
    public static ResponseDto success() { return new ResponseDto("200", "OK", null, "1"); }

    public static ResponseDto error(String msg,Object data){
        return new ResponseDto("200", msg, data, "0");
    }
    public static ResponseDto error(String code,String msg){ return new ResponseDto(code, msg, null, "0"); }
    public static ResponseDto error(String msg){ return new ResponseDto("200", msg, null, "0"); }


}
