package net.aooms.core.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 框架拦截器
 * Created by 风象南(cheereebo) on 2018-04-23
 */
public class AoomsInterceptorAdaptor implements HandlerInterceptor {

    private AoomsAbstractInterceptor abstractInterceptor;

    public AoomsInterceptorAdaptor(AoomsAbstractInterceptor abstractInterceptor) {
        this.abstractInterceptor = abstractInterceptor;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(abstractInterceptor.isDisabled(handler)) return true;
        return abstractInterceptor.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(abstractInterceptor.isEnabled(handler)){
            abstractInterceptor.handle(request,response,handler,modelAndView);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(abstractInterceptor.isEnabled(handler)){
            abstractInterceptor.finalHandle(request,response,handler,ex);
        }
    }
}