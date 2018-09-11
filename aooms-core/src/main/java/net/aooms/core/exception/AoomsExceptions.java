package net.aooms.core.exception;

/**
 * 框架异常工具类
 * Created by 风象南(cheereebo) on 2018-04-18
 */
public class AoomsExceptions {

    /**
     * 创建异常
     * @return
     */
    public static AoomsException create(String msg){
        return new AoomsException(msg);
    }

    /**
     * 创建异常
     * @return
     */
    public static AoomsException create(String msg,int code){
        return new AoomsException(msg,code);
    }

    /**
     * 创建异常
     * @return
     */
    public static AoomsException create(String msg, Throwable throwable){
        return new AoomsException(msg,throwable);
    }

    /**
     * 创建异常
     * @return
     */
    public static AoomsException create(String msg, int code,Throwable throwable){
        return new AoomsException(msg,code,throwable);
    }


}