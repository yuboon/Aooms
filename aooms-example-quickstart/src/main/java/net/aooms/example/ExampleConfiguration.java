package net.aooms.example;

import cn.hutool.core.util.ArrayUtil;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsSetting;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.interceptor.AoomsInterceptorRegistryProxy;
import net.aooms.core.web.interceptor.DemoModeInterceptor;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * 配置类
 * Created by 风象南(yuboon) on 2018-09-18
 */
@Configuration
public class ExampleConfiguration {

    @Bean
    public AoomsSetting exampleSetting(){
        return new AoomsSetting() {

            @Override
            public void configService(ServiceConfigurations serviceConfigurations) {

            }

            @Override
            public void configInterceptor(AoomsInterceptorRegistryProxy interceptorRegistryProxy) {
                String[] excludes = new String[]{"/**/find*","/**/login","/**/logout"};
                String[] ignores = ArrayUtil.addAll(interceptorRegistryProxy.getIgnores(),excludes);
                interceptorRegistryProxy.addInterceptor(new DemoModeInterceptor(interceptorRegistryProxy.getPathPatterns(),ignores));
            }

            @Override
            public void configProps(PropertyObject propertyObject) {

            }

            @Override
            public void onFinish(Aooms aooms) {

            }
        };
    }

}