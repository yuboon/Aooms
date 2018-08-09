package net.aooms.core.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.hibernate.validator.cfg.context.ReturnValueTarget;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * DTO-结果对象
 * Created by cccyb on 2018-04-18
 */
public class DTORet implements Serializable {

    // result status key
    private static final String STATUS_KEY = "_RS";

    private Map<String,Object> results = CollectionUtil.newHashMap();

    public DTORet() {

        // 默认设置成功状态
        this.success();

    }

    /**
     * 填充结果数据
     * @param key
     * @param value
     * @return
     */
    public DTORet set(String key,Object value){
        results.put(key,value);
        return this;
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
        results.put(STATUS_KEY ,new Status(code, msg));
    }

    /**
     * 设置成功
     */
    public void success(){
        results.put(STATUS_KEY ,new Status("success",true));
    }

    /**
     * 设置成功
     */
    public void success(String msg){
        results.put(STATUS_KEY ,new Status(msg,true));
    }

    /**
     * 设置失败
     */
    public void failure(){
        results.clear(); // 失败时清理数据
        Status status = new Status("",false);
        status.setError("Aooms error");
        results.put(STATUS_KEY ,status);
    }

    /**
     * 设置失败
     */
    public void failure(String error){
        results.clear(); // 失败时清理数据
        Status status = new Status("",false);
        status.setError(error);
        results.put(STATUS_KEY ,status);
    }

    /**
     * 获取结果状态
     */
    public Status getStatus(){
       return (Status) results.get(STATUS_KEY);
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
    class Status implements Serializable {

        private int code;

        private String msg;

        private String error;

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
    }


}