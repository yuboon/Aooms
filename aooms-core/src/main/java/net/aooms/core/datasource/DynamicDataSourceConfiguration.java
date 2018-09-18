
package net.aooms.core.datasource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import io.shardingsphere.core.api.yaml.YamlShardingDataSourceFactory;
import net.aooms.core.AoomsConstants;
import net.aooms.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * Created by 风象南(cheereebo) on 2018-08-18
 */
@Configuration
public class DynamicDataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfiguration.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    DynamicDataSourceAspect dynamicDataSourceAspect(){
        return new DynamicDataSourceAspect();
    }

    @Bean
    @Primary
    public DataSource dataSource(Environment env) throws Exception {

        // 数据源名称
        String names = env.getProperty("spring.datasource.names");
        // sharding-jdbc 属性
        boolean shardingJdbc = env.getProperty("spring.datasource.sharding-jdbc",Boolean.class,false);
        String  shrdingNames = env.getProperty("spring.datasource.sharding-names");

        // 数据源对象
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // log
        logger.info(LogUtils.logFormat("DataSources : [ {} ]"),names);
        logger.info(LogUtils.logFormat("ShardingJdbc : [ {} ]"),shardingJdbc);
        logger.info(LogUtils.logFormat("ShardingDataSources : [ {} ]"),shrdingNames);

        Map<Object, Object> dataSources = new HashMap<Object, Object>();
        Map<String, DataSource> shardingDataSources = new HashMap<String, DataSource>();

        if(StrUtil.isNotBlank(names)){
            String[] dataSourceNames = names.split(",");
            buildDataSources(env,dataSourceNames,dataSources);
        }

        // 设置dynamicDataSource数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSources.get(AoomsConstants.DEFAULT_DATASOURCE));
        dynamicDataSource.setTargetDataSources(dataSources);


        // sharding-jdbc 数据源创建
        if(StrUtil.isNotBlank(shrdingNames) && shardingJdbc){
            String[] dataSourceNames = shrdingNames.split(",");
            buildDataSources(env,dataSourceNames,shardingDataSources);

            java.net.URL url = this.getClass().getResource("/application-sharding-jdbc.yml");
            if(url != null){
                byte[] bytes = FileUtil.readBytes(url.getPath());
                DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(shardingDataSources,bytes);
                // 设置默认数据源为 sharding-jdbc 构造的数据源
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
                // 修改datasources中的默认数据源,其他数据源不变
                dataSources.put(AoomsConstants.DEFAULT_DATASOURCE,dataSource);
                DynamicDataSourceHolder.dataSourceMap.put(AoomsConstants.DEFAULT_DATASOURCE,dataSource);
            }else{
                throw new RuntimeException("application-sharding-jdbc.yml not found !");
            }
        }



        // 配置Order表规则
        /*TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        //orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order${0..1}");
        orderTableRuleConfig.setActualDataNodes("master.t_order_${0..1}");*/

        // 配置分库 + 分表策略
        /*orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "master"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);*/

        // 省略配置order_item表规则...
        // ...

        // 读写分离
        //MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("master","master", Arrays.asList("slave"));
        //shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfig);

        //DefaultKeyGenerator
        // 获取数据源对象
        //DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());


        return dynamicDataSource;
    }

    private void buildDataSources(Environment env,String[] dataSourceNames,Map map){
        for(String name : dataSourceNames){
            if(StrUtil.isNotBlank(name)){
                // 创建数据源
                DataSource dataSource = createDataSource(env,"spring.datasource",name);
                map.put(name, dataSource);
            }
        }
    }

    private DataSource createDataSource(Environment env,String prefix,String name){
        HikariConfig config = Binder.get(env).bind(prefix + "." + name, HikariConfig.class).orElse(new HikariConfig());
        config.setMetricRegistry(meterRegistry);
        HikariDataSource dataSource = new HikariDataSource(config);
        // 添加到数据源持有对象
        DynamicDataSourceHolder.dataSourceIds.add(name);
        DynamicDataSourceHolder.dataSourceMap.put(name,dataSource);

        logger.info(LogUtils.logFormat("DataSource [" + name + "] - Start Completed , use conifg : " + prefix + "." + name));
        return dataSource;
    }

}
