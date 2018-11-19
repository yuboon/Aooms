package net.aooms.core.module.mybatis.interceptor;

import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.datasource.DynamicDataSourceHolder;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.dialect.DialectSelector;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;


/**
 * 查询插件
 * Created by 风象南(yuboon) on 2018/9/7
 */
@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    )
    /*@Signature(
        type = StatementHandler.class,
        method = "parameterize",
        args = {Statement.class}
    )*/
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

        /*MappedStatement mappedStatement2 = (MappedStatement)invocation.getArgs()[0];
        if(MyBatisConst.MS_RECORD_FIND_BY_PK.equals(mappedStatement2.getId())){
            System.err.println("invocation = " + invocation);
        }else{
            return invocation.proceed();
        }*/

        StatementHandler target = MetaObjectAssistant.getTarget(invocation,StatementHandler.class);
        MetaObject metaObject = MetaObjectAssistant.getMetaObject(target);

        PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler) metaObject.getValue("delegate");
        Map para = (Map) preparedStatementHandler.getBoundSql().getParameterObject();
        if(para == null) return invocation.proceed();

        Object isCount = para.get(MyBatisConst.CRUD_QUERY_COUNT_PLACEHOLDER);
        Object isPaging = para.get(MyBatisConst.CRUD_QUERY_PAGING_PLACEHOLDER);

        if(isCount != null){
            String countsql = "select count(*) count from (" + preparedStatementHandler.getBoundSql().getSql() + ") _table";
            metaObject.setValue("delegate.boundSql.sql",countsql);
        }

        if(isPaging != null){
            String ds = DynamicDataSourceHolder.getDataSource();
            String driveClass = Aooms.self().getDynamicDataSource().getDriveName(ds == null ? AoomsVar.DEFAULT_DATASOURCE : ds);
            //(String)metaObject.getValue("delegate.configuration.environment.dataSource.driverClass");
            RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
            String sql = preparedStatementHandler.getBoundSql().getSql();
            String pagingSql = dialectSelector.selector(driveClass).pagingQuery(sql,rowBounds);
            metaObject.setValue("delegate.boundSql.sql",pagingSql);

            metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET); // 默认分页不生效
            metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);   // 默认分页不生效
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