package net.aooms.core.web.interceptor;


import net.aooms.core.web.AoomsContextHolder;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ServletContext上下文初始化
 * Created by cccyb on 2018-04-19
 */
public class AoomsContextInterceptor extends AoomsAbstractInterceptor {

    public AoomsContextInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){
        AoomsContextHolder.init(new AoomsContextHolder.AoomsContext(request,response,handler));
        return true;
    }

    @Override
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        AoomsContextHolder.remove();
    }
}