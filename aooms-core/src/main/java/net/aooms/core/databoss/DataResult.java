package net.aooms.core.databoss;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.aooms.core.AoomsConstants;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.record.Record;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DataBoss-结果对象
 * Created by 风象南(yuboon) on 2018-04-18
 */
public class DataResult implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String,Object> results = CollectionUtil.newHashMap();
    private List<String> caller = Lists.newArrayList();

    public DataResult() {
        // 默认设置成功状态
        this.success();

    }

    public DataResult(Map<String,Object> results) {
        this.results = results;
        // 默认设置成功状态
        this.success();
    }

    public void setData(Map<String,Object> results) {
        this.results = results;
    }

    public void merge(Map<String,Object> results) {
        this.results.putAll(results);
    }

    /**
     * 填充结果数据
     * @param key
     * @param value
     * @return
     */
    public DataResult set(String key, Object value){
        if(logger.isInfoEnabled()){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String className = elements[3].getClassName();
            String methodName = elements[3].getMethodName();
            long lineNumber = elements[3].getLineNumber();

            caller.add(className + "." + methodName + " (line:" + lineNumber + ")");
        }
        results.put(key,value);
        return this;
    }

    /**
     * 设置请求状态
     */
    public DataResult setStatus(DataResultStatus status){
        results.put(AoomsConstants.Result.META ,status);
        return this;
    }

    public void printCaller(){
        LogUtils.logFormatPrint("DataResult.set Total Called " + caller.size() + " Times");
        int index = 0;
        for(String call : caller){
            LogUtils.logFormatPrint((++index) + " : " + call);
        }
    }

    /**
     * 转换JSON字符串
     * @return
     */
    public String toJsonStr(){
        return JSON.toJSONString(results);
    }

    /**
     * 获取结果集
     * @return
     */
    public Map<String,Object> getData(){
        return results;
    }

    /**
     * 获取Bean结果
     * @return
     */
    public <T> T getBean(String key,Class<T> beanClass){
        return BeanUtil.mapToBean(((Map)results.get(key)),beanClass,true);
    }

    /**
     * 获取Bean结果
     * @return
     */
    public <T> List<T> getBeanList(String key,Class<T> beanClass){
        List<Map<String,Object>> list = (List<Map<String,Object>>)results.get(key);
        List<T> targetList = Lists.newArrayList();
        for (Map<String,Object> item : list) {
            targetList.add(BeanUtil.mapToBean(item,beanClass,true));
        }
        return targetList;
    }

    /**
     * 获取Bean结果
     * @return
     */
    public <T> T getValue(String key,Class<T> typeClass){
        return (T)results.get(key);
    }

    /**
     * 获取PagingRecord结果
     * @return
     */
    public RecordGroup getRecordGroup(String key){
        return BeanUtil.mapToBean(((Map)results.get(key)),RecordGroup.class,true);
    }

    /**
     * 获取Record结果
     * @return
     */
    public Record getRecord(String key){
        return Record.empty().setData((Map)results.get(key));
    }

    /**
     * 获取RecordList结果
     * @return
     */
    public List<Record> getRecordList(String key){
        return (List<Record>)results.get(key);
    }

    /**
     * 逻辑失败，由业务控制
     * @param code
     * @param msg
     */
    public void failure(int code, String msg){
        results.put(AoomsConstants.Result.META ,new DataResultStatus(code, msg));
    }

    /**
     * 设置成功
     */
    public void success(){
        results.put(AoomsConstants.Result.META ,new DataResultStatus("success",true));
    }

    /**
     * 设置成功
     */
    public void success(String msg){
        results.put(AoomsConstants.Result.META ,new DataResultStatus(msg,true));
    }

    /**
     * 设置错误
     */
    public void error(){
        results.clear(); // 失败时清理数据
        DataResultStatus status = new DataResultStatus("",false);
        status.setError("Aooms error");
        results.put(AoomsConstants.Result.META ,status);
    }

    /**
     * 设置错误
     */
    public void error(String error){
        results.clear(); // 失败时清理数据
        DataResultStatus status = new DataResultStatus("",false);
        status.setError(error);
        results.put(AoomsConstants.Result.META ,status);
    }

    /**
     * 获取结果状态
     */
    public DataResultStatus getStatus(){
       return (DataResultStatus) results.get(AoomsConstants.Result.META);
    }

    /**
     * 判断结果置状态是否正常
     */
    public boolean isSuccess(){
        return getStatus().isSuccess();
    }


}