package net.aooms.core.module.mybatis.interceptor;

import net.aooms.core.Constants;
import net.aooms.core.datasource.DynamicDataSourceContextHolder;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.dialect.DialectSelector;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;


@Intercepts({
    @Signature(
        type = Executor.class,
        method = "setExecutorWrapper",
        args = {Executor.class}
    )
})
public class ClearDataSourceInterceptor implements Interceptor {

    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin 
     * .Invocation) 
     */  
    public Object intercept(Invocation invocation) throws Throwable {
        try{
            Object value = invocation.proceed();
            return value;
        }finally {
            System.err.println("finallycdfsdss");
            DynamicDataSourceContextHolder.clearDataSource();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}  