package net.aooms.core.web.client;

import net.aooms.core.property.ApplicationProperty;
import net.aooms.core.property.ServerProperty;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

/**
 * 服务请求客户端
 * Created by cccyb on 2018-02-24
 */
@Component
public class AoomsRestClient implements IRestClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpleRestTemplate simpleRestTemplate;

    @Autowired
    private ApplicationProperty applicationProperties;

    @Autowired
    private ServerProperty serverProperties;

    private Boolean useRegistry;

    @Override
    public ResponseEntity<String> get(String url) {
        return get(url, Collections.emptyMap());
    }

    @Override
    public ResponseEntity<String> getOriginal(String url) {
       return getOriginal(url, Collections.emptyMap());
    }

    @Override
    public ResponseEntity<String> get(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = null;
        if(useRegistry()){
            resp = restTemplate.getForEntity(url,String.class);
        }else{
            String serverUrl = getLocalServerUrl(url);
            if(logger.isInfoEnabled()){
                logger.info(LogUtils.logFormat("convert " + url + " -> " + serverUrl));
            }
            resp = getOriginal(serverUrl,params);
        }
        return resp;
    }

    @Override
    public ResponseEntity<String> getOriginal(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = simpleRestTemplate.getForEntity(url,String.class,params);
        return resp;
    }

    @Override
    public ResponseEntity<String> post(String url) {
        return post(url,Collections.emptyMap());
    }

    @Override
    public ResponseEntity<String> postOriginal(String url) {
        return postOriginal(url,Collections.emptyMap());
    }

    @Override
    public ResponseEntity<String> post(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = null;
        if(useRegistry()){
            resp = restTemplate.postForEntity(url,null,String.class,params);
        }else{
            String serverUrl = getLocalServerUrl(url);
            if(logger.isInfoEnabled()){
                logger.info(LogUtils.logFormat("convert " + url + " -> " + serverUrl));
            }
            resp = postOriginal(serverUrl,params);
        }
        return resp;

    }

    @Override
    public ResponseEntity<String> postOriginal(String url, Map<String, Object> params) {
        ResponseEntity<String> resp = simpleRestTemplate.postForEntity(url,null,String.class,params);
        return resp;
    }

    @Override
    public ResponseEntity<String> upload(String url, Map<String, Object> params, Map<String, File> uploadFiles) {
        params.putAll(uploadFiles);
        return this.post(url,params);
    }

    @Override
    public ResponseEntity<String> uploadOriginal(String url, Map<String, Object> params, Map<String, File> uploadFiles) {
        params.putAll(uploadFiles);
        return postOriginal(url,params);
    }

    // 是否使用注册中心
    public boolean useRegistry(){
        if(null == useRegistry)
            return useRegistry = applicationProperties.isAoomsUseRegistry();
        return useRegistry;
    }

    // 获取本地服务真实地址,本地集成部署或调试时使用
    private String getLocalServerUrl(String url){
        try {
            URI uri = new URI(url);
            StringBuilder builder = new StringBuilder();
            builder
                    .append(uri.getScheme())
                    .append(":")
                    .append("//")
                    .append("127.0.0.1:")
                    .append(serverProperties.getPort() == 0 ? 8080 : serverProperties.getPort())
                    .append(uri.getPath())
            ;

            return builder.toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(LogUtils.errorLogFormat("server url : "+ url +" is invalid"),e);
        }
    }

    @Override
    public SimpleRestTemplate getSimpleRestTemplate() {
        return simpleRestTemplate;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }


}