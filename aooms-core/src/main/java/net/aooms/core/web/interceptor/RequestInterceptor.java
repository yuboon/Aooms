package net.aooms.core.web.interceptor;


import net.aooms.core.Aooms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * demo拦截器
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class RequestInterceptor extends AoomsAbstractInterceptor {

    public RequestInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        boolean isDev = Aooms.self().getPropertyObject().getAoomsProperty().isDevMode();
        if(isDev){
            System.out.println("http:" + request.getRequestURL());
        }
        return true;
    }



}