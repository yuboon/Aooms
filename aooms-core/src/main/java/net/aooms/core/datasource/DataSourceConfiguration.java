
package net.aooms.core.datasource;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class DataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    /*private String user;

    private String password;

    private String url;

    private String driverClassName;

    private String connectionTestQuery;*/

    @Autowired
    private MetricRegistry metricRegistry;
/*
    @Bean
    public DataSource primaryDataSource() {
        Properties dsProps = new Properties();
        dsProps.setProperty("url", url);
        dsProps.setProperty("user", user);
        dsProps.setProperty("password", password);

        Properties configProps = new Properties();
        configProps.setProperty("connectionTestQuery", connectionTestQuery);
        configProps.setProperty("driverClassName", driverClassName);
        configProps.setProperty("jdbcUrl", url);

        HikariConfig hc = new HikariConfig(configProps);
        hc.setDataSourceProperties(dsProps);
        hc.setMetricRegistry(metricRegistry);
        return new HikariDataSource(hc);
    }*/

    /*@Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public DataSource primaryDataSource() {
        //HikariDataSource hikariDataSource = new HikariDataSource();
        logger.info("hikari datasource create.......");
        return DataSourceBuilder.create().build();
    }*/

}
