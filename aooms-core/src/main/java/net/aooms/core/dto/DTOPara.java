package net.aooms.core.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;
import sun.rmi.transport.ObjectTable;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * DTO-参数对象
 * Created by cccyb on 2018-04-18
 */
public class DTOPara implements Serializable {

    private Map<String,Object> params = CollectionUtil.newHashMap();

    /**
     * 获取参数集
     * @return
     */
    public Map<String,Object> getData(){
        return params;
    }

    /**
     * 设置参数集
     * @return
     */
    public void setData(Map<String,Object> paramMap){
        this.params = paramMap;
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object get(String key){
        Object value = params.get(key.toLowerCase());
        return value;
    };

    /**
     * 获取字符串值
     * @param key
     * @return
     */
    public String getStr(String key){
        Object value = get(key);
        if(value == null){
            return "";
        }
        return String.valueOf(value);
    };

    /**
     * 获取字符串like格式
     *
     * for example:
     *      % + value + %
     *
     * @param key
     * @return
     */
    public String getStrLike(String key){
        String value = getStr(key);
        if(StringUtils.isEmpty(value)){
            return "%";
        }
        return new StringBuilder("%").append(value).append("%").toString();
    };

    /**
     * 获取字符串like格式
     *
     * for example:
     *      value + %
     *
     * @param key
     * @return
     */
    public String getStrLikeAfter(String key){
        String value = getStr(key);
        if(StringUtils.isEmpty(value)){
            return "%";
        }
        return new StringBuilder().append(value).append("%").toString();
    };

    /**
     * 获取整型值
     * @param key
     * @return
     */
    public int getInt(String key){
        return Integer.valueOf(getStr(key));
    };

    /**
     * 获取长整型值
     * @param key
     * @return
     */
    public int getLong(String key){
        return Integer.valueOf(getStr(key));
    };




}