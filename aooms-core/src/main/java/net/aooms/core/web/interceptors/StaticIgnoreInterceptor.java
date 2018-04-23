package net.aooms.core.web.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 静态资源忽略拦截器
 * Created by cccyb on 2018-04-23
 */
public class StaticIgnoreInterceptor extends AbstractInterceptor {

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        // 静态资源判断
        return false;
    }
}