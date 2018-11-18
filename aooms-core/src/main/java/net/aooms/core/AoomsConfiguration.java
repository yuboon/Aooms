package net.aooms.core;

import net.aooms.core.module.hystrix.ThreadLocalProcessHystrixConcurrencyStrategy;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.property.*;
import net.aooms.core.service.DbLogService;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.AoomsGlobalErrorController;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.service.CallServiceController;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.core.web.filter.CorsFilter;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * 框架默认配置类
 * Created by 风象南(yuboon) on 2018-03-05
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
    public PropertyAooms propertyAooms(){
        return new PropertyAooms();
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
    public ServiceConfigurations serviceConfigurations(){
        return new ServiceConfigurations();
    };

    @Bean
    @Order(-1)
    public WebMvcConfigurer webMvcConfigurer(){
        return new AoomsWebMvcConfigurer();
    };

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

    @Bean
    public CallServiceController callServiceController(){
        return new CallServiceController();
    }

    @Bean
    public SpringUtils springUtils(){
        return new SpringUtils();
    }

    @Bean
    public DbLogService dbLogService(){
        return new DbLogService();
    }

    @Bean
    public AoomsApplicationRunner aoomsApplicationRunner(){
        return new AoomsApplicationRunner();
    }

    // 上传文件临时路径指定
    /*@Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(tmepPath);
        return factory.createMultipartConfig();
    }*/

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        //registration.setOrder(1);
        return registration;
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