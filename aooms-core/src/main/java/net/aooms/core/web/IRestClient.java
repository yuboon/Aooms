package net.aooms.core.web;

import java.io.File;
import java.util.Map;

/**
 * 服务请求接口
 * Created by cccyb on 2018-02-24
 */
public interface IRestClient {

    public String get(String url);

    public String get(String url, Map<String,Object> params);

    public String post(String url);

    public String post(String url, Map<String,Object> params);

    public String upload(String url,Map<String,Object> params, Map<String,File> uploadFiles);


}