package net.aooms.core.web.interceptors;


import cn.hutool.core.collection.CollectionUtil;
import net.aooms.core.dto.DTO;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.*;

/**
 * Param参数处理
 * Created by cccyb on 2018-04-19
 */
public class ParamInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
            System.err.println("name:" + multipartFileMap.get("my").getName());
            System.err.println("original.name:" + multipartFileMap.get("my").getOriginalFilename());

        }

        DTO.me().getPara().setData(params);
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