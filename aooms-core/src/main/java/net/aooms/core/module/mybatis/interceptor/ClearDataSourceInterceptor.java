package net.aooms.core.module.mybatis.interceptor;

import net.aooms.core.datasource.DynamicDataSourceHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;


/**
 * 数据源清除
 * Created by 风象南(cheereebo) on 2018/9/7
 */
@Intercepts({
    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
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
            if(!TransactionSynchronizationManager.isActualTransactionActive()){
                DynamicDataSourceHolder.removeDataSource();
            }
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