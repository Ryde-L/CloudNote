package graduation.project.sgu.cloudnote.eureka.client.web.utils;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.FileResponseDto;


public class FileResponseUtil {

    public static FileResponseDto success(String url) {
        return new FileResponseDto(0,url,"");
    }

    public static FileResponseDto error(String msg){ return new FileResponseDto(1,"", msg); }


}
