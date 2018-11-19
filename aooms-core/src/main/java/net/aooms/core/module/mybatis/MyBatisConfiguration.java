package net.aooms.core.module.mybatis;

import net.aooms.core.module.mybatis.interceptor.SwitchDataSourceResetInterceptor;
import net.aooms.core.module.mybatis.interceptor.QueryInterceptor;
import net.aooms.core.module.mybatis.interceptor.RecordInterceptor;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置
 * Created by 风象南(yuboon) on 2018/9/7
 */
@Configuration
public class MyBatisConfiguration {

    /*@Bean
    public Interceptor interceptor(){
        return new MyInterceptor();
    }*/

    @Bean
    public Interceptor switchDataSourceResetInterceptor(){
        return new SwitchDataSourceResetInterceptor();
    }

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
                // 修改默认vfs, 解决jar包 mapper.xml 不加载问题
                VFS.addImplClass(SpringBootVFS.class);
                //configuration.setVfsImpl(SpringBootVFS.class);

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


    /*@Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {

        // DefaultVFS在获取jar上存在问题，使用springboot只能修改
        VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources("classpath*:/net/aooms/rbac/mapper/*.xml");
        //Resource[] resources2 = resolver.getResources("classpath*:/mysql/mapper/*.xml");
        //Resource[] resources = (Resource[]) ArrayUtils.addAll(resources1);
        sqlSessionFactoryBean.setMapperLocations(resources);
        // sqlSessionFactoryBean.setTypeAliasesPackage("com.xxx.xx.entity");
        return sqlSessionFactoryBean.getObject();
    }*/

}
