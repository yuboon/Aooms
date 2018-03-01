package net.aooms.core.web.client;

import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * 服务调用客户端配置类
 * Created by cccyb on 2018-02-22
 */
@Configuration
public class RestClientConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean(name="simpleRestTemplate")
    public RestTemplate simpleRestTemplate(){
        RestTemplate simpleRestTemplate = new RestTemplate();
        return simpleRestTemplate;
    }

}