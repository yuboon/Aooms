package net.aooms.core.module.mybatis.interceptor;

import cn.hutool.core.util.StrUtil;
import net.aooms.core.module.mybatis.record.IRecordOper;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.module.mybatis.record.RecordOperRouting;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;


@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    ),
    @Signature(
        type = StatementHandler.class,
        method = "parameterize",
        args = {Statement.class}
    )
})
public class RecordInterceptor implements Interceptor {

    private RecordOperRouting recordOperRouting = new RecordOperRouting();

    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin 
     * .Invocation) 
     */  
    public Object intercept(Invocation invocation) throws Throwable {
        //StatementHandler statementHandler = (StatementHandler)realTarget(invocation.getTarget());
        //MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        //Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
        //MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        StatementHandler target = MetaObjectAssistant.getTarget(invocation, StatementHandler.class);
        MetaObject metaObject = MetaObjectAssistant.getMetaObject(target);
        Object parameterObject = MetaObjectAssistant.getParameterObject(metaObject);

        // parameterObject is not a record , skip RecordInterceptor
        if(parameterObject.getClass() != Record.class){
            return invocation.proceed();
        }

        // record oper process
        IRecordOper recordOper = recordOperRouting.route(metaObject);
        recordOper.process();

        Object value = invocation.proceed();
        return value;
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