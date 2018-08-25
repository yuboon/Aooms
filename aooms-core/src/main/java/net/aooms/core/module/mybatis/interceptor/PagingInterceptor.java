package net.aooms.core.module.mybatis.interceptor;

import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.dialect.DialectSelector;
import net.aooms.core.module.mybatis.record.IRecordOper;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.module.mybatis.record.RecordOperRouting;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;


@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    )
})
public class PagingInterceptor implements Interceptor {

    private DialectSelector dialectSelector = new DialectSelector();

    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin 
     * .Invocation) 
     */  
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler target = MetaObjectAssistant.getTarget(invocation,StatementHandler.class);
        MetaObject metaObject = MetaObjectAssistant.getMetaObject(target);

        PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler) metaObject.getValue("delegate");
        Map para = (Map) preparedStatementHandler.getBoundSql().getParameterObject();
        Object isCount = para.get(MyBatisConst.CRUD_QUERY_COUNT_PLACEHOLDER);
        Object isPaging = para.get(MyBatisConst.CRUD_QUERY_PAGING_PLACEHOLDER);

        if(isCount != null){
            String countsql = "select count(*) count from (" + preparedStatementHandler.getBoundSql().getSql() + ") _table";
            metaObject.setValue("delegate.boundSql.sql",countsql);
        }

        if(isPaging != null){
            String driveClass = (String)metaObject.getValue("delegate.configuration.environment.dataSource.driverClass");
            RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
            String sql = preparedStatementHandler.getBoundSql().getSql();
            String pagingSql = dialectSelector.selector(driveClass).pagingQuery(sql,rowBounds);
            metaObject.setValue("delegate.boundSql.sql",pagingSql);
        }

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