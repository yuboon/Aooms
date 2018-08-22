package net.aooms.mybatis;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Date;
import java.util.Map;

/**
 * Sql script params
 */
public class SqlPara {

    private LinkedCaseInsensitiveMap paramMaps = new LinkedCaseInsensitiveMap();

    public static SqlPara NEW(){
        SqlPara sqlPara = new SqlPara();
        return sqlPara;
    }

    public static SqlPara NEW(Map<String,Object> params){
        SqlPara sqlPara = new SqlPara();
        sqlPara.setParams(params);
        return sqlPara;
    }

    public SqlPara set(String key, Object value){
        paramMaps.put(key, value);
        return this;
    }

    public SqlPara setParams(Map<String,Object> params){
        paramMaps.putAll(params);
        return this;
    }

    public Object get(String key){
        return paramMaps.get(key);
    }

    public Map<String,Object> getParams(){
        return paramMaps;
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

}
