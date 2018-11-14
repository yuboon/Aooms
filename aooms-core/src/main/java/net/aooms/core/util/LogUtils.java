package net.aooms.core.util;

/**
 * 简易框架内部日志输出、格式化
 * Created by 风象南(yuboon) on 2018-03-06
 */
public class LogUtils {


    public static void info(String msg){
        System.out.println("AoomsLog --- [INFO] " + msg);
    }

    public static void error(String msg){
        System.err.println("AoomsLog --- [ERROR] " + msg);
    }

}