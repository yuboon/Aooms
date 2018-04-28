package net.aooms.core.web.interceptor;


import net.aooms.core.web.ServletContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ServletContext上下文初始化
 * Created by cccyb on 2018-04-19
 */
public class ServletContextInterceptor extends AoomsAbstractInterceptor {

    public ServletContextInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){
        ServletContextHolder.init(new ServletContextHolder.ServletContext(request,response));
        return true;
    }

    @Override
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        ServletContextHolder.remove();
    }
}