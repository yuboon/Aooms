package net.aooms.core.data;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.aooms.core.Vars;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DataBoss-结果对象
 * Created by cccyb on 2018-04-18
 */
public class DataResult implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String,Object> results = CollectionUtil.newHashMap();
    private List<String> caller = Lists.newArrayList();

    public DataResult() {

        // 默认设置成功状态
        this.success();

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

    public void printCaller(){
        System.out.println(LogUtils.logFormat("DataResult.set Total Called " + caller.size() + " Times"));
        int index = 0;
        for(String call : caller){
            System.out.println(LogUtils.logFormat((++index) + " : " + call));
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
     * 逻辑失败，由业务控制
     * @param code
     * @param msg
     */
    public void logicFailure(int code, String msg){
        results.put(Vars.Result.META ,new Status(code, msg));
    }

    /**
     * 设置成功
     */
    public void success(){
        results.put(Vars.Result.META ,new Status("success",true));
    }

    /**
     * 设置成功
     */
    public void success(String msg){
        results.put(Vars.Result.META ,new Status(msg,true));
    }

    /**
     * 设置失败
     */
    public void failure(){
        results.clear(); // 失败时清理数据
        Status status = new Status("",false);
        status.setError("Aooms error");
        results.put(Vars.Result.META ,status);
    }

    /**
     * 设置失败
     */
    public void failure(String error){
        results.clear(); // 失败时清理数据
        Status status = new Status("",false);
        status.setError(error);
        results.put(Vars.Result.META ,status);
    }

    /**
     * 获取结果状态
     */
    public Status getStatus(){
       return (Status) results.get(Vars.Result.META);
    }

    /**
     * 判断结果置状态是否正常
     */
    public boolean isSuccess(){
        return getStatus().code == 0;
    }

    /**
     * 结果状态对象
     */
    public class Status implements Serializable {

        private int code;

        private String msg;

        private String error;

        private String trace;

        private boolean isSuccess = true;

        public Status(String msg, boolean isSuccess) {
            this.msg = msg;
            this.isSuccess = isSuccess;
        }

        public Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Status(int code, String msg, boolean isSuccess) {
            this.code = code;
            this.msg = msg;
            this.isSuccess = isSuccess;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getTrace() {
            return trace;
        }

        public void setTrace(String trace) {
            this.trace = trace;
        }
    }

}