package net.aooms.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

 

/**
 * 获取DynamicDataSourceHolder指定的数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /*
     * 获取数据源ID
     */
    @Override
    protected Object determineCurrentLookupKey() {

       return DynamicDataSourceHolder.getDataSource();
    }

}