package net.aooms.core.web.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 框架拦截器
 * Created by cccyb on 2018-04-23
 */
public class AoomsInterceptorAdaptor implements HandlerInterceptor {

    private AbstractInterceptor abstractInterceptor;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(abstractInterceptor.isDisabled(handler)) return true;
        return abstractInterceptor.invokeBefore(request,response,handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(abstractInterceptor.isEnabled(handler)){
            abstractInterceptor.invokeAfter(request,response,handler);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(abstractInterceptor.isEnabled(handler)){
            abstractInterceptor.invokeFinal(request,response,handler);
        }
    }
}