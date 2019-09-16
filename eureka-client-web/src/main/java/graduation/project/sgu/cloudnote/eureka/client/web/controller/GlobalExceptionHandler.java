package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.NoTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        System.out.println("抛出数据绑定异常");
        return ResultUtil.error("500","参数绑定异常",null);
    }

    @ExceptionHandler(NumberFormatException.class)
    ResponseDto handleNumberFormatException(NumberFormatException e){
        System.out.println("抛出格式转换异常");
        return ResultUtil.error("500",e.getMessage(),null);
    }

    @ExceptionHandler(NullPointerException.class)
    ResponseDto handleNullPointerException(NullPointerException e){
        System.out.println("抛出空指针异常");
        logger.error("发生空指针异常："+e.getCause());
        e.printStackTrace();
        return ResultUtil.error("500",e.getMessage(),null);
    }


    @ExceptionHandler(NoTransactionException.class)
    ResponseDto handleNoTransactionException(NoTransactionException e){
        System.out.println("没有事务需要回滚");
        return ResultUtil.error("500","参数绑定异常",null);
    }

    @ExceptionHandler(Exception.class) //表示让Spring捕获到所有抛出的SignException异常，并交由这个被注解的方法处理。
//    @ResponseStatus(HttpStatus.BAD_REQUEST)  //表示设置状态码。
    ResponseDto handleException(Exception e){
        System.out.println("抛出异常");
        logger.error("发生异常："+e.getMessage());
        e.printStackTrace();
        return ResultUtil.error("500",e.getMessage(),null);

    }
}
