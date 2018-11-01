package net.aooms.core.web.client;

import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * web请求客户端
 * Created by 风象南(yuboon) on 2018/9/7
 */
public class AoomsHttpTemplate {

    //slf4j.log
    private Logger log = LoggerFactory.getLogger(AoomsHttpTemplate.class);

    public String get(String url) {
        String result = HttpUtil.get(url);
        return result;
    }

    public String get(String url,Map<String, Object> params) {
        String result = HttpUtil.get(url, params);
        return result;
    }

    public String post(String url) {
        String result = HttpUtil.post(url, Maps.newHashMap());
        return result;
    }

    public String post(String url,Map<String, Object> params) {
        String result = HttpUtil.post(url, params);
        return result;
    }

    public String upload(String url,Map<String, Object> params, Map<String,File> files) {
        params.putAll(files);
        String result = HttpUtil.post(url, params);
        return result;
    }
}


