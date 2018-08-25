package net.aooms.core;

import net.aooms.core.web.client.SimpleRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 框架配置Bean
 * Created by cccyb on 2018-03-05
 */
public interface IConfiguration {


    public RestTemplate restTemplate();

    public SimpleRestTemplate simpleRestTemplate();

    public WebMvcConfigurer aoomsWebConfigurer();


}