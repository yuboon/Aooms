package net.aooms.core.module.mybatis.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;

/*@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = {  
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }) })*/
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class MyInterceptor implements Interceptor {
  
    private static final String R_FEMALE = "女";  
  
    private static final String R_MALE = "男";  
  
    private static final String FEMALE = "female";  
  
    private static final String MALE = "male";  
    private Properties properties;


    public static Object realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        } else {
            return target;
        }
    }

    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin 
     * .Invocation) 
     */  
    public Object intercept(Invocation invocation) throws Throwable {
  System.err.println("invocation.name = " + invocation.getMethod().getName());
       /* MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();  
        String namespace = sqlId.substring(0, sqlId.indexOf('.'));  
        Executor exe = (Executor) invocation.getTarget();  
        String methodName = invocation.getMethod().getName();

        String s = mappedStatement.getSqlSource().getBoundSql(invocation.getArgs()[1]).getSql();
        System.err.println("s = " + s);*/



        StatementHandler statementHandler = (StatementHandler)realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        System.err.println("mappedStatement = " + mappedStatement);
        RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
        System.err.println("rowBounds = " + rowBounds);



        System.err.println(JSON.toJSONString(metaObject.getGetterNames()));

        //MapperBuilderAssistant mba = new MapperBuilderAssistant();
        //mba.addMappedStatement()

  
       /* if (methodName.equals("query")) {
                Object parameter = invocation.getArgs()[1];  
                RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];  
                   
        }  
        else if(methodName.equals("update")){  
            Object parameter = invocation.getArgs()[1];  
            //if(parameter instanceof User)
               // ((User)parameter).setGender(saveValueToDb(((User)parameter).getGender()));
              
        }*/

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
  
    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties) 
     */  
    public void setProperties(Properties properties) {
        this.properties = properties;  

    }

    /**插入数据库 
     * @param value 
     * @return 
     */  
    private String saveValueToDb(String value) {  
        if (value.equals(R_FEMALE)) {  
            return FEMALE;  
        } else if (value.equals(R_MALE)) {  
            return MALE;  
        } else {  
            throw new IllegalArgumentException("数据库异常!" + value);  
        }  
    }  
  
}  