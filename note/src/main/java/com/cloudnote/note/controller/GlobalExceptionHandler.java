package com.cloudnote.note.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDto handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        System.out.println("找不到参数");
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.BAD_REQUEST.toString(),"部分参数可能缺少，请检查参数");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        System.out.println("抛出数据绑定异常");
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.BAD_REQUEST.toString(),"参数绑定异常，请检查参数格式");
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDto handleNumberFormatException(NumberFormatException e){
        System.out.println("参数格式转换异常");
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.BAD_REQUEST.toString(),"参数格式转换异常，请检查参数格式");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseDto handleNullPointerException(NullPointerException e){
        System.out.println("抛出空指针异常");
        logger.error("发生空指针异常："+e.getMessage());
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseDto handleIOException(IOException e){
        System.out.println("抛出IO异常");
        logger.error("发生IO异常："+e.getMessage());
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseDto handleCannotCreateTransactionException(IOException e){
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"数据库连接超时");
    }


    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDto handleNotLoginException(NotLoginException e){
        return ResultUtil.error(HttpStatus.BAD_REQUEST.toString(),"当前会话未登录");
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDto handleNotPermissionException(NotPermissionException e){
        System.out.println(e.getMessage());
        return ResultUtil.error(HttpStatus.BAD_REQUEST.toString(),"无权操作");
    }

    @ExceptionHandler(Exception.class) //表示让Spring捕获到所有抛出的SignException异常，并交由这个被注解的方法处理。
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  //表示设置状态码。
    ResponseDto handleException(Exception e){
        System.out.println("抛出异常");
        logger.error("发生异常："+e.getMessage());
        e.printStackTrace();
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
    }


}
