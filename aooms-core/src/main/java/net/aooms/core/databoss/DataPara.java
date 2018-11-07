package net.aooms.core.databoss;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import net.aooms.core.AoomsVar;
import net.aooms.core.exception.AoomsException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DataBoss-参数对象
 * Created by 风象南(yuboon) on 2018-04-18
 */
public class DataPara implements Serializable {

    private Map<String,Object> params = CollectionUtil.newHashMap();
    private Map<String,MultipartFile> files = CollectionUtil.newHashMap();
    private Map<String,Object> pathVars = CollectionUtil.newHashMap();

    /**
     * 获取参数集
     * @return
     */
    public Map<String,Object> getData(){
        return params;
    }

    /**
     * 获取文件
     * @return
     */
    public Map<String,MultipartFile> getFiles(){
        return files;
    }

    /**
     * 获取文件
     * @return
     */
    public MultipartFile getFile(String uploadName){
        return files.get(uploadName);
    }

    /**
     * 获取文件流
     * @return
     */
    public InputStream getFileInputStream(String uploadName){
        try {
            MultipartFile file = getFile(uploadName);
            if(file == null){
                return null;
            }

            return file.getInputStream();
        } catch (IOException e) {
           throw new AoomsException("File InputStrem " + uploadName + " get error" , e);
        }
    }

    /**
     * 获取所有的路径参数
     * @return
     */
    public Map<String, Object> getPathVars() {
        return pathVars;
    }

    /**
     * 获取属性集合
     * @return
     */
    public List<Object> getListFromJson(String key,String propName) {
        return getListFromJson(key,propName,Object.class);
    }

    /**
     * 获取属性集合
     * @return
     */
    public <T> List<T> getListFromJson(String key,String propName,Class<T> propType) {
        List<T> list = Lists.newArrayList();
        String jsonStr = getString(key);
        if(StrUtil.isNotBlank(jsonStr)){
            JSONArray jsonArray = JSON.parseArray(jsonStr);
            for(Object obj : jsonArray){
                JSONObject jsonObject = (JSONObject)obj;
                list.add((T)jsonObject.get(propName));
            }
        }
        return list;
    }

    /**
     * 获取路径参数
     * @return
     */
    public <T> T getPathVar(String key) {
        return (T)pathVars.get(key);
    }

    /**
     * 设置参数集
     * @return
     */
    public void setData(Map<String,Object> paramMap){
        this.params = paramMap;
    }

    /**
     * 设置文件
     * @return
     */
    public void setFiles(Map<String,MultipartFile> files){
        this.files = files;
    }

    /**
     * 设置路径参数
     */
    public void setPathVars(Map<String, Object> pathVars){
        this.pathVars = pathVars;
    }



    /**
     * 获取值
     * @param key
     * @return
     */
    public Object get(String key){
        Object value = params.get(key);
        return value;
    };

    /**
     * 获取字符串值
     * @param key
     * @return
     */
    public String getString(String key){
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
    public String getStringLike(String key){
        String value = getString(key);
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
    public String getStringLikeAfter(String key){
        String value = getString(key);
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
    public int getInteger(String key){
        return Integer.valueOf(getString(key));
    };

    /**
     * 获取长整型值
     * @param key
     * @return
     */
    public int getLong(String key){
        return Integer.valueOf(getString(key));
    };

    /**
     * 获取当前页数
     * @return
     */
    public int getPage(){
        String page = getString(AoomsVar.P_PAGE);
        if(StringUtils.isEmpty(page)) return 0;
        return Integer.valueOf(page);
    };

    /**
     * 获取每页数据量
     * @return
     */
    public int getLimit(){
        String limit = getString(AoomsVar.P_LIMIT);
        if(StringUtils.isEmpty(limit)) return 0;
        return Integer.valueOf(limit);
    };

    /**
     * 获取json字符串
     * @throws Exception
     */
    public List<String> getIdsFromJsonStr(String jsonStr){
        //[{"bean.id": "1"},{"bean.id": "1"}]
        List<String> ids = Lists.newArrayList();
        if(StrUtil.isEmpty(jsonStr))return ids;
        JSONArray idArray = JSON.parseArray(jsonStr);
        int size = idArray.size();
        for (int i = 0;i<size;i++) {
            ids.add(idArray.getJSONObject(i).getString(AoomsVar.ID));
        }
        return ids;
    }

}