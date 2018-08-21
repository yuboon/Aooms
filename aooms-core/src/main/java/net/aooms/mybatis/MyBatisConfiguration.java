package net.aooms.mybatis;

import net.aooms.mybatis.interceptor.RecordInterceptor;
import net.aooms.mybatis.mapper.MyBatisConst;
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
                // configuration.addMapper();
                HashSet<MappedStatement> mappedStatements = new HashSet<MappedStatement>(configuration.getMappedStatements());
                System.err.println("mappedStatements.size = " + mappedStatements.size());
                // 如果注释下面替换步骤就会出错
                for (MappedStatement ms : mappedStatements) {
                    //SimpleMapperHelper.changeMs(ms);
                    System.err.println("mappedStatements.id = " + ms.getId());
                }



                //必须使用<script>标签包裹代码
               /* StringBuffer sqlBuilder = new StringBuffer("<script>");
                //简单使用类名作为包名
                sqlBuilder.append("insert into ").append(" user ");
                Field[] fields = entityClass.getDeclaredFields();
                sqlBuilder.append(" <where> ");
                for (Field field : fields) {
                    sqlBuilder.append("<if test=\"")
                            .append(field.getName()).append("!=null\">");
                    //字段名直接作为列名
                    sqlBuilder.append(" and ").append(field.getName())
                            .append(" = #{").append(field.getName()).append("}");
                    sqlBuilder.append("</if>");
                }
                sqlBuilder.append("</where>");
                sqlBuilder.append("</script>");
                //解析 sqlSource
                SqlSource sqlSource = XML_LANGUAGE_DRIVER.createSqlSource(
                        ms.getConfiguration(), sqlBuilder.toString(), entityClass);
                //替换
                MetaObject msObject = SystemMetaObject.forObject(ms);
                msObject.setValue("sqlSource", sqlSource);*/
                SqlSource sqlSource = new SqlSource() {
                    @Override
                    public BoundSql getBoundSql(Object parameterObject) {
                        return new BoundSql(configuration,"nosqlscript",null,null);
                    }
                };

                MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_INSERT, sqlSource, SqlCommandType.INSERT);
                configuration.addMappedStatement(statementBuilder.build());


                /*
                MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType)
                        .resource(resource)
                        .fetchSize(fetchSize)
                        .timeout(timeout)
                        .statementType(statementType)
                        .keyGenerator(keyGenerator)
                        .keyProperty(keyProperty)
                        .keyColumn(keyColumn)
                        .databaseId(databaseId)
                        .lang(lang)
                        .resultOrdered(resultOrdered)
                        .resultSets(resultSets)
                        .resultMaps(getStatementResultMaps(resultMap, resultType, id))
                        .resultSetType(resultSetType)
                        .flushCacheRequired(valueOrDefault(flushCache, !isSelect))
                        .useCache(valueOrDefault(useCache, isSelect))
                        .cache(currentCache);

                ParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
                if (statementParameterMap != null) {
                    statementBuilder.parameterMap(statementParameterMap);
                }

                MappedStatement statement = statementBuilder.build();
                configuration.addMappedStatement(statement);
                */

               // configuration.addMappedStatement();

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
