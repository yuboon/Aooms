package net.aooms.core.module.mybatis.record;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.interceptor.MetaObjectAssistant;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.Iterator;
import java.util.Map;

public class OperRouting {

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
