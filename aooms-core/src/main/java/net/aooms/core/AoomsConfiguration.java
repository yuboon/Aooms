package net.aooms.core;

import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.client.SimpleRestTemplate;
import net.aooms.core.web.interceptor.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 框架默认配置类
 *
 * 项目中可自定义实现IConfiguration覆盖默认的AoomsConfiguration
 *
 * Created by cccyb on 2018-03-05
 */
@Configuration
public class AoomsConfiguration implements IConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public SimpleRestTemplate simpleRestTemplate(){
        SimpleRestTemplate simpleRestTemplate = new SimpleRestTemplate();
        return simpleRestTemplate;
    }

    @Bean
    public PropertyObject propertyObject(){
        PropertyObject propertyObject = new PropertyObject();
        propertyObject.instance(propertyObject);
        return propertyObject;
    }

    /**
     * web环境配置
     * @return
     */
    @Bean
    public WebMvcConfigurer aoomsWebConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 拦截器注册代理类
                AoomsInterceptorRegistryProxy registryProxy = new AoomsInterceptorRegistryProxy(registry);
                String[] pathPatterns = registryProxy.getPathPatterns();
                String[] ignores = registryProxy.getIgnores();

                registryProxy.addInterceptor(new KissoLoginInterceptor(pathPatterns,ignores)); //ArrayUtil.append(ignores,"/login")
                //registryProxy.addInterceptor(new KissoPermissionInterceptor(pathPatterns,ignores));
                registryProxy.addInterceptor(new DataBossInterceptor(pathPatterns,ignores));
                registryProxy.addInterceptor(new ContextInterceptor(pathPatterns,ignores));
                registryProxy.addInterceptor(new DemoInterceptor(pathPatterns,ignores));
                registryProxy.addInterceptor(new ParamInterceptor(pathPatterns,ignores));
            }

            // 指定路径忽略大小写
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                AntPathMatcher matcher = new AntPathMatcher();
                matcher.setCaseSensitive(false);
                configurer.setPathMatcher(matcher);
            }
        };
    }

}