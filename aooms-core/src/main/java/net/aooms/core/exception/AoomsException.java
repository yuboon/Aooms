package net.aooms.core.exception;

/**
 * 自定义框架异常，框架所有异常的基类
 *
 * Created by cccyb on 2018-04-18
 */
public class AoomsException extends RuntimeException {

    // 异常代码
    protected int code;
    // 异常描述
    protected String message;

    public AoomsException(String message) {
        this(message,0);
    }

    public AoomsException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public AoomsException(String message, Throwable cause) {
        this(message,0,cause);
        this.message = message;
    }

    public AoomsException(String message, int code,Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}