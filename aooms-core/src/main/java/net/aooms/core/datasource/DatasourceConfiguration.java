
package net.aooms.core.datasource;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class DatasourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DatasourceConfiguration.class);

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
