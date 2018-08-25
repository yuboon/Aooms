package net.aooms.core.module.mybatis.record;

import cn.hutool.core.util.StrUtil;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.interceptor.MetaObjectAssistant;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.Iterator;
import java.util.Map;

public class RecordInsert implements IRecordOper {

    private MetaObject metaObject;

    public RecordInsert(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    @Override
    public void process() {
        MappedStatement mappedStatement = MetaObjectAssistant.getMappedStatement(metaObject);
        Object parameterObject = MetaObjectAssistant.getParameterObject(metaObject);
        Record record = (Record) parameterObject;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" insert into ");
        stringBuilder.append(record.get(MyBatisConst.TABLE_NAME_PLACEHOLDER)); // tableName
        stringBuilder.append(" ({}) ");
        stringBuilder.append(" values ");
        stringBuilder.append(" ({}) ");

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        int index = 0;

        // 移除内部属性
        record.removeInternalKey();
        Iterator<String> keyIterator = record.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (index > 0) {
                columns.append(",");
                values.append(",");
            }
            columns.append(key);
            values.append("#{").append(key).append("}");
            index++;
        }

        String sql = StrUtil.format(stringBuilder, columns, values);
        SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(mappedStatement.getConfiguration(), sql, Map.class);
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        //metaObject.setValue("delegate.boundSql", boundSql);
        //metaObject.setValue("delegate.parameterHandler.boundSql", boundSql);

        MetaObjectAssistant.setDelegateBoundSql(metaObject,boundSql);
        MetaObjectAssistant.setDelegateParameterHandlerBoundSql(metaObject,boundSql);
    }

}
