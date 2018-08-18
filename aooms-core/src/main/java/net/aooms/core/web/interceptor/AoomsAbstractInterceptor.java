package net.aooms.core.web.interceptor;

import net.aooms.core.annotation.ClearInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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

        Class<? extends AoomsAbstractInterceptor>[] interceptors = clearInterceptor.value();
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
    public void invokeAfter(HttpServletRequest request, HttpServletResponse response, Object handler ,ModelAndView modelAndView){};
    public void invokeFinal(HttpServletRequest request, HttpServletResponse response, Object handler ,Exception ex){};


}