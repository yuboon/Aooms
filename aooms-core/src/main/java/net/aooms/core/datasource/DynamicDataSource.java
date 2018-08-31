package net.aooms.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

 

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /*
     * 代码中的determineCurrentLookupKey方法取得一个字符串，
     * 该字符串将与配置文件中的相应字符串进行匹配以定位数据源，配置文件，即applicationContext.xml文件中需要要如下代码：(non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {

       /*
        * DynamicDataSourceContextHolder代码中使用setDataSourceType
        * 设置当前的数据源，在路由类中使用getDataSourceType进行获取，
        *  交给AbstractRoutingDataSource进行注入使用。
        */
       return DynamicDataSourceHolder.getDataSource();
    }

}