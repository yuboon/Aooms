package net.aooms.mybatis;

import net.aooms.mybatis.interceptor.RecordInterceptor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class MyBatisConfiguration {

    /*@Bean
    public Interceptor interceptor(){
        return new MyInterceptor();
    }*/


    @Bean
    public Interceptor recordInterceptor(){
        return new RecordInterceptor();
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
                // HashSet<MappedStatement> mappedStatements = new HashSet<MappedStatement>(configuration.getMappedStatements());

                /*SqlSource sqlSource = new SqlSource() {
                    @Override
                    public BoundSql getBoundSql(Object parameterObject) {
                        return new BoundSql(configuration,"nosqlscript",null,null);
                    }
                };

                MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_INSERT, sqlSource, SqlCommandType.INSERT);
                configuration.addMappedStatement(statementBuilder.build());
                */

                RecordMappedStatmentFactory recordMappedStatmentFactory = new RecordMappedStatmentFactory(configuration);
                configuration.addMappedStatement(recordMappedStatmentFactory.getRecordInsertMappedStatment());


            }
        };
    }

}
