package net.aooms.core.web.interceptor;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.aooms.core.annotation.ClearInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 抽象拦截器
 * Created by cccyb on 2018-04-23
 */
public abstract class AoomsAbstractInterceptor {

    // 排除路径
    private String[] ignores;

    // 拦截路径
    private String[] pathPatterns;


    public AoomsAbstractInterceptor(String[] pathPatterns,String[] ignores) {
        this.pathPatterns = pathPatterns;
        this.ignores = ignores;
    }

    public String[] getIgnores() {
        return ignores;
    }

    public void setIgnores(String[] ignores) {
        this.ignores = ignores;
    }

    public void setPathPatterns(String[] pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public String[] getPathPatterns() {
        return pathPatterns;
    }


    public Method getMethod(Object handler){
        if(handler instanceof HandlerMethod){
            return ((HandlerMethod)handler).getMethod();
        }
        return null;
    }

    public Class<?> getClass(Object handler){
        if(handler instanceof HandlerMethod){
            return ((HandlerMethod)handler).getBeanType();
        }
        return null;
    }

    /**
     * 不可用
     * @param handler
     * @return
     */
    public boolean isDisabled(Object handler){
        Class<?> clazz = getClass(handler);
        if(clazz == null) return false;

        Method method = getMethod(handler);
        if(method == null) return  false;

        ClearInterceptor classClearInterceptor = clazz.getAnnotation(ClearInterceptor.class);
        ClearInterceptor clearInterceptor = method.getAnnotation(ClearInterceptor.class);
        if(clearInterceptor == null && classClearInterceptor == null){
            return false;
        }

        Set<Class<? extends AoomsAbstractInterceptor>> interceptors = Sets.newHashSet();
        if(classClearInterceptor != null && classClearInterceptor.value() != null){
            interceptors.addAll(Arrays.asList(classClearInterceptor.value()));
        }

        if(clearInterceptor != null && clearInterceptor.value() != null){
            interceptors.addAll(Arrays.asList(clearInterceptor.value()));
        }

        for (Class<? extends  AoomsAbstractInterceptor> interceptor : interceptors){
            if(this.getClass() == interceptor){
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){return true;};
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler ,ModelAndView modelAndView){};
    public void finalHandle(HttpServletRequest request, HttpServletResponse response, Object handler ,Exception ex){};


}