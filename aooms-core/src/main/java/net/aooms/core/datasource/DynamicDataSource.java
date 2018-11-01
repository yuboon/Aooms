package net.aooms.core.datasource;

import net.aooms.core.AoomsConstants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;


/**
 * 获取DynamicDataSourceHolder指定的数据源
 * Created by 风象南(yuboon) on 2018-08-18
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /*
     * 获取数据源ID
     */
    @Override
    protected Object determineCurrentLookupKey() {
       return DynamicDataSourceHolder.getDataSource();
    }

    public DataSource getDefaultDataSource(){
        //return this.determineTargetDataSource();
        return getDataSource(AoomsConstants.DEFAULT_DATASOURCE);
    }

    public DataSource getDataSource(String key){
        DataSource dataSource = DynamicDataSourceHolder.dataSourceMap.get(key);
        if(dataSource == null){
            throw new RuntimeException("The datasource was not found through the key [ {"+ key +"} ] ");
        }
        return dataSource;
    }

    public String getDriveName(String key){
        String driveName = DynamicDataSourceHolder.driveNameMap.get(key);
        if(driveName == null){
            throw new RuntimeException("The driveName was not found through the key [ {" + key + "} ] ");
        }
        return driveName;
    }

}