package net.lishen.exeception;

import lombok.extern.slf4j.Slf4j;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.exeception
 * @Project：gpcloud-meter
 * @name：CustomExceptionHandler
 * @Date：2023-12-29 9:22
 * @Filename：CustomExceptionHandler
 */
@ControllerAdvice
//@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){

        if(e instanceof BizException bizException){
            log.error("[业务异常]{}",e);
            return JsonData.buildCodeAndMsg(bizException.getCode(),bizException.getMsg());
        }else {
            log.error("[系统异常]{}",e);
            return JsonData.buildError("系统异常");
        }

    }

}