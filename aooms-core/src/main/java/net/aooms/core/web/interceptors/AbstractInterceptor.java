package net.aooms.core.web.interceptors;

import net.aooms.core.annocation.ClearInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 抽象拦截器
 * Created by cccyb on 2018-04-23
 */
public abstract class AbstractInterceptor {

    public Method getMethod(Object handler){
        if(handler instanceof HandlerMethod){
            return ((HandlerMethod)handler).getMethod();
        }else if(handler instanceof ResourceHttpRequestHandler){
            return null;
        }
        return null;
    }

    /**
     * 不可用
     * @param handler
     * @return
     */
    public boolean isDisabled(Object handler){
        Method method = getMethod(handler);
        if(method == null) return  false;

        ClearInterceptor clearInterceptor = method.getAnnotation(ClearInterceptor.class);
        if(clearInterceptor == null){
            return false;
        }

        Class<? extends HandlerInterceptor>[] interceptors = clearInterceptor.value();
        int len = interceptors.length;
        for (int i = 0 ; i < len; i++){
            if(this.getClass() == interceptors[i]){
                return true;
            }
        }

        return false;
    }

    /**
     * 可用
     * @param handler
     * @return
     */
    public boolean isEnabled(Object handler){
        return !isDisabled(handler);
    }

    /**
     * 执行
     * @return
     */
    public boolean invokeBefore(HttpServletRequest request, HttpServletResponse response, Object handler){return true;};
    public void invokeAfter(HttpServletRequest request, HttpServletResponse response, Object handler){};
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler){};


}