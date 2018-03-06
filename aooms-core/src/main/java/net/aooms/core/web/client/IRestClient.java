package net.aooms.core.web.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 服务请求接口
 *
 * xxOriginal -> 原始请求处理接口，不从服务中心获取服务地址，直接请求URL
 *
 * Created by cccyb on 2018-02-24
 */
public interface IRestClient {

    public ResponseEntity<String> get(String url);

    public ResponseEntity<String> getOriginal(String url);

    public ResponseEntity<String> get(String url, Map<String,Object> params);

    public ResponseEntity<String> getOriginal(String url, Map<String,Object> params);

    public ResponseEntity<String> post(String url);

    public ResponseEntity<String> postOriginal(String url);

    public ResponseEntity<String> post(String url, Map<String,Object> params);

    public ResponseEntity<String> postOriginal(String url, Map<String,Object> params);

    public ResponseEntity<String> upload(String url,Map<String,Object> params, Map<String,File> uploadFiles);

    public ResponseEntity<String> uploadOriginal(String url,Map<String,Object> params, Map<String,File> uploadFiles);


    // get RestTemmplate Original
    public RestTemplate getRestTemplate();

    public SimpleRestTemplate getSimpleRestTemplate();

}