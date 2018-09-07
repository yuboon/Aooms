package net.aooms.core.datasource;

import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 动态数据源上下文
 */
public class DynamicDataSourceHolder {


    /*
     * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /*
     * 管理所有的数据源id;
     * 主要是为了判断数据源是否存在;
     */

    static List<String> dataSourceIds = new ArrayList<String>();
    static Map<String,DataSource> dataSourceMap = Maps.newHashMap();

    /**
     * 使用setDataSource设置当前的
     */

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void removeDataSource() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */

    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

}