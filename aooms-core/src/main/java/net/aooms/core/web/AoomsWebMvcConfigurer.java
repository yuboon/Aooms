package net.aooms.core.web;

import net.aooms.core.web.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web Configurer
 * Created by 风象南(cheereebo) on 2018/9/18
 */
public class AoomsWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    // 拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册代理类
        AoomsInterceptorRegistryProxy registryProxy = new AoomsInterceptorRegistryProxy(registry);
        String[] pathPatterns = registryProxy.getPathPatterns();
        String[] ignores = registryProxy.getIgnores();

        //registryProxy.addInterceptor(new LoginInterceptor(pathPatterns,ignores)); //ArrayUtil.append(ignores,"/login")
        //registryProxy.addInterceptor(new PermissionInterceptor(pathPatterns,ignores));
        registryProxy.addInterceptor(new DataBossInterceptor(pathPatterns,ignores));
        registryProxy.addInterceptor(new ContextInterceptor(pathPatterns,ignores));
        //registryProxy.addInterceptor(new RequestInterceptor(pathPatterns,ignores));
        registryProxy.addInterceptor(new ParamInterceptor(pathPatterns,ignores));
        registryProxy.addInterceptor(new DefaultRenderInterceptor(pathPatterns,ignores));
    }

    // 指定路径忽略大小写
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

}
