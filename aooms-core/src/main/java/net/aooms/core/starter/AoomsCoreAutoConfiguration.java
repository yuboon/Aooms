package net.aooms.core.starter;

import net.aooms.core.AoomsConfiguration;
import net.aooms.core.datasource.DynamicDataSourceConfiguration;
import net.aooms.core.module.mybatis.MyBatisConfiguration;
import net.aooms.core.module.mybatis.dao.GenericDao;
import net.aooms.core.module.mybatis.dao.GenericDaoSupport;
import net.aooms.core.property.PropertyApplication;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.property.PropertyServer;
import net.aooms.core.property.PropertyTest;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装载aooms-core工程
 */
@Configuration
public class AoomsCoreAutoConfiguration {

    /*@Bean
    public DynamicDataSourceConfiguration dynamicDataSourceConfiguration(){
        return new DynamicDataSourceConfiguration();
    }

    @Bean
    public AoomsConfiguration aoomsConfiguration(){
        return new AoomsConfiguration();
    }


    @Bean
    public MyBatisConfiguration myBatisConfiguration(){
        return new MyBatisConfiguration();
    }*/

    @Bean
    public PropertyApplication propertyApplication(){
        return new PropertyApplication();
    }

    @Bean
    public PropertyServer propertyServer(){
        return new PropertyServer();
    }

    @Bean
    public PropertyTest propertyTest(){
        return new PropertyTest();
    }

    @Bean
    public GenericDao genericDao(){
        return new GenericDaoSupport();
    }



    //net.aooms.core.module.mybatis.MyBatisConfiguration

    //PropertyApplication
    //PropertyObject
    //PropertyServer
    //

}
