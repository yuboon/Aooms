package net.aooms.core.web;

import net.aooms.core.dto.DTO;
import net.aooms.core.exception.AoomsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类
 * Created by cccyb on 2018-04-18
 */

@ControllerAdvice
public class AoomsExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 所有异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map ErrorHandler(Exception ex) {
        logger.error("AoomsError",ex);
        if(ex != null){
            DTO.me().getRet().failure();
        }
        return DTO.me().getRet().getData();
    }

    /**
     * 拦截捕捉自定义异常 AoomsException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AoomsException.class)
    public Map AoomsErrorHandler(AoomsException ex) {
        logger.error("AoomsError",ex);
        if(ex != null){
            DTO.me().getRet().failure();
        }
        return DTO.me().getRet().getData();
    }

}