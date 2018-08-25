package net.aooms.core.web.interceptor;


import net.aooms.core.web.AoomsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ServletContext上下文初始化
 * Created by cccyb on 2018-04-19
 */
public class ContextInterceptor extends AoomsAbstractInterceptor {

    public ContextInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        AoomsContext.init(new AoomsContext.Context(request,response,handler));
        return true;
    }

    @Override
    public void finalHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        AoomsContext.remove();
    }
}