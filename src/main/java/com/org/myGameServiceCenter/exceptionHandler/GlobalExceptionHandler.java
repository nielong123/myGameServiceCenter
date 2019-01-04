package com.org.myGameServiceCenter.exceptionHandler;


import com.org.myGameServiceCenter.base.BaseRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**********
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseRequestModel exceptionHandler(Exception exception) {
        try {
            log.error("exception", exception);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return BaseRequestModel.makeExceptionRequestModel(exception.getMessage());
    }

}
