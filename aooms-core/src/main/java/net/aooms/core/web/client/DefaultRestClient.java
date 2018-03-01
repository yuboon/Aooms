package net.aooms.core.web.client;

import net.aooms.core.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 服务请求客户端
 * Created by cccyb on 2018-02-24
 */
@Component
public class DefaultRestClient implements  IRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate simpleRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;


    @Override
    public String get(String url) {
        return null;
    }

    @Override
    public String get(String url, Map<String, Object> params) {
        return null;
    }

    @Override
    public String post(String url) {
        return null;
    }

    @Override
    public String post(String url, Map<String, Object> params) {
        return null;
    }

    @Override
    public String upload(String url, Map<String, Object> params, Map<String, File> uploadFiles) {
        return null;
    }

}