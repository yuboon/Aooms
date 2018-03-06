package net.aooms.core.configuration;

import net.aooms.core.web.client.SimpleRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

}