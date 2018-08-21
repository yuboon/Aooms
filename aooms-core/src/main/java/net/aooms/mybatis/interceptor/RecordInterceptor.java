package net.aooms.mybatis.interceptor;

import cn.hutool.core.util.StrUtil;
import net.aooms.mybatis.mapper.MyBatisConst;
import net.aooms.mybatis.mapper.Record;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class RecordInterceptor implements Interceptor {

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
        StatementHandler statementHandler = (StatementHandler)realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(mappedStatement.getConfiguration(), sqlBuilder.toString(), Record.class);
        //替换
        //MetaObject msMetaObject = SystemMetaObject.forObject(mappedStatement);
        //msMetaObject.lue("sqlSource", sqlSource);

        if(SqlCommandType.INSERT == mappedStatement.getSqlCommandType()){
            Object parameterObject = statementHandler.getParameterHandler().getParameterObject();//metaObject.getValue("delegate.boundSql.parameterObject");

            Record record = (Record) parameterObject;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" insert into ");
            stringBuilder.append(  record.remove(MyBatisConst.TABLE_NAME_PLACEHOLDER)); // tableName
            stringBuilder.append(" ({}) ");
            stringBuilder.append(" values ");
            stringBuilder.append(" ({}) ");

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            int index = 0;
            Iterator<String> keyIterator = record.keySet().iterator();
            while(keyIterator.hasNext()){
                String key = keyIterator.next();
                if(index > 0){
                    columns.append(",");
                    values.append(",");
                }
                System.err.println("key = {}" + key);
                columns.append(key);
                values.append("#{").append(key).append("}");
                index++;
            }


            //String sql = ("insert into "+  +"(id,name) values ("+ System.currentTimeMillis() +",#{name})");
            String sql = StrUtil.format(stringBuilder,columns,values);
            SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(mappedStatement.getConfiguration(), sql, Map.class);

            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
            metaObject.setValue("delegate.boundSql", boundSql);
            metaObject.setValue("delegate.parameterHandler.boundSql", boundSql);
        }



        //metaObject.setValue("delegate.boundSql.parameterObject", maps);
        //metaObject.setValue("delegate.mappedStatement.sqlSource", sqlSource);

       // statementHandler.getClass().getField("")

        //System.err.println("mappedStatement = " + mappedStatement);
        //RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
        //System.err.println("rowBounds = " + rowBounds);
        //String methodName = invocation.getMethod().getName();
        //System.err.println("methodName:" + methodName);

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

    @Override
    public void setProperties(Properties properties) {

    }

}  