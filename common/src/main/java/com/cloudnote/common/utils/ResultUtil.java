package com.cloudnote.common.utils;


import com.cloudnote.common.dto.ResponseDto;

public class ResultUtil<T> {

    public static<T> ResponseDto<T> success(String msg, T data) {
        return new ResponseDto<T>("200","", msg, data, "1");
    }
    public static<T> ResponseDto<T> success(String code,String url) { return new ResponseDto<T>(code,url, "", null, "1"); }
    public static<T> ResponseDto<T> success(String msg) { return new ResponseDto<T>("200","", msg, null, "1"); }
    public static<T> ResponseDto<T> success() { return new ResponseDto<T>("200", "","", null, "1"); }

    public static<T> ResponseDto<T> error(String msg,T data){
        return new ResponseDto<T>("200","", msg, data, "0");
    }
    public static<T> ResponseDto<T> error(String code,String url,String msg){ return new ResponseDto<T>(code,url, msg, null, "0"); }
    public static<T> ResponseDto<T> error(String code,String msg){ return new ResponseDto<T>(code,"", msg, null, "0"); }
    public static<T> ResponseDto<T> error(String msg){ return new ResponseDto<T>("200", "",msg, null, "0"); }



}
