package net.aooms.mybatis.record;

import cn.hutool.core.util.StrUtil;
import net.aooms.core.Vars;
import net.aooms.mybatis.MyBatisConst;
import net.aooms.mybatis.interceptor.MetaObjectAssistant;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.Iterator;
import java.util.Map;

public class RecordUpdate implements IRecordOper {

    private MetaObject metaObject;

    public RecordUpdate(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    @Override
    public void process() {
        MappedStatement mappedStatement = MetaObjectAssistant.getMappedStatement(metaObject);
        Object parameterObject = MetaObjectAssistant.getParameterObject(metaObject);
        Record record = (Record) parameterObject;

        String tableName = record.getString(MyBatisConst.TABLE_NAME_PLACEHOLDER);
        String pkName = String.valueOf(record.getOrDefault(MyBatisConst.TABLE_PK_NAME_PLACEHOLDER,Vars.ID));
        Object pkValue = record.get(pkName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" update ");
        stringBuilder.append(record.get(MyBatisConst.TABLE_NAME_PLACEHOLDER)); // tableName
        stringBuilder.append(" set {} ");
        stringBuilder.append(" where "+ pkName +" = #{"+ pkName +"} ");

        StringBuilder columns = new StringBuilder();
        int index = 0;
        record.removeInternalKey();
        Iterator<String> keyIterator = record.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (index > 0) {
                columns.append(",");
            }
            columns.append(key).append(" = ").append("#{").append(key).append("}");
            index++;
        }

        String sql = StrUtil.format(stringBuilder, columns);
        SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(mappedStatement.getConfiguration(), sql, Map.class);
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);

        MetaObjectAssistant.setDelegateBoundSql(metaObject,boundSql);
        MetaObjectAssistant.setDelegateParameterHandlerBoundSql(metaObject,boundSql);
    }

}
