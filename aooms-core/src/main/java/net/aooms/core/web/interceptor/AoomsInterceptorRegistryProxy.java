package net.aooms.core.web.interceptor;

import com.google.common.collect.Lists;
import net.aooms.core.exception.AoomsExceptions;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

/**
 * 拦截器注册代理类
 * Created by 风象南(yuboon) on 2018-04-28
 */
public class AoomsInterceptorRegistryProxy {

    private InterceptorRegistry interceptorRegistry;
    private static List<Class<? extends AoomsAbstractInterceptor>> skipInterceptors = Lists.newArrayList();
    private static List<Class<? extends AoomsAbstractInterceptor>> interceptors = Lists.newArrayList();
    private static List<String> interceptorNames = Lists.newArrayList();

    // 忽略的路径，默认忽略静态资源、error、其他监控路径等
    private String[] ignores = new String[]{
            "/**/*.jpg",
            "/**/*.jpeg",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.css",
            "/**/*.js",
            "/error"
    };

    // 拦截路径,默认值拦截一切
    private String[] pathPatterns = new String[]{"/**"};

    public AoomsInterceptorRegistryProxy(InterceptorRegistry interceptorRegistry) {
        this.interceptorRegistry = interceptorRegistry;
    }

    /**
     * 添加拦截器
     * @param interceptor
     */
    public void addInterceptor(AoomsAbstractInterceptor interceptor){
        if(interceptorRegistry == null)
            throw AoomsExceptions.create("InterceptorRegistry is null");

        // 不重复添加
        if(interceptorNames.contains(interceptor.getClass().getName())){
            return;
        }

        if(interceptor.getPathPatterns() == null)
            throw AoomsExceptions.create("Interceptor " +interceptor.getClass().getName() + " PathPatterns is null");

        InterceptorRegistration interceptorRegistration = interceptorRegistry.addInterceptor(new AoomsInterceptorAdaptor(interceptor));
        interceptorRegistration.addPathPatterns(interceptor.getPathPatterns());
        if(interceptor.getIgnores() != null){
            interceptorRegistration.excludePathPatterns(interceptor.getIgnores());
        }

        interceptor.setAoomsInterceptorRegistryProxy(this);
        interceptors.add(interceptor.getClass());
        interceptorNames.add(interceptor.getClass().getName());
    }

    public void removeInterceptor(Class<? extends  AoomsAbstractInterceptor> interceptor){
        skipInterceptors.add(interceptor);
    }

    public InterceptorRegistry getInterceptorRegistry() {
        return interceptorRegistry;
    }

    public String[] getIgnores() {
        return ignores;
    }

    public void setIgnores(String[] ignores) {
        this.ignores = ignores;
    }

    public String[] getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(String[] pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public List<Class<? extends AoomsAbstractInterceptor>> getInterceptors() {
        return interceptors;
    }

    public List<Class<? extends AoomsAbstractInterceptor>> getSkipInterceptors() {
        return skipInterceptors;
    }
}