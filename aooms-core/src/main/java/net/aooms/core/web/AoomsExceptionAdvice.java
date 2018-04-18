package net.aooms.core.web;

import net.aooms.core.exception.AoomsException;
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

    /**
     * 所有异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", ex.getMessage());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 AoomsException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AoomsException.class)
    public Map myErrorHandler(AoomsException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMessage());
        return map;
    }

}