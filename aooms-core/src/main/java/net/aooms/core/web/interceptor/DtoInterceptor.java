package net.aooms.core.web.interceptor;


import net.aooms.core.dto.DTO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DTO拦截器
 * Created by cccyb on 2018-04-19
 */
public class DtoInterceptor extends AoomsAbstractInterceptor {

    public DtoInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){
        DTO.create();
        return true;
    }

    @Override
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        DTO.destroy();
    }
}