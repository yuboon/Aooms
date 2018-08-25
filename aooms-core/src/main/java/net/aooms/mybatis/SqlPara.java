package net.aooms.mybatis;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import net.aooms.core.data.DataBoss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Sql script params
 */
public class SqlPara {

    public static SqlPara SINGLETON = new SqlPara();
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 内置key
    private static List<String> InternalKey = Arrays.asList(
            MyBatisConst.CRUD_QUERY_COUNT_PLACEHOLDER,
            MyBatisConst.CRUD_QUERY_PAGING_PLACEHOLDER
    );

    private LinkedCaseInsensitiveMap paramMaps = new LinkedCaseInsensitiveMap();

    private boolean isPaging;

    private int page,limit;

    // 数据量统计标记
    public static class Count{

    }

    public static SqlPara NEW(){
        SqlPara sqlPara = new SqlPara();
        return sqlPara;
    }

    public static SqlPara fromDataBoss(){
        SqlPara sqlPara = new SqlPara();
        sqlPara.addParams(DataBoss.get().getPara().getData());
        sqlPara.addParams(DataBoss.get().getPara().getPathVars());
        return sqlPara;
    }

    public static SqlPara NEW(Map<String,Object> params){
        SqlPara sqlPara = new SqlPara();
        sqlPara.addParams(params);
        return sqlPara;
    }

    public SqlPara set(String key, Object value){
        paramMaps.put(key, value);
        return this;
    }

    /**
     * 分页
     * @param page
     * @param limit
     * @return
     */
    public SqlPara paging(int page,int limit){
        if(page > 0 && limit > 0){
            isPaging = true;
            this.page = page;
            this.limit = limit;
        }
        return this;
    }

    public boolean isPaging(){
        return isPaging;
    }

    public SqlPara addParams(Map<String,Object> params){
        paramMaps.putAll(params);
        return this;
    }

    /**
     * 移除所有内置属性
     */
    public void removeInternalKey(){
        InternalKey.forEach(key -> {
            paramMaps.remove(key);
        });
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public Boolean getBoolean(String key){
        return Boolean.parseBoolean(getString(key));
    }

}
