package net.aooms.core.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * demo拦截器
 * Created by 风象南(cheereebo) on 2018-04-19
 */
public class RequestInterceptor extends AoomsAbstractInterceptor {

    public RequestInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        System.err.println("RequestInterceptor.getRequestURL:" + request.getRequestURL());

        return true;
    }



}