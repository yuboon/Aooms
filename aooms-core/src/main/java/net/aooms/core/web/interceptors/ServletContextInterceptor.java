package net.aooms.core.web.interceptors;


import cn.hutool.core.collection.CollectionUtil;
import net.aooms.core.dto.DTO;
import net.aooms.core.web.ServletContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ServletContext上下文初始化
 * Created by cccyb on 2018-04-19
 */
public class ServletContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletContextHolder.init(new ServletContextHolder.ServletContext(request,response));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ServletContextHolder.remove();
    }
}