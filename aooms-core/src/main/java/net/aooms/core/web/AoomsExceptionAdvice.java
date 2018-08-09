package net.aooms.core.web;

import com.alibaba.fastjson.JSON;
import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTORet;
import net.aooms.core.exception.AoomsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
     * 错误输出
     * @param ex
     */
    private void errorPrint(Exception ex){
        try {
            DTORet ret = new DTORet();
            if(ex != null){
                String error = ex.getMessage();
                if(error == null){
                    error = ex.toString();
                }
                ret.failure(error);
                logger.error("AoomsError",ex);
            }

            PrintWriter writer = AoomsContextHolder.getResponse().getWriter();
            AoomsContextHolder.getResponse().setContentType("application/json");
            writer.write(JSON.toJSONString(ret.getData()));
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("AoomsExceptionAdvice Error",e);
        }
    }

    /**
     * 所有异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public void ErrorHandler(Exception ex) {
        errorPrint(ex);
    }

    /**
     * 拦截捕捉自定义异常 AoomsException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AoomsException.class)
    public void AoomsErrorHandler(AoomsException ex) {
        errorPrint(ex);
    }

}