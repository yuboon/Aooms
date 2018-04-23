package net.aooms.core.web.interceptors;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Param参数处理
 * Created by cccyb on 2018-04-19
 */
public class DemoInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println(request.getRequestURL() + ":" + handler.getClass().getName());

        if(!isClear(handler)){
            System.err.println("DemoInterceptor......");
        }

        return true;
    }



}