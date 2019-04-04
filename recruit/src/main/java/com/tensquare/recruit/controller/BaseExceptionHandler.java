package com.tensquare.recruit.controller;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ztq161024zsc
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 异常的处理
     * @param e 打印异常信息
     * @return 返回值
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();        
        return new Result( StatusCode.ERROR, "执行出错",false);
    }
}
