package net.aooms.core.web;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import net.aooms.core.AoomsSetting;
import net.aooms.core.util.LogUtils;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.interceptor.*;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configurer
 * Created by 风象南(yuboon) on 2018/9/18
 */
@Order(-1)
public class AoomsWebMvcConfigurer implements WebMvcConfigurer {

    private AoomsInterceptorRegistryProxy interceptorRegistryProxy;

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                //.allowedOrigins("http://localhost:9000")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE");
    }*/

    // 框架默认拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册代理类
        interceptorRegistryProxy = new AoomsInterceptorRegistryProxy(registry);

        String[] pathPatterns = interceptorRegistryProxy.getPathPatterns();
        String[] ignores = interceptorRegistryProxy.getIgnores();

        // important 顺序很重要
        interceptorRegistryProxy.addInterceptor(new ContextInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new ControllerInterceptor(pathPatterns, ignores));
        interceptorRegistryProxy.addInterceptor(new ReportInterceptor(pathPatterns, ignores));
        interceptorRegistryProxy.addInterceptor(new TokenInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new LoginInterceptor(pathPatterns,ignores)); //ArrayUtil.append(ignores,"/login")
        //registryProxy.addInterceptor(new PermissionInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new DbLogInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new DataBossInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new ParamInterceptor(pathPatterns,ignores));
        interceptorRegistryProxy.addInterceptor(new DefaultRenderInterceptor(pathPatterns,ignores));

        /*String[] excludes = new String[]{"/find*","/login","/logout"};
        String[] ignores2 = ArrayUtil.addAll(interceptorRegistryProxy.getIgnores(),excludes);
        System.err.println("interceptorRegistryProxy.getPathPatterns():" + interceptorRegistryProxy.getPathPatterns());
        interceptorRegistryProxy.addInterceptor(new DemoModeInterceptor(interceptorRegistryProxy.getPathPatterns(),ignores2));*/

        // 自定义拦截器追加
        /*ApplicationContext context = SpringUtils.getApplicationContext();
        String[] beanNames = context.getBeanNamesForType(AoomsSetting.class);
        for(String name : beanNames){
            AoomsSetting settingBean = (AoomsSetting)context.getBean(name);
            settingBean.configInterceptor(interceptorRegistryProxy);
        }*/
    }


    // 指定路径忽略大小写
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    public AoomsInterceptorRegistryProxy getInterceptorRegistryProxy() {
        return interceptorRegistryProxy;
    }

}
