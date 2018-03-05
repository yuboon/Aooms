package net.aooms.core.web.client;

import net.aooms.core.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 服务请求客户端
 * Created by cccyb on 2018-02-24
 */
@Component
public class AoomsRestClient implements IRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpleRestTemplate simpleRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    private Boolean remoteMode;

    @Override
    public ResponseEntity<String> get(String url) {
        ResponseEntity<String> resp = null;
        if(isRemoteMode()){
            resp = restTemplate.getForEntity(url,String.class);
        }else{
            resp = simpleRestTemplate.getForEntity(url,String.class);
        }
        return resp;
    }

    @Override
    public String get(String url, Map<String, Object> params) {
        if(isRemoteMode()){
            return restTemplate.getForObject(url,String.class,params);
        }
        return simpleRestTemplate.getForObject(url,String.class,params);

    }

    @Override
    public String post(String url) {
        return null;
    }

    @Override
    public ResponseEntity<String> post(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = null;
        if(isRemoteMode()){
            resp = restTemplate.postForEntity(url,null,String.class,params);
        }else{
            resp = simpleRestTemplate.postForEntity(url,null,String.class,params);
        }
        return resp;
    }

    @Override
    public String upload(String url, Map<String, Object> params, Map<String, File> uploadFiles) {
        return null;
    }

    @Override
    public SimpleRestTemplate getSimpleRestTemplate() {
        return simpleRestTemplate;
    }

    public boolean isRemoteMode(){
        if(null == remoteMode)
            return applicationProperties.isRemoteMode();
        return remoteMode;
    }

    @Override
    public void setRemoteMode(boolean remoteMode) {
        this.remoteMode = remoteMode;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }


}