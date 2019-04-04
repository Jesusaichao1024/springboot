package com.sureal.exception;

import entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import entity.StatusCode;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 11:35
 * @PackageName com.sureal.exception
 * @Project_Name tensquare_parent
 * @Description 统一异常处理
 */
//@ControllerAdvice
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
   // @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(StatusCode.ERROR, e.getMessage(), false);
    }

}
