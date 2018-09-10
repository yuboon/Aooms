package net.aooms.core.web.client;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import net.aooms.core.AoomsConstants;
import net.aooms.core.data.DataResultStatus;
import net.aooms.core.data.DataResult;
import net.aooms.core.property.PropertyApplication;
import net.aooms.core.property.PropertyServer;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class AoomsRestTemplate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    private PropertyApplication applicationProperties;

    @Autowired
    private PropertyServer serverProperties;

    private Boolean useRegistry;

    public DataResult get(String url) {
        return get(url, Collections.emptyMap());
    }

    public DataResult get(String url, Map<String, Object> params) {
        DataResult dataResult = new DataResult();
        Map map = null;
        if(useRegistry()){
            map = loadBalancedRestTemplate.getForObject(url,Map.class);
        }else{
            String serverUrl = getLocalServerUrl(url);
            if(logger.isInfoEnabled()){
                logger.info(LogUtils.logFormat("convert " + url + " -> " + serverUrl));
            }
            map = restTemplate.getForObject(serverUrl,Map.class,params);
        }

        Map mapStatus = (Map) map.get(AoomsConstants.Result.META);
        DataResultStatus status = BeanUtil.mapToBean(mapStatus,DataResultStatus.class,true);
        map.put(AoomsConstants.Result.META,status);
        dataResult.setData(map);
        return dataResult;
    }

    public DataResult post(String url) {
        return post(url,Collections.emptyMap());
    }

    public DataResult post(String url, Map<String, Object> params) {
        DataResult dataResult = new DataResult();
        Map map = null;
        if(useRegistry()){
            map = loadBalancedRestTemplate.getForObject(url,Map.class);
        }else{
            String serverUrl = getLocalServerUrl(url);
            if(logger.isInfoEnabled()){
                logger.info(LogUtils.logFormat("convert " + url + " -> " + serverUrl));
            }
            map = restTemplate.postForObject(serverUrl,params,Map.class);
        }

        Map mapStatus = (Map) map.get(AoomsConstants.Result.META);
        DataResultStatus status = BeanUtil.mapToBean(mapStatus,DataResultStatus.class,true);
        map.put(AoomsConstants.Result.META,status);
        dataResult.setData(map);
        return dataResult;
    }

    public DataResult upload(String url, Map<String, Object> params, Map<String, File> uploadFiles) {
        params.putAll(uploadFiles);
        return this.post(url,params);
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
            throw new IllegalArgumentException(LogUtils.errorLogFormat("server url : "+ url +" is invalid !"),e);
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }


}