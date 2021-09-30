package com.wenhui.health.controller;

import com.wenhui.health.entity.Result;
import com.wenhui.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 邓文辉
 * @date 2021/9/26
 */
@RestControllerAdvice
public class HealExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);
    /**
     * catch(MyException)
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result healExceptionAdvice(MyException e) {
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result healExceptionAdvice(Exception e) {
        log.error("发生未知异常",e);
        return new Result(false,"发生未知异常，请稍后再试！");
    }
}
