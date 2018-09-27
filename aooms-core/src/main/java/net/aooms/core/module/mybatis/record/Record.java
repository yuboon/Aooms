package net.aooms.core.module.mybatis.record;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.aooms.core.data.DataBoss;
import net.aooms.core.data.DataResult;
import net.aooms.core.module.mybatis.MyBatisConst;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.*;

/**
 * Record
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public class Record extends LinkedCaseInsensitiveMap {

    // 内置key
    private static List<String> InternalKey = Arrays.asList(
            MyBatisConst.TABLE_NAME_PLACEHOLDER,
            MyBatisConst.TABLE_PK_NAME_PLACEHOLDER
    );

    // 一般属性，保存时，不会持久化到数据库
    private Map<String,Object> generalData = new LinkedCaseInsensitiveMap();

    /**
     * 创建Record
     * @return
     */
    public static Record NEW(){
        return new Record();
    }

    /**
     * 设置数据集
     * @return
     */
    public Record setData(Map<String, Object> data){
        this.clear();
        this.putAll(data);
        return this;
    }

    /**
     * 清空
     */
    @Override
    public void clear(){
        this.clear();
        generalData.clear();
    }

    public Record set(String key,Object value){
        this.put(key,value);
        return this;
    }

    // bean getter
    public <T> T toBean(Class<T> beanClass){
        return BeanUtil.mapToBean(this,beanClass,true);
    }

    // bean setter
    public <T> Record fromBean(T bean){
        return (Record) BeanUtil.beanToMap(bean,this,true,true);
    }

    public Record setByKey(String prefix){
        String paraPrefix = prefix + ".";

        Map<String,Object> paras = DataBoss.self().getPara().getData();
        for(Map.Entry<String,Object> entry : paras.entrySet()){
            Object value = entry.getValue();
            /* 不是以定义的记录标识为前缀不处理  */
            if(!entry.getKey().startsWith(paraPrefix))continue;
            String key = entry.getKey().replace(paraPrefix, "");
            this.set(key,value);
        }
        return this;
    }

    public Record setByJsonKey(String jsonKey){
        String jsonStr = DataBoss.self().getPara().getString(jsonKey);
        if(StrUtil.isNotBlank(jsonStr)){
            JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
            Set<String> keySet = jsonObject.keySet();
            for(String key : keySet){
                Object value = jsonObject.get(key);
                this.set(key,value);
            }
        }
        return this;
    }

    public String getString(String key){
        return String.valueOf(this.get(key));
    }

    public Integer getInteger(String key){
        return Integer.parseInt(getString(key));
    }

    public Long getLong(String key){
        return Long.parseLong(getString(key));
    }

    public Float getFloat(String key){
        return Float.parseFloat(getString(key));
    }

    public Double getDouble(String key){
        return Double.parseDouble(getString(key));
    }

    public Date getDate(String key){
        return DateUtil.parseDate(getString(key));
    }

    public DateTime getDateTime(String key){
        return DateUtil.parseDateTime(getString(key));
    }

    public Boolean getBoolean(String key){
        return Boolean.parseBoolean(getString(key));
    }

    public Record pkIs(String key){
        this.put(MyBatisConst.TABLE_PK_NAME_PLACEHOLDER,key);
        return this;
    }


    public Record setGeneral(String key,Object value){
        generalData.put(key,value);
        return this;
    }

    public <T> T getGeneral(String key){
        return (T)generalData.get(key);
    }

    public Map<String,Object> getGeneralData(){
        return generalData;
    }

    /**
     * 移除所有内置属性
     */
    public void removeInternalKey(){
        InternalKey.forEach(key -> {
            this.remove(key);
        });
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
