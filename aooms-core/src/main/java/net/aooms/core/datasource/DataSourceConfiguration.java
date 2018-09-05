
package net.aooms.core.datasource;

import cn.hutool.core.util.StrUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import net.aooms.core.Constants;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public DataSource dataSource(Environment env) {
        // 数据源对象
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 默认数据源
        DataSource defaultDataSource = createDataSource(env,"spring.datasource",Constants.DEFAULT_DATASOURCE);

        // 其他数据源
        String more = env.getProperty("spring.more-datasource.keys");
        Map<Object, Object> moreDataSources = new HashMap<Object, Object>();
        if(StrUtil.isNotBlank(more)){

            String[] moreDataSourceNames = more.split(",");
            for(String name : moreDataSourceNames){
                if(StrUtil.isNotBlank(name)){
                    // 创建其他数据源
                    moreDataSources.put(name, createDataSource(env,"spring.more-datasource",name));
                }
            }
        }

        // 设置dynamicDataSource数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        dynamicDataSource.setTargetDataSources(moreDataSources);

        return dynamicDataSource;
    }

    private DataSource createDataSource(Environment env,String prefix,String name){
        HikariConfig config = Binder.get(env).bind(prefix + "." + name, HikariConfig.class).orElse(new HikariConfig());
        config.setMetricRegistry(meterRegistry);
        HikariDataSource dataSource = new HikariDataSource(config);
        // 添加到数据源持有对象
        DynamicDataSourceHolder.dataSourceIds.add(name);
        logger.info(LogUtils.logFormat("DataSource [" + name + "] - Start Completed , use conifg : " + prefix + "." + name));
        return dataSource;
    }

}
