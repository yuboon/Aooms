package net.aooms.core.module.mybatis.interceptor;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;


/**
 * MetaObject操作
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public abstract class MetaObjectAssistant {

    public static Object realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        } else {
            return target;
        }
    }

    public static MetaObject getMetaObject(Object target) {
        MetaObject metaObject = SystemMetaObject.forObject(target);
        return metaObject;
    }

    public static <T> T getTarget(Invocation invocation,Class<T> targetClass) {
        T target = (T) realTarget(invocation.getTarget());
        return target;
    }

    public static MappedStatement getMappedStatement(MetaObject metaObject) {
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        return mappedStatement;
    }

    public static Object getParameterObject(MetaObject metaObject) {
        Object parameterObject = metaObject.getValue("delegate.boundSql.parameterObject");
        return parameterObject;
    }

    public static void setDelegateBoundSql(MetaObject metaObject, BoundSql boundSql) {
        metaObject.setValue("delegate.boundSql", boundSql);
    }

    public static void setDelegateParameterHandlerBoundSql(MetaObject metaObject, BoundSql boundSql) {
        metaObject.setValue("delegate.parameterHandler.boundSql", boundSql);
    }

}  