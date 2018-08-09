package net.aooms.core.web;

import com.alibaba.fastjson.JSON;
import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTORet;
import net.aooms.core.exception.AoomsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类
 * Created by cccyb on 2018-04-18
 */

//@ControllerAdvice
public class AoomsExceptionAdvice extends DefaultHandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 错误输出
     * @param ex
     */
    private void errorPrint(Exception ex, HttpServletRequest request, HttpServletResponse resp){
        try {
            DTORet ret = new DTORet();

            if(ex != null){
                // 404
                if(ex instanceof NoHandlerFoundException){
                    ret.failure(ex.getMessage());
                }else{
                    String error = ex.getMessage();
                    if(error == null){
                        error = ex.toString();
                    }
                    ret.failure(error);
                }

                logger.error("AoomsError",ex);
            }


          /*  String requestType = AoomsContextHolder.getRequest().getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(requestType)){
                PrintWriter writer = AoomsContextHolder.getResponse().getWriter();
                AoomsContextHolder.getResponse().setContentType("application/json");
                writer.write(JSON.toJSONString(ret.getData()));
                writer.flush();
                writer.close();
            }else{
                AoomsContextHolder.getResponse().sendRedirect("/error");
            }*/

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
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
    public void ErrorHandler(Exception ex, HttpServletRequest request, HttpServletResponse resp) {
        errorPrint(ex,request,resp);
    }

    /**
     * 拦截捕捉自定义异常 AoomsException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AoomsException.class)
    public void AoomsErrorHandler(AoomsException ex, HttpServletRequest request, HttpServletResponse resp) {
        errorPrint(ex,request,resp);
    }

}