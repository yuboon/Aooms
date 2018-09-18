package net.aooms.core;

import net.aooms.core.module.hystrix.ThreadLocalProcessHystrixConcurrencyStrategy;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.property.PropertyApplication;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.property.PropertyServer;
import net.aooms.core.property.PropertyTest;
import net.aooms.core.web.AoomsGlobalErrorController;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.core.web.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

    @Autowired
    private ServerProperties serverProperties;
    @Bean
    public BasicErrorController basicErrorController(){
        return new AoomsGlobalErrorController(serverProperties);
    }

    /**
     * web Configurer
     */
    /*@Bean
    public WebMvcConfigurer aoomsWebConfigurer() {
        System.err.println("345353453453");
        return new AoomsWebMvcConfigurer();
    }*/

}