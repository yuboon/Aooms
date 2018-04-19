package net.aooms.core.configuration;

import net.aooms.core.web.client.SimpleRestTemplate;
import net.aooms.core.web.interceptors.DtoInterceptor;
import net.aooms.core.web.interceptors.ParamInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                registry.addInterceptor(new DtoInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
                registry.addInterceptor(new ParamInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
            }
        };
    }


}