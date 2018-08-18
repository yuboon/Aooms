package net.aooms.core.module.mybatis;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Properties;

@Configuration
public class MyBatisConfiguration {

    @Bean
    public Interceptor interceptor(){
        return new MyInterceptor();
    }


    /*@Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }*/

    // @Configuration class
    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // configuration.addMapper();
                HashSet<MappedStatement> mappedStatements = new HashSet<MappedStatement>(configuration.getMappedStatements());

                // 如果注释下面替换步骤就会出错
                for (MappedStatement ms : mappedStatements) {
                    //SimpleMapperHelper.changeMs(ms);
                }

                //替换后执行该方法
                //CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
                //Country query = new Country();
                //可以修改条件或者注释条件查询全部
               /* query.setCountrycode("CN");
                List<Country> countryList = mapper.select(query);
                for (Country country : countryList) {
                    System.out.printf("%s - %s\n",
                            country.getCountryname(),
                            country.getCountrycode());
                }*/
            }
        };
    }

}
