package net.aooms.core.module.mybatis;

import net.aooms.core.module.mybatis.interceptor.QueryInterceptor;
import net.aooms.core.module.mybatis.interceptor.RecordInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public Interceptor pagingInterceptor(){
        return new QueryInterceptor();
    }


    // 分页 https://www.cnblogs.com/hubing/p/5564692.html

    /*@Bean
    public Interceptor recordInsertBatchInterceptor(){
        return new RecordInsertBatchInterceptor();
    }
*/



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
                // 设置map属性空值时仍返回数据
                configuration.setCallSettersOnNulls(true);

                RecordMappedStatmentFactory recordMappedStatmentFactory = new RecordMappedStatmentFactory(configuration);
                configuration.addMappedStatement(recordMappedStatmentFactory.getRecordInsertMappedStatment());
                configuration.addMappedStatement(recordMappedStatmentFactory.getRecordUpdateMappedStatment());
                configuration.addMappedStatement(recordMappedStatmentFactory.getRecordDeleteMappedStatment());
                configuration.addMappedStatement(recordMappedStatmentFactory.getRecordFindByPkMappedStatment());

            }
        };
    }

}
