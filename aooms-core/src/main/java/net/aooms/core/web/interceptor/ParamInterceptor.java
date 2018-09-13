package net.aooms.core.web.interceptor;


import cn.hutool.core.collection.CollectionUtil;
import net.aooms.core.data.DataBoss;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Param参数处理
 * Created by 风象南(cheereebo) on 2018-04-19
 */
public class ParamInterceptor extends AoomsAbstractInterceptor {

    public ParamInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Map<String,String[]> paramMaps = request.getParameterMap();
        Map<String,Object> params = CollectionUtil.newHashMap();
        paramMaps.forEach((k,v) -> {
            params.put(k.toLowerCase(),convert(v));
        });


        // 文件上传
        if(request instanceof AbstractMultipartHttpServletRequest){
            //CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
            StandardMultipartHttpServletRequest multipartHttpServletRequest = (StandardMultipartHttpServletRequest)request;
            Map<String,MultipartFile> multipartFileMap = multipartHttpServletRequest.getFileMap();
            DataBoss.self().getPara().setFiles(multipartFileMap);
        }

        // 请求参数
        DataBoss.self().getPara().setData(params);
        // 路径参数
        DataBoss.self().getPara().setPathVars((Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
        return true;
    }

    /**
     * 请求是否包含附件
     * @param method
     * @param contentType
     * @return
     */
    private boolean isMultipartContent(String method,String contentType){
        if(!"POST".equals(method.toUpperCase())){
            return false;
        }
        if (contentType == null) {
            return false;
        } else {
            return contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/");
        }
    }

    /**
     * 参数转换
     * @param value
     * @return
     */
    private String convert(String[] value){
        if(value == null){
            return "";
        }

        if(value.length == 1){
            return value[0];
        }

        int len = value.length;
        String values = "";
        for (int i = 0; i < len; i++){
            if(i > 0){
                values += ",";
            }
            values += value[i];
        }
        return values;
    }

}