package net.aooms.core.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * demo拦截器
 * Created by cccyb on 2018-04-19
 */
public class DemoInterceptor extends AoomsAbstractInterceptor {

    public DemoInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){
        System.err.println("DemoInterceptor.getRequestURL:" + request.getRequestURL());

        return true;
    }



}