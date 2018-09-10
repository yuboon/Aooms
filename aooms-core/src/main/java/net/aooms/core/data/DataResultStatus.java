package net.aooms.core.data;

import java.io.Serializable;

/**
 * 接口结果状态对象
 */
public class DataResultStatus implements Serializable {

    private int code;

    private String msg;

    private String error;

    private String trace;

    private boolean isSuccess = true;

    public DataResultStatus() {

    }

    public DataResultStatus(String msg, boolean isSuccess) {
        this.msg = msg;
        this.isSuccess = isSuccess;
    }

    public DataResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResultStatus(int code, String msg, boolean isSuccess) {
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