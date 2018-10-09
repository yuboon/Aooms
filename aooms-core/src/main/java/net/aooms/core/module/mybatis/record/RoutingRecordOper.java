package net.aooms.core.module.mybatis.record;

import net.aooms.core.module.mybatis.interceptor.MetaObjectAssistant;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;

/**
 * Record操作路由
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public class RoutingRecordOper {

    public IRecordOper route(MetaObject metaobject){
        MappedStatement mappedStatement = MetaObjectAssistant.getMappedStatement(metaobject);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        if(SqlCommandType.INSERT == sqlCommandType){
            return new RecordInsert(metaobject);
        }

        if(SqlCommandType.UPDATE == sqlCommandType){
            return new RecordUpdate(metaobject);
        }

        if(SqlCommandType.DELETE == sqlCommandType){
            return new RecordDelete(metaobject);
        }

        return null;
    }

}
