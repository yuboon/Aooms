package net.aooms.core.module.mybatis.interceptor;

import net.aooms.core.module.mybatis.record.IRecordOper;
import net.aooms.core.module.mybatis.record.OperRouting;
import net.aooms.core.module.mybatis.record.Record;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;


/**
 * Record处理插件
 * Created by 风象南(cheereebo) on 2018/9/7
 */
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

    private OperRouting recordOperRouting = new OperRouting();

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
        if(parameterObject == null) return invocation.proceed();

        // parameterObject is not a pojo , skip RecordInterceptor
        if(parameterObject.getClass() != Record.class){
            return invocation.proceed();
        }

        // pojo oper process
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