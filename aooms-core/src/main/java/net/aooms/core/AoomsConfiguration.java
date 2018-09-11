package net.aooms.core;

import net.aooms.core.module.hystrix.ThreadLocalProcessHystrixConcurrencyStrategy;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.property.PropertyApplication;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.property.PropertyServer;
import net.aooms.core.property.PropertyTest;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.core.web.interceptor.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 框架默认配置类
 * Created by 风象南(cheereebo) on 2018-03-05
 */
@Configuration
public class AoomsConfiguration {

    @Bean("restTemplate")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @LoadBalanced
    @Bean("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public AoomsHttpTemplate aoomsHttpTemplate(){
        AoomsHttpTemplate httpTemplate = new AoomsHttpTemplate();
        return httpTemplate;
    }

    @Bean
    public AoomsRestTemplate aoomsRestTemplate(){
        AoomsRestTemplate restTemplate = new AoomsRestTemplate();
        return restTemplate;
    }

    @Bean
    public PropertyObject propertyObject(){
        PropertyObject propertyObject = new PropertyObject();
        propertyObject.instance(propertyObject);
        return propertyObject;
    }

    @Bean
    public PropertyApplication propertyApplication(){
        return new PropertyApplication();
    }

    @Bean
    public PropertyServer propertyServer(){
        return new PropertyServer();
    }

    @Bean
    public PropertyTest propertyTest(){
        return new PropertyTest();
    }

    @Bean
    public Db db(){
        return new Db();
    }

    @Bean
    public Aooms aoomsInstance(){
        Aooms aooms = new Aooms();
        aooms.instance(aooms);
        return aooms;
    }

    @Bean
    public ThreadLocalProcessHystrixConcurrencyStrategy threadLocalProcessHystrixConcurrencyStrategy(){
        return new ThreadLocalProcessHystrixConcurrencyStrategy();
    }

    /**
     * web环境配置
     * @return
     */
    @Bean
    //@ConditionalOnMissingBean
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

                registryProxy.addInterceptor(new LoginInterceptor(pathPatterns,ignores)); //ArrayUtil.append(ignores,"/login")
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