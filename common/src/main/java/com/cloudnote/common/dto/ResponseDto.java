package com.cloudnote.common.dto;



public class ResponseDto<T> {

    //错误码
    private String code;
    //跳转url
    private String url;
    //提示信息
    private String msg;
    //具体的内容
    private T data;
    //成功与否 0或1
    private String isSuccessful;

    public ResponseDto() {
    }

    public ResponseDto(String code, String url, String msg, T data, String isSuccessful) {
        this.code = code;
        this.url = url;
        this.msg = msg;
        this.data = data;
        this.isSuccessful = isSuccessful;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(String isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
