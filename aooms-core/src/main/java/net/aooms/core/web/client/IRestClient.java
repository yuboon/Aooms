package net.aooms.core.web.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 服务请求接口
 * Created by cccyb on 2018-02-24
 */
public interface IRestClient {

    public ResponseEntity<String> get(String url);

    public String get(String url, Map<String,Object> params);

    public String post(String url);

    public ResponseEntity<String> post(String url, Map<String,Object> params);

    public String upload(String url,Map<String,Object> params, Map<String,File> uploadFiles);

    public void setRemoteMode(boolean isRemoteMode);

    public RestTemplate getRestTemplate();

    public SimpleRestTemplate getSimpleRestTemplate();

}