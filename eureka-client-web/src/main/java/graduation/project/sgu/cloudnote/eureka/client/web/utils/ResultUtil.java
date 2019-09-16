package graduation.project.sgu.cloudnote.eureka.client.web.utils;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;

public class ResultUtil {

    public static ResponseDto success(String msg,Object data) {
        return new ResponseDto("200", "操作成功", data, "1");
    }

    public static ResponseDto error(String code,String msg,Object data){
        return new ResponseDto(code, msg, data, "0");
    }


}
