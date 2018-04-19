package net.aooms.core.web.interceptors;


import cn.hutool.core.collection.CollectionUtil;
import net.aooms.core.dto.DTO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

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

        DTO.me().getPara().setData(params);
        return true;
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