package net.aooms.core.web;

import net.aooms.core.properties.PropertiesApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 服务请求客户端
 * Created by cccyb on 2018-02-24
 */
@Component
public class DefaultRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplate simpleRestTemplate;

    @Autowired
    private PropertiesApplication propertiesApplication;

    public String get(){
       // simpleRestTemplate.getForObject()

        return null;
    };

    public String post(){

        return null;
    }


}