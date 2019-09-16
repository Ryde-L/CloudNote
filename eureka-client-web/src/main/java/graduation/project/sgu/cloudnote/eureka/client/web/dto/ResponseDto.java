package graduation.project.sgu.cloudnote.eureka.client.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    //错误码
    private String code;
    //提示信息
    private String msg;
    //具体的内容
    private T data;
    //成功与否 0或1
    private String isSuccessful;

//    public ResponseDto(String code, String msg,T data,String isSuccessful) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//        this.isSuccessful = isSuccessful;
//    }


}
