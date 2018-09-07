package net.aooms.core.util;

/**
 * 日志打印
 * Created by cccyb on 2018-03-06
 */
public class LogUtils {

    public static String errorLogFormat(String errorMsg){
        return "AoomsErrorMsg >> " + errorMsg;
    }

    public static String logFormat(String msg){
        return "AoomsMsg >> " + msg;
    }

    public static void logFormatPrint(String msg){
        System.err.println(logFormat(msg));
    }

    public static void errorLogFormatPrint(String msg){
        System.err.println(errorLogFormat(msg));
    }

}