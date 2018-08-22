package net.aooms.core.web.interceptor;


import net.aooms.core.data.DataBoss;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DTO拦截器
 * Created by cccyb on 2018-04-19
 */
public class DataBossInterceptor extends AoomsAbstractInterceptor {

    public DataBossInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){
        DataBoss.create();
        return true;
    }

    @Override
    public void invokeAfter(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        DataBoss.destroy();
    }
}